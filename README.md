# GLSL Parsing and Program Transformation with ANTLR4

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.douira/glsl-transformer/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.douira/glsl-transformer)
[![javadoc](https://javadoc.io/badge2/io.github.douira/glsl-transformer/javadoc.svg)](https://javadoc.io/doc/io.github.douira/glsl-transformer)
[![Gradle Build](https://github.com/douira/glsl-transformer/actions/workflows/gradle.yml/badge.svg)](https://github.com/douira/glsl-transformer/actions/workflows/gradle.yml)

This is an actively developed library that parses GLSL using an ANTLR4 grammar and provides the facilities for transforming and re-printing the resulting parse tree.

`glsl-transformer` is developed and maintained by [douira](https://github.com/douira). This project was created as a hobby project initiated by the need for a more powerful GLSL shader patcher in the wonderful [Iris](https://github.com/IrisShaders/Iris/) shaders mod for Minecraft. This repo is now part of the IrisShaders org on GitHub.

## Capabilities

`glsl-transformer` is a library for GLSL program transformation. It uses a parser generated with ANTLR based on a custom GLSL grammar to turn shader code into a parse tree. At this point there are two different ways of approaching transformation: The concrete syntax tree (CST) that is directly taken from the parser can be transformed. The other approach turns this CST into an abstract syntax tree (AST) and then does transformations on that. In both types of trees each syntactic piece of the code is represented as a node with children. However, the AST is a little closer to the semantic content of the code rather than how the parser understands it.

For transforming the CST, the API facilitates the creation of composable transformations that can be used to manipulate this parse tree. Transformations can iterate through the parse tree, insert or remove nodes, match patterns and extract information from it. An execution planner optimizes at what time each step of the whole transformation process is performed. After the parse tree has been changed, it is printed back into a string while preserving the original whitespace.

Transforming the AST requires the additional step of building the AST from the parse tree (the CST). Once the AST is available, transforming it is easier and more efficient since traversing the entire tree frequently (or even altogether) is avoided though the use of index structures that allow fast queries for specific identifiers and node types. After transformation the AST is directly printed into a string, optionally with some formatting applied. Since the AST only contains the semantically relevant data from the code, any non-code parts of the original string, such as comments and formatting whitespace, are not present in the re-printed string. AST transformations don't have an execution planner and dependency graph mechanism since they operate on the AST without traversal that needs synchronization and management.

- GLSL Lexing & Parsing
- AST and CST transformation
- Tree walking with visitors and listeners
- Parse tree manipulation and declaration injection
- New nodes are treated as part of the existing parse tree
- AST: Complex pattern matching
- AST: Templating for subtree generation
- AST: Printing with various formatting options
- AST: Index-based queries

Further reading on [Abstract vs Concrete (Parse) Syntax Trees](https://eli.thegreenplace.net/2009/02/16/abstract-vs-concrete-syntax-trees/)

## What `glsl-transformer` is not

Currently, `glsl-transformer` mostly operates only on the syntactic level, even if an AST is constructed. This means it only knows how the code looks and what structure it has to have, not what it means and which structures are legal or not. It only has a limited semantic understanding of the code. Semantic processing of programs can be implemented by API users on a case-by-case basis for specific tasks. The implemented AST does not do type checking or check if the defined structures adhere to the GLSL spec. Implementing full semantics would require building what basically amounts to a GLSL compiler which is way out of scope for this project.

`glsl-transformer` does not do semantic validation of the code and will not error on code that is actually invalid for semantic reasons. It will only error on syntax errors. In particular, it will not error on type errors like assigning a boolean to an integer. It supports GLSL 4.6 with some extensions such as explicit arithmetic types and some others. It won't error on modern syntax features even if your driver doesn't support them. Do not rely on `glsl-transformer` for shader validation, only for syntax transformation.

It also doesn't validate that features aren't used which may not be available in older GLSL versions. The `#version` directive is not semantically interpreted. It may also fail to parse code that is technically legal in old GLSL version where certain tokens weren't declared as reserved words yet. Just don't use reserved words as tokens in this case. If it's really necessary, preprocess the code by simply replacing the reserved words with some placeholder before parsing.

## Versioning

This project uses semver for versioning. If there are frequent breaking API changes then the major version will change frequently. This is the way.

This library is written in Java 16 but without using Java language APIs beyond those of Java 8 and using [jabel](https://github.com/bsideup/jabel) it is compiled to Java 8 compatible classes. The tests are not affected by this and will only be run on the latest Java version (because it's annoying only use Java 8 in the tests). If nobody needs Java 8 support anymore in the future, it will be dropped with a major release. Currently, this is because Minecraft 1.16 uses Java 8.

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

See the Javadocs for API documentation and some API-specific notes. See [the documentation overview](docs/overview.md) for detailed documentation. The tests also illustrate the usage of each individual feature. The `core` package uses `glsl-transformer`'s features and extends them to provide structures for common tasks.

## Todo

See the planned features and other work items in `TODO.md`.
