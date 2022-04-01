# Todo

Current to-do items can be found in the issues and the progress [https://github.com/IrisShaders/glsl-transformer/projects/1](project board).

## Future Goals

- Make the Lexer parse #define directives (and others?) better
- Macro expansion (preprocessor) and other functions?
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
