# GLSL Parsing and Program Transformation with ANTLR4

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.douira/glsl-transformer/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.douira/glsl-transformer)
[![javadoc](https://javadoc.io/badge2/io.github.douira/glsl-transformer/javadoc.svg)](https://javadoc.io/doc/io.github.douira/glsl-transformer)
[![Gradle Build](https://github.com/douira/glsl-transformer/actions/workflows/gradle.yml/badge.svg)](https://github.com/douira/glsl-transformer/actions/workflows/gradle.yml)

This is an actively being developed library that parses GLSL using an ANTLR4 grammar and provides the facilities for transforming and re-printing the resulting parse tree.

`glsl-transformer` is developed and maintained by [douira](https://github.com/douira). This project was created as a hobby project initiated by the need for a more powerful GLSL shader patcher in the wonderful [Iris](https://github.com/IrisShaders/Iris/) shaders mod for Minecraft. This repo is now part of the IrisShaders org on GitHub.

## Capabilities

`glsl-transformer` is a library for GLSL program transformation. It uses a parser generated with ANTLR based on a custom GLSL grammar to turn shader code into a parse tree. In this tree each syntactic piece of the code is represented as a node with children. The API facilitates the creation of composable transformations that can be used to manipulate this parse tree. Transformations can iterate through the parse tree, insert or remove nodes, match patterns and extract information from it. An execution planner optimizes at what time each step of the whole transformation process is performed. After the parse tree has been changed, it is printed back into a string while preserving the original whitespace.

- GLSL Lexing & Parsing
- Composable parse tree transformations
- Pattern matching and tree visitation
- Parse tree manipulation and declaration injection
- New nodes are treated as part of the existing parse tree
- Whitespace-preserving re-printing
- The original input is preserved if no changes are made
- Limited support for AST generation and printing

## What `glsl-transformer` is not

Currently, `glsl-transformer` mostly operates only on the syntactic level. This means it only knows how the code looks and what structure it has to have, not what it means and which structures are legal or not. It has no or only a very limited semantic understanding of the code. Semantic processing of programs can be implemented by API users on a case-by-case basis for specific tasks. Building a simple AST that is abstracted far away from the actual GLSL spec is an interesting possibility for future development of this library. However, implementing full semantics would require building what basically amounts to a GLSL compiler which is way out of scope for this project. 

`glsl-transformer` does not do semantic validation of the code and will not error on code that is actually invalid for semantic reasons. It will only error on syntax errors. In particular, it will not error on type errors like assigning a boolean to an integer. It supports GLSL 4.6 with some extensions such as explicit arithmetic types and some others. It won't error on modern syntax features even if your driver doesn't support them. Do not rely on `glsl-transformer` for shader validation, only for syntax transformation.

It also doesn't validate that features aren't used which may not be available in a older GLSL versions. The `#version` directive is not semantically interpreted. It may also fail to parse code that is technically legal in old GLSL version where certain tokens weren't declared as reserved words yet. Just don't use reserved words as tokens in this case. If it's really necessary, preprocess the code by simply replacing the reserved words with some placeholder before parsing.

Further reading on [Abstract vs Concrete (Parse) Syntax Trees](https://eli.thegreenplace.net/2009/02/16/abstract-vs-concrete-syntax-trees/)

## Versioning

This project uses semver for versioning. If there are frequent breaking API changes then the major version will change frequently. This is the way.

This library is written in Java 16 and using [jabel](https://github.com/bsideup/jabel) compiled to Java 8 compatible classes. This means it doesn't use any newer Java language APIs. The tests are not affected by this and will only be run on the latest Java version (because it's annoying only use Java 8 in the tests). If nobody needs Java 8 support anymore in the future, it will be dropped with a major release. Currently, this is because Minecraft 1.16 uses Java 8.

## Credit

Credit for the basics goes to https://github.com/gabriele-tomassetti/antlr-mega-tutorial which is part of the nice [Java Setup section of the ANTLR Mega Tutorial](https://tomassetti.me/antlr-mega-tutorial/#java-setup).

The files in `glslang-test` are from [glslang](https://github.com/KhronosGroup/glslang/tree/master/Test) which seems to be appropriately licensed that they can be used here. HLSL and other files have been removed since they aren't relevant to this project.

Of course all of this wouldn't be possible without ANTLR4 and its contributors, in particular Terence Parr, the creator of ANTLR and author of the ANTLR book. Thanks!

## Support

If something breaks, please make an issue report with the details so I can fix the issue and add a test case. Until 1.0 is released, expect many features to be untested. For more direct support, you can also ask in the `glsl-transformer` thread in the [Iris discord server](https://discord.gg/jQJnav2jPu). I'm also interested in hearing from anyone using this library in their projects!

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
// setup a manager
var manager = new TransformationManager<>();

// register a transformation
manager.addConcurrent(transformation);

// after transformation
System.out.println(manager.transform(string));
```

## Permitted Parse Tree Operations

- Adding a new parsed node anywhere (as a new local root)
- Removing any node with its entire subtree
- Replacing any node (remove, then add)
- Moving a local root node anywhere
- Moving a node strictly inside its local root scope (not a subscope)
- Removing or moving a non-local-root node without touching its subtree, though this may break things that rely on grammar rules being followed

### Not permitted (yet)

- Moving a node out of the scope of its local root (it would be missing a token stream)
- Entirely removing a local root node and replacing it with a new one, giving it the previous node's children

# Documentation

See the Javadocs for API documentation and some API-specific notes. See [the documentation overview](docs/overview.md) for detailed documentation. The tests also illustrate the usage of each individual feature. The `core` package uses `glsl-transformer`'s features and extends them to provide structures for common tasks.

# Notes

## Todo

See the planned features and other work items in `TODO.md`.

## Releasing Publishing

See [publish-on-central](https://github.com/DanySK/publish-on-central) for docs on how it works.

1. Make sure the javadocs are up to date and mostly complete `gradle javadoc`
2. Test that everything builds ok `gradle build --warning-mode all`
3. Run the tests `gradle test`
4. Bump the version in `build.gradle` and commit it
5. Make a tag `git tag vX.Y.Z`
6. Upload the tag `git push --tags`
7. Release the build `gradle releaseJavaMavenOnMavenCentralNexus`

## Other

All files in a directory can be joined using `find . -type f -exec cat {} \; `. The output is printed to the terminal. Some cleanup maybe required. (this is sometimes useful when working with external test files)

Setup signing: (secrets are kept in `~/.gradle/gradle.properties`)

```
gpg --keyring secring.gpg --export-secret-keys > ~/.gnupg/secring.gpg
gpg --list-keys --keyid-format short
```
