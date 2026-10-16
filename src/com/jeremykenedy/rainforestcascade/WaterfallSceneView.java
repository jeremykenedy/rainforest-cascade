package com.jeremykenedy.rainforestcascade;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.preference.PreferenceManager;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Random;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public final class WaterfallSceneView extends GLSurfaceView implements GLSurfaceView.Renderer {
  private final WaterfallOptions options;
  private final FloatBuffer vertices =
      ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder()).asFloatBuffer();
  private final float[] matrix = new float[16];
  private final HashMap<String, Integer> uniforms = new HashMap<>();
  private int position;
  private volatile boolean active;
  private boolean frameAvailable;
  private SurfaceTexture video;
  private MediaPlayer player;
  private int program, texture, width, height;
  private long started;

  public WaterfallSceneView(Context context) {
    super(context);
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    options =
        WaterfallOptions.resolve(
            prefs.getString("environment", "forest"), prefs.getString("lighting", "day"),
            prefs.getString("width", "curtain"), prefs.getString("speed", "natural"),
            prefs.getString("mist", "light"), prefs.getString("sunlight", "on"),
            prefs.getBoolean("randomize_all", false), new Random());
    vertices.put(new float[] {-1, -1, 1, -1, -1, 1, 1, 1}).position(0);
    setEGLContextClientVersion(2);
    setRenderer(this);
    setRenderMode(RENDERMODE_CONTINUOUSLY);
  }

  public void start() {
    active = true;
    onResume();
  }

  public void stop() {
    active = false;
    queueEvent(this::release);
    onPause();
  }

  private void release() {
    if (player != null) {
      player.release();
      player = null;
    }
    if (video != null) {
      video.release();
      video = null;
    }
  }

  private String readShader() {
    try (java.io.InputStream input = getContext().getAssets().open("waterfall.frag")) {
      java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
      byte[] bytes = new byte[4096];
      int count;
      while ((count = input.read(bytes)) != -1) out.write(bytes, 0, count);
      return out.toString("UTF-8");
    } catch (java.io.IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private int shader(int type, String source) {
    int shader = GLES20.glCreateShader(type);
    GLES20.glShaderSource(shader, source);
    GLES20.glCompileShader(shader);
    int[] ok = new int[1];
    GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, ok, 0);
    if (ok[0] == 0) throw new IllegalStateException(GLES20.glGetShaderInfoLog(shader));
    return shader;
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    release();
    int vertex =
        shader(
            GLES20.GL_VERTEX_SHADER,
            "attribute vec2 position; varying vec2 uv; void main(){uv=position*.5+.5;gl_Position=vec4(position,0.,1.);}");
    int fragment = shader(GLES20.GL_FRAGMENT_SHADER, readShader());
    program = GLES20.glCreateProgram();
    GLES20.glAttachShader(program, vertex);
    GLES20.glAttachShader(program, fragment);
    GLES20.glLinkProgram(program);
    int[] ok = new int[1];
    GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, ok, 0);
    if (ok[0] == 0) throw new IllegalStateException(GLES20.glGetProgramInfoLog(program));
    uniforms.clear();
    for (String name :
        new String[] {
          "waterfall", "videoMatrix", "resolution", "time", "night", "mist", "sunlight", "framing"
        }) {
      uniforms.put(name, GLES20.glGetUniformLocation(program, name));
    }
    position = GLES20.glGetAttribLocation(program, "position");
    GLES20.glDeleteShader(vertex);
    GLES20.glDeleteShader(fragment);
    int[] textures = new int[1];
    GLES20.glGenTextures(1, textures, 0);
    texture = textures[0];
    GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture);
    GLES20.glTexParameteri(
        GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
    GLES20.glTexParameteri(
        GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
    video = new SurfaceTexture(texture);
    video.setOnFrameAvailableListener(
        surface -> {
          synchronized (this) {
            frameAvailable = true;
          }
        });
    Surface surface = new Surface(video);
    player = new MediaPlayer();
    int recording =
        getResources().getIdentifier(options.sceneAsset, "raw", getContext().getPackageName());
    if (recording == 0) throw new IllegalStateException("The selected waterfall is missing");
    try (AssetFileDescriptor asset = getResources().openRawResourceFd(recording)) {
      player.setDataSource(asset.getFileDescriptor(), asset.getStartOffset(), asset.getLength());
      player.setSurface(surface);
      player.setLooping(true);
      player.setVolume(0, 0);
      player.prepare();
      player.setPlaybackParams(new PlaybackParams().setSpeed(options.speed).setPitch(1f));
      if (active) player.start();
    } catch (java.io.IOException e) {
      throw new IllegalStateException("The bundled waterfall could not play", e);
    } finally {
      surface.release();
    }
    started = System.nanoTime();
  }

  @Override
  public void onSurfaceChanged(GL10 gl, int w, int h) {
    width = w;
    height = h;
    GLES20.glViewport(0, 0, w, h);
  }

  private void uniform(String name, float value) {
    GLES20.glUniform1f(uniforms.get(name), value);
  }

  @Override
  public void onDrawFrame(GL10 gl) {
    GLES20.glClearColor(0, 0, 0, 1);
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    if (!active || video == null) return;
    synchronized (this) {
      if (frameAvailable) {
        video.updateTexImage();
        video.getTransformMatrix(matrix);
        frameAvailable = false;
      }
    }
    GLES20.glUseProgram(program);
    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
    GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture);
    GLES20.glUniform1i(uniforms.get("waterfall"), 0);
    GLES20.glUniformMatrix4fv(uniforms.get("videoMatrix"), 1, false, matrix, 0);
    GLES20.glUniform2f(uniforms.get("resolution"), width, height);
    float elapsed = (System.nanoTime() - started) / 1000000000f;
    uniform("time", elapsed);
    uniform("framing", options.framingScale);
    uniform("night", options.night ? 1f : 0f);
    uniform("mist", options.mist / 72f);
    uniform("sunlight", options.sunlight && !options.night ? 1f : 0f);
    GLES20.glEnableVertexAttribArray(position);
    GLES20.glVertexAttribPointer(position, 2, GLES20.GL_FLOAT, false, 0, vertices);
    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    GLES20.glDisableVertexAttribArray(position);
  }
}
