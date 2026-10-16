#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES waterfall;
uniform mat4 videoMatrix;
uniform vec2 resolution;
uniform float time;
uniform float night;
uniform float framing;
uniform float mist;
uniform float sunlight;
varying vec2 uv;
void main(){
 float aspect=resolution.x/max(resolution.y,1.);
 vec2 fit=vec2(min(1.,aspect/(16./9.)),min(1.,(16./9.)/aspect));
 vec2 q=(uv-.5)*fit*(.982/framing)+.5+vec2(.003*sin(time*.011),.002*cos(time*.014));
 vec4 sampleUv=videoMatrix*vec4(q,0.,1.);
 vec3 color=texture2D(waterfall,sampleUv.xy).rgb;
 float luminance=dot(color,vec3(.299,.587,.114));
 vec3 moon=mix(vec3(luminance),color,.30)*vec3(.28,.36,.52);
 color=mix(color,moon,night);
 // Added atmosphere remains separate from the real spray recorded in the clip.
 float wisps=sin(uv.x*8.+sin(uv.y*7.+time*.12)*1.2+time*.08)*.5+.5;
 wisps*=sin(uv.x*13.-uv.y*5.-time*.09)*.25+.75;
 float lowMist=exp(-pow((uv.y-.17)*4.8,2.))*wisps*mist*.15;
 color=mix(color,mix(vec3(.82,.88,.89),vec3(.24,.34,.48),night),lowMist);
 float rays=pow(max(0.,sin((uv.x+uv.y*.43)*28.+time*.018)),7.);
 rays*=smoothstep(.05,.85,uv.y)*.028*sunlight;
 color+=vec3(1.,.85,.56)*rays;
 color*=1.-.09*pow(length((uv-.5)*1.3),2.);
 gl_FragColor=vec4(color,1.);
}
