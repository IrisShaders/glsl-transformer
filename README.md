# GLSL Parsing and Program Transformation with ANTLR4

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.douira/glsl-transformer/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.douira/glsl-transformer)
[![javadoc](https://javadoc.io/badge2/io.github.douira/glsl-transformer/javadoc.svg)](https://javadoc.io/doc/io.github.douira/glsl-transformer)
[![Gradle Build](https://github.com/douira/glsl-transformer/actions/workflows/gradle.yml/badge.svg)](https://github.com/douira/glsl-transformer/actions/workflows/gradle.yml)

This is an actively developed library that parses GLSL using an ANTLR4 grammar and provides the facilities for transforming and re-printing the resulting parse tree.

`glsl-transformer` is developed and maintained by [douira](https://github.com/douira). This project was created as a hobby project initiated by the need for a more powerful GLSL shader patcher in the wonderful [Iris](https://github.com/IrisShaders/Iris/) shaders mod for Minecraft. This repo is now part of the IrisShaders org on GitHub.

## Capabilities

`glsl-transformer` is a library for GLSL program transformation. It uses a parser generated with ANTLR based on a custom GLSL grammar to turn shader code into a parse tree, called the concrete syntax tree (CST). The CST is then turned into an abstract syntax tree (AST) and transformations are performed on it.

Transforming the AST requires building the AST from the parse tree (the CST). Once the AST is available, transforming it is easier and more efficient since traversing the entire CST frequently (or even altogether) is avoided though the use of index structures that allow fast queries for specific identifiers and node types. After transformation the AST is printed into a string, optionally with some formatting applied. Since the AST only contains the semantically relevant data from the code, any non-code parts of the original string, such as comments and formatting whitespace, are not present in the re-printed string.

- GLSL Lexing & Parsing
- AST transformation
- Tree walking with visitors and listeners
- Complex pattern matching
- Templating for subtree generation
- Printing with various formatting options
- Index-based queries

Further reading on [Abstract vs Concrete (Parse) Syntax Trees](https://eli.thegreenplace.net/2009/02/16/abstract-vs-concrete-syntax-trees/)

## What `glsl-transformer` is not

Currently, `glsl-transformer` mostly operates only on the syntactic level, even if an AST is constructed. This means it only knows how the code looks and what structure it has to have, not what it means and which structures are legal or not. It only has a limited semantic understanding of the code. Semantic processing of programs can be implemented by API users on a case-by-case basis for specific tasks. The implemented AST does not do type checking or check if the defined structures adhere to the GLSL spec. Implementing full semantics would require building what basically amounts to a GLSL compiler which is out of scope for this project.

`glsl-transformer` does not do semantic validation of the code and will not error on code that is actually invalid for semantic reasons. It will only error on syntax errors. In particular, it will not error on type errors like assigning a boolean to an integer. It supports GLSL 4.6 with some extensions such as explicit arithmetic types and some others. It won't error on modern syntax features even if your driver doesn't support them. Do not rely on `glsl-transformer` for shader validation, only for syntax transformation.

It also doesn't validate that features aren't used which may not be available in older GLSL versions. The `#version` directive is not semantically interpreted. It may also fail to parse code that is technically legal in old GLSL version where certain tokens weren't declared as reserved words yet. Just don't use reserved words as tokens in this case. If it's really necessary, preprocess the code by simply replacing the reserved words with some placeholder before parsing.

`glsl-transformer` does not preprocess the code before parsing and will throw parsing exceptions if it encounters preprocessor directives like `#define`, `#if`, and `#include` etc. A few GLSL-specific and other directives such as `#version`, `#extension`, `#pragma`, `#line`, and `#custom` are supported as they have special roles. You can use [`glsl-preprocessor`](https://github.com/IrisShaders/glsl-preprocessor) to preprocess the GLSL code while preserving relevant directives before passing it to `glsl-transformer`.

## Versioning

This project uses semver for versioning. If there are frequent breaking API changes then the major version will change frequently. This is the way.

The library is written in and published for Java 16.

## Credit

Credit for the basics goes to https://github.com/gabriele-tomassetti/antlr-mega-tutorial which is part of the nice [Java Setup section of the ANTLR Mega Tutorial](https://tomassetti.me/antlr-mega-tutorial/#java-setup).

The files in `glslang-test` are from [glslang](https://github.com/KhronosGroup/glslang/tree/master/Test) which seems to be appropriately licensed that they can be used here. HLSL and other files have been removed since they aren't relevant to this project.

Of course all of this wouldn't be possible without ANTLR4 and its contributors, in particular Terence Parr, the creator of ANTLR and author of the ANTLR book. Thanks!

This project includes parts of Apache Commons Collections in its respective package. Not all source files have been included since only those related to `Trie` are needed. Apache Commons Collections is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

## Support

If something breaks, please make an issue report with the details so I can fix the issue and add a test case. For more direct support, you can also ask in the `#glsl-transformer` channel in the [Iris discord server](https://discord.gg/jQJnav2jPu). I'm also interested in hearing from anyone using this library in their projects!

# Usage

## Building and Testing

```bash
# generate grammar sources to enable IDE usage
gradle generateGrammarSource

# building also runs the tests
gradle build

# run the tests (also generates the jacoco coverage report)
gradle test

# generate javadoc
gradle javadoc
```

## Example

```java
// setup a transformer
var transformer = new ASTTransformer<>();

// set the transformation
transformer.setTransformation((translationUnit, root) -> {
  // find addition expressions
  root.nodeIndex.getStream(AdditionExpression.class).forEach(System.out::println);

  // delete all statements that contain the identifier "bar"
  root.process(
    node.identifierIndex.getStream("bar"),
    ASTNode::detachAndDelete
  );

  // find the "baz" reference expression
  System.out.println(
    root.identifierIndex.getOneReferenceExpression("baz").getIdentifier().getName());

  // add a line to the program
  translationUnit.parseAndInjectNode(
    transformer, ASTInjectionPoint.BEFORE_FUNCTIONS, "uniform int foo = 4;");
});

// after transformation
System.out.println(transformer.transform(input));
```

# Documentation

See the Javadocs for API documentation and some API-specific notes. See [the documentation overview](docs/overview.md) for detailed documentation. The tests also illustrate the usage of each individual feature.

## Todo

See the planned features and other work items in `TODO.md`.
