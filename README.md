# GLSL Parsing and Program Transformation with ANTLR4

This is a work in progress library that parses GLSL using an ANTLR4 grammar and provides the facilities for transforming and re-printing the resulting parse tree.

## Credit

This repo is based on https://github.com/gabriele-tomassetti/antlr-mega-tutorial which is part of the nice [Java Setup section of the ANTLR Mega Tutorial](https://tomassetti.me/antlr-mega-tutorial/#java-setup).

The files in `glsllang-test` are from [glslang](https://github.com/KhronosGroup/glslang/tree/master/Test) which seems to be appropriately licensed that they can be used here. HLSL and other files have been removed.

## Goals

All of these should be fulfilled at the same time.

- Print out the entire original input and preserve line numbers
- Find certain syntax structures
- Delete, replace or modify certain syntax structures
- Print out the modified input and also preserve line numbers
- Return parsing error if something is wrong
- Return a parsing error if PREPROCESSOR-channel items are detected

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

- Cache a single token interval during tree traversal and check if the node is has no other tokens than a single child
- Make a more elegant system for transforming the trees. Some kind of tree matching with actions. (lambdas?)
- AST structures for generating GLSL Code. In particular structs and types. (construction of vector types from lengths)
- low priority: implement more extensions, like `GL_EXT_shader_explicit_arithmetic_types`

## Notes

All files in a directory can be joined using `find . -type f -exec cat {} \; `. The output is printed to the terminal. Some cleanup maybe required.

### Tree printing idea

1. only record added tokens during print visitation. keep a counter of how many tokens have been encountered. record the added tokens to a list that also stored the index of each new token in the final printing list. (without deleted tokens having been removed)
2. then go through all tokens, filtering out deleted ones and adding in added ones. the added ones are taken from a queue as their index in the token list appears and inserted before the next regular token. they are added according to the counter of tokens that doesn't exclude deleted tokens. this means no intervals have to be merged, just a list of indexes and tokens. interval set of the deleted ones still needs to be created.

difficulty:

- keeping track of the current token count. this will still require visitChildren to be used and before, between and after intervals to be inspected.
- how are deletion placeholders handled? can we count the number of tokens they replace? before that wasn't necessary but now it would be.
- is the interval system more flexible for some other application?

This is probably not necessary because of the aforementioned difficulties and the fact that the current interval-joining printer is already very fast. The parsing takes 5 to 20 times longer than printing. Parsing speed is largely determined by ANTLR's performance and how the grammar is constructed.

Caching transformed shaders should be considered. Maybe even on a per-file basis.
