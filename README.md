# GLSL Parsing and Program Transformation with ANTLR4

This is a work in progress library that parses GLSL using an ANTLR4 grammar and provides the facilities for transforming and re-printing the resulting parse tree.

## Credit

This repo is based on https://github.com/gabriele-tomassetti/antlr-mega-tutorial which is part of the nice [Java Setup section of the ANTLR Mega Tutorial](https://tomassetti.me/antlr-mega-tutorial/#java-setup).

The files in `glslang-test` are from [glslang](https://github.com/KhronosGroup/glslang/tree/master/Test) which seems to be appropriately licensed that they can be used here. HLSL and other files have been removed.

## Goals

All of these should be fulfilled at the same time.

- Print out the entire original input and preserve line numbers
- Find certain syntax structures
- Delete, replace or modify certain syntax structures
- Print out the modified input and also preserve line numbers. (each unmodified token should be printed on the line it was parsed at)
- Return lexing/parsing errors if something is wrong
- Optionally return a parsing error if PREPROCESSOR-channel items are detected

## Demonstration

Try it out with `gradle run`. It will parse a GLSL file from `resources` and print the corresponding parse tree. WIP: The tests will be a demonstration of this too.

### An example

One program transformation job that this project wants to achieve is the following. (from coderbot on Discord)

Match all declarations of the form:

`layout (location = 0) in vec4 Name;` or `layout (location = 0) attribute vec4 Name;`

Then:

1. delete that declaration
2. replace all references to that attribute / input with a function call to iris_getModelSpaceVertexPosition()
3. Add the function iris_getModelSpaceVertexPosition() with a specified payload
4. Add a different vertex attribute declaration called iris_Position, Assuming:
5. there are no functions already called iris_getModelSpaceVertexPosition and there are no vertex attributes with the name iris_Position - if any are found, flag an error

## TODO

- More Phase functionality (abstract declaration replacement into a separate class, phase bases for inserting at certain locations)
- AST structures for GLSL types, parse the types themselves
- Unit (and integration) testing of all the parts
- Error on any tokens in the PREPROCESSOR channel optionally

## Misc TODO

- Add license (LGPL3 ?)
- Package/distribute as a library
- GitHub Actions builds, auto run tests
- Introduce semver when it's more stable

## Notes

All files in a directory can be joined using `find . -type f -exec cat {} \; `. The output is printed to the terminal. Some cleanup maybe required.
