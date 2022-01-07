# Todo

- ASTNode tests
- StringTerminalNode tests (printing did not work previously)
- IdentifierReplacement that lets the user register replacements of identifiers with expressions and then during transformation it performs the replacements (In Iris there actually should be no remaining #define statements when glsl-transformer gets the code after preprocessing)
- Write tests that matching still works even after injection
- Test node removal, replacement and injection in local roots (in `TransformationPhaseTest`)
- Write tests that tree walker doesn't break even with child array modification (apart from injection, which is already being tested)

- Unit (and integration) testing of all the parts

- AST structures for GLSL types: Continue work on Tensor (tensor manipulation methods and "widening" until it hits the maximum)
- AST: Structs
- More Phase functionality (abstract declaration replacement into a separate class)
- Error on any tokens in the PREPROCESSOR channel optionally
- Make the Lexer parse #define directives (and others?) better
- Macro expansion (preprocessor) and other functions?

## Other

- Publish Java 8-compatible version as `glsl-transformer-j8` instead of it being the main version? Is this useful?
