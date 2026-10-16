# Quality correction

The original native captures and Canvas source show a flat painted cliff opening,
a low-detail water sheet and repeated marks. The old renderer scheduled frames
at 33 ms intervals; this was a request, not a measured frame rate. Its physical
Fire TV preview check did not establish idle DreamService activation.

The private replacement decodes licensed real waterfall recordings into an
OpenGL ES 2.0 surface. Three real environments provide a wooded cascade, mossy stone falls and a
snowy canyon waterfall. Wide, balanced and close framing use uniform camera
zoom. No waterfall is widened by horizontal stretching. Day/night is
an explicit color grade of daylight footage; added mist and sunlight shimmer
remain separate overlays and cannot remove real spray or sunlight already in
the recording. Speed changes real video playback. Current native rendering and control
checks are recorded in [verification](VERIFICATION.md). Moving visual approval
and physical hardware release gates remain separate.

The existing seven preference keys and accepted values remain stable:
`environment`, `lighting`, `width`, `speed`, `mist`, `sunlight`, `randomize_all`.
Labels now say Forest and Canyon rather than claiming all locations are a
rainforest or red canyon. Package identity and the production certificate are
unchanged. The signer SHA-256 is
`4818ad1fb9c91392b47d28fb84113640dfdc386d642890d980dd632637161400`.

The original JaCoCo report had 43/43 lines and 46/46 branches covered, but its
COVEREDCOUNT gate only required one covered line/branch. The corrected gate uses
COVEREDRATIO 1.0. The Gradle project was moved to the source root so Spotless
checks actual Java files rather than outside-project globs. The current host
report has 49/49 lines and 46/46 branches covered. Framework, decoder, graphics
and lifecycle code require native verification and are outside this percentage.

Current gallery captures show the actual replacement on the API 34 emulator.
Poster review did not establish source suitability: complete timelines exposed
people, internal cuts and handheld pans in several rejected recordings. The
candidate therefore uses three stronger moving environments with honest uniform
framing controls. It does not claim nine different waterfall locations or
physical width control. The flower-garden sunrise scene remains unfulfilled.
No candidate release or physical TV test is claimed by this audit.

## Separate sunrise garden candidate

The garden reference has a distinct original animated-matte candidate. Its generated 1672x941 plate is rendered to 1080p with independently moving falling-water detail, pond waves, mist and local foreground foliage. It is not recorded footage. Native garden framing/effects, remote Down then Select Preview and Back, actual bound DreamService and credits were checked on API 34; exact preferences and system values were restored. Independent native moving review accepted changing waterfall, pond and mist across the 16-second cycle. Public branch checks and physical verification remain pending.
