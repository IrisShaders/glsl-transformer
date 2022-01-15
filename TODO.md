# Todo

- Add transformation that does wrapping: check for presence of wrap replacement, inject wrapper code, perform identifier replacement with expression or terminal
- Some sort of additional parameter system for passing per-job state to transformation phases is required. (in order to have them activate/deactivate using the already present isActive system)
- Reduce the amount of `instanceof` usage, especially around RunPhase/WalkPhase
- Test ReplaceTerminals static methods
- Test addConcurrentPhase on Transformation
- Test the token filters and all their associated functionality (on transformation manager, printer and all the token filters themselves)
- Test removal/replacement of single terminal node (tests ExtendedTerminalNode)
- Figure out if and how removal of local roots works (nothing needs to be omitted since they are additions, trying to omit them may break. a placeholder still needs to be placed though)
- Write tests that matching still works even after injection
- Test node removal, replacement and injection in local roots (in `TransformationPhaseTest`)
- Unit (and integration) testing of all the other parts

- AST structures for GLSL types: Continue work on Tensor (tensor manipulation methods and "widening" until it hits the maximum)
- AST: Structs
- More Phase functionality (abstract declaration replacement into a separate class)
- Error on any tokens in the PREPROCESSOR channel optionally
- Make the Lexer parse #define directives (and others?) better
- Macro expansion (preprocessor) and other functions?

- DynamicParseTreeWalker: have transformation phase tell it about movements in the child array it caused to avoid inserting placeholder nodes (and then also avoid even doing the compacting step if it's not necessary)

## Other

- Publish Java 8-compatible version as `glsl-transformer-j8` instead of it being the main version. This would only be useful in case testing doesn't find a bug that jabel introduced into the J8 version that version being tested on J16/17 doesn't have. The main version would be "safe" from this kind of bug in jabel since it's the same exact code as is being tested.
