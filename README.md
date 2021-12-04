# GLSL Parsing and Program Transformation with ANTLR4

This is a work in progress library that parses GLSL using an ANTLR4 grammar and provides the facilities for transforming and re-printing the resulting parse tree.

See the JavaDoc, the documentation section further down and the tests. Also check ot `Demo.java` in the tests.

## Capabilities

- GLSL Parsing
- Parse tree transformation with phases
- Pattern matching and tree visitation
- Parse tree removal and addition
- New nodes are treated as part of the existing parse tree
- Whitespace-preserving re-printing
- The original input is preserved if no changes are made

## What `glsl-transformer` is not

Note that `glsl-transformer` is **not** a GLSL compiler or even a graphics card driver. It has no or only a very limited semantic understanding of the code. It's primarily intended as a syntactic transformation tool and as such the goal is not to parse the code into an abstract syntax tree (AST). In an AST the syntax is represented in a more abstract way and isn't bound to specific parsing rules. This representation requires a lot more work to implement and is not all of useful in this case. There are AST-like features for specific pieces of syntax though. They are optional and built on top of the parse tree.

`glsl-transformer` does not do semantic validation of the code and will not error on code that is actually invalid for semantic reasons. It will only error on syntax errors. Note however that it supports GLSL 4.6 with some extensions (lik explicit arithmetic types and some others) and won't error when modern features are used even though your driver might not support them. Do not rely on `glsl-transformer` for shader validation, only for syntax transformation.

It also doesn't validate that features aren't used which may not be available in a older GLSL versions. The `#version` directive is not semantically interpreted. It may also fail to parse code that is technically legal in old GLSL version where certain tokens weren't declared as reserved words yet. Just don't use reserved words as tokens. If it's really necessary, preprocess the code by simply replacing the reserved words with some placeholder before parsing.

## Goals

All of these should be fulfilled at the same time.

- Print out the entire original input and preserve line numbers
- Find certain syntax structures
- Delete, replace or modify certain syntax structures
- Print out the modified input and also preserve line numbers. (each unmodified token should be printed on the line it was parsed at)
- Return lexing/parsing errors if something is wrong
- Optionally return a parsing error if PREPROCESSOR-channel items are detected

## Future Goals

- Macro substitution by parsing preprocessor directives
- (Partial) AST features that allow for semantic operations on the tree

## Versioning

This project uses semver for versioning. Once it gets more stable it will move to `major.minor.patch` versions instead of `0.0.major`. If there are frequent breaking API changes then the major version will change frequently. That's how it has to be.

## Credit

Credit for the basics goes to https://github.com/gabriele-tomassetti/antlr-mega-tutorial which is part of the nice [Java Setup section of the ANTLR Mega Tutorial](https://tomassetti.me/antlr-mega-tutorial/#java-setup).

The files in `glslang-test` are from [glslang](https://github.com/KhronosGroup/glslang/tree/master/Test) which seems to be appropriately licensed that they can be used here. HLSL and other files have been removed since they aren't relevant to this project.

Of course all of this wouldn't be possible without ANTLR4 and its contributors, in particular Terence Parr, the creator of ANTLR and author of the ANTLR book. Thanks!

`glsl-transfomer` was created as a hobby project initiated by the need for a more powerful GLSL shader patcher in the wonderful [Iris](https://github.com/IrisShaders/Iris/) shaders mod for Minecraft.

# Usage

## Building and Testing

This is WIP, but the common commands are these:

```
gradle generateGrammarSource
gradle build
gradle test
```

## Example

```java
// setup the tree
var lexer = new GLSLLexer(input);
var commonTokenStream = new CommonTokenStream(lexer);
var parser = new GLSLParser(commonTokenStream);
var tree = parser.translationUnit();

// print before transformations
System.out.println(PrintVisitor.printTree(commonTokenStream, tree));

// setup transformer
var transformer = new PhaseCollector(parser);
transformer.registerTransformationMultiple(ComplexTransformations::registerWith);

// perform transformations
var editContext = transformer.transformTree(tree, commonTokenStream);

// after transformation
System.out.println(PrintVisitor.printTree(commonTokenStream, tree, editContext));
```

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

# Documentation

As mentioned earlier, the JavaDoc contains detailled information on individual classes and methods. However it doesn't fully discuss the general function and usage of `glsl-transformer` by API consumers. This section will attempt to give an overview of how it works.

## Lexing and Parsing with ANTLR

TODO:

- how does the grammar work
- lexing (on chars) vs parsing (on tokens)
- details of the glsl lexer and parser
- hidden token channels
- what is a parse tree
- parse tree vs AST (see wikipedia, the terms are not entirely disjoint)
- how can parse nodes be used

## Edit Context

TODO:

- the issue with removing nodes without replacement (concurrent modification issues, NPE)
- parsing of new nodes as local roots
- new tokens streams for local roots
- omission sets for removed nodes

## Transformation

TODO:

- PhaseCollector, Transformation, Phase
- xpath
- pattern matching
- phase state management (mention issue with parser instance being referenced)
- phase execution checks
- AST features and their usage

## Re-Printing

TODO:

- Tree visit with attributed interval list
- interval iteration, omission set testing
- Whitespace preservation (+demonstration)

# Notes

## Todo

- Lots of documentation (document all public classes)
- Unit (and integration) testing of all the parts
- Test if there are issue with not giving local root nodes `null` parent values
- Figure out if somehow transformations could be compiled/reused without binding them to a particular parser instance
- "Snapshot" tests with the glslang tests (test that exactly the expected errors occur, no more no less)
- Publish as a library with semver (something like 0.0.1 because it's really unstable)
- AST structures for GLSL types: Continue work on Tensor (tensor manipulation methods and "widening" until it hits the maximum)
- More Phase functionality (abstract declaration replacement into a separate class, phase bases for inserting at certain important locations like before directives, before declarations, before defines, before the first function, after everything)
- Error on any tokens in the PREPROCESSOR channel optionally
- Make the Lexer parse #define directives (and others?) better
- Macro expansion (preprocessor) and other functions?

## Misc Todo

- GitHub Actions builds and auto run tests
- Put a build status badge in the README

## Releasing Publishing Notes

All files in a directory can be joined using `find . -type f -exec cat {} \; `. The output is printed to the terminal. Some cleanup maybe required.

Setup signing: (secrets are kept in `~/.gradle/gradle.properties`)

```
gpg --keyring secring.gpg --export-secret-keys > ~/.gnupg/secring.gpg
gpg --list-keys --keyid-format short
```

Build process: (see [publish-on-central](https://github.com/DanySK/publish-on-central) for docs on how it works)

```
gradle build --warning-mode all
gradle releaseJavaMavenOnMavenCentralNexus
```
