# Todo

- Exclude generated classes from jacoco coverage reports
- make replacement targets and WrapIdentifier more extensible
- Call init on handler targets, also other things like before/after search and activation
- Job parameters on token filters
- Test everything that isn't covered yet
- Test WrapIdentifier
- Test job parameter system
- Test ReplaceTerminals static methods
- Test addConcurrentPhase on Transformation
- Test the token filters and all their associated functionality (on transformation manager, printer and all the token filters themselves)
- Test removal/replacement of single terminal node (tests ExtendedTerminalNode)
- Figure out if and how removal of local roots works (nothing needs to be omitted since they are additions, trying to omit them may break. a placeholder still needs to be placed though)
- Write tests that matching still works even after injection (does it work before placeholder cleanup? and after?)
- Test node removal, replacement and injection in local roots (in `TransformationPhaseTest`)
- Unit (and integration) testing of all the other parts

- AST structures for GLSL types: Continue work on Tensor (tensor manipulation methods and "widening" until it hits the maximum)
- AST: Structs
- More Phase functionality (abstract declaration replacement into a separate class)
- Error on any tokens in the PREPROCESSOR channel optionally
- Make the Lexer parse #define directives (and others?) better
- Macro expansion (preprocessor) and other functions?

- DynamicParseTreeWalker: have transformation phase tell it about movements in the child array it caused to avoid inserting placeholder nodes (and then also avoid even doing the compacting step if it's not necessary)

- Performance ideas:
  - Stop tree walk if all participating walk phases signal that they're done
  - Limit depth of tree walk if participating walk phases agree
  - Limit tree walk to certain nodes (/node types) or exclude some node types from being visited if participating walk phases agree (maybe also dynamically adjust)

## Future Goals

- Macro substitution by parsing preprocessor directives

## An example transformation

One program transformation job that this project wants to achieve is the following. (from coderbot on Discord)

Match all declarations of the form:

`layout (location = 0) in vec4 Name;` or `layout (location = 0) attribute vec4 Name;`

Then:

1. delete that declaration
2. replace all references to that attribute / input with a function call to iris_getModelSpaceVertexPosition()
3. Add the function iris_getModelSpaceVertexPosition() with a specified payload
4. Add a different vertex attribute declaration called iris_Position, Assuming:
5. there are no functions already called iris_getModelSpaceVertexPosition and there are no vertex attributes with the name iris_Position - if any are found, flag an error
