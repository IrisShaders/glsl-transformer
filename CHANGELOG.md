This file holds the release notes of the next release. Update it together with the version in `gradle.properties`
before running the release workflow, see `docs/development.md`.
[Version]() is automatically replaced with the version being released, e.g. 3.0.0
The first line below the separator is the release title, which is shown as "v[Version]() - Title" on GitHub.
Everything above the separator is ignored. Everything below the title is the body of the GitHub release.
----------
Type Analysis

## Type analysis
This release adds a type inference pass over the AST in the new `ast.typing` package. See the type analysis section of `docs/overview.md` for an introduction and the list of known limitations.

* `node.getType()` returns the type of any expression. `node.getTypeAnalysis()` returns the whole `TypeAnalysis` with `typeOf`, `symbolOf`, `declarationOf`, `signatureOf`, `scopeOf` and `diagnostics`.
* Every expression gets a type and every reference is resolved to its declaration: variables, struct types, user defined and builtin functions including overload resolution.
* The analysis is lenient: it never throws on invalid shaders but gives what cannot be typed the `ErrorType` and records a `Diagnostic`. `new TypeAnalyzer().setStrict(true)` throws a `TypeAnalysisException` for the first problem.
* The implicit conversions and the available builtins follow the `TypeEnvironment`: the version, profile, shader stage and enabled extensions. It is derived from the tree and can be set with `root.setTypeEnvironment(...)`, which is necessary to provide the shader stage.
* The builtin functions, variables and constants of GLSL 4.60, GLSL ES 3.20, the compatibility profile and a number of extensions are defined in GLSL in resource files.
* The analysis is cached in the `Root` and recomputed after the tree changes.
* The type system consists of the sealed interface `Type` with `NumericType`, `FixedType`, `ArrayType`, `StructType`, `InterfaceBlockType`, `SubroutineType`, `StringType` and `ErrorType`.

## Bug fixes with behavior changes
* `NumericType.isVector()` is false for scalars. Exactly one of `isScalar()`, `isVector()` and `isMatrix()` is true for each type.
* `NumericType.ofTokenType` throws an `IllegalArgumentException` for token types that are not numeric types instead of an `ArrayIndexOutOfBoundsException` or returning `null`.
* `LiteralExpression.isNonZero()` is false for the literal `false`.
* The setters of `LiteralExpression` that change the kind of the literal reset the state of the other kinds: `setString` resets the integer format, the other setters reset the string.
* Literals of the types `int8_t` and `uint8_t` cannot be constructed anymore since there is no syntax for them. Previously the printer threw when printing them.
* `LiteralExpression.getDefaultNumericValue(NumericType)` returns an expression of exactly the given type: a literal with the right suffix (`0u`, `0.0lf`) for scalars and a constructor call (`vec3(0.0f)`, `int8_t(0)`) for everything else. Previously a scalar `int`, `float` or `bool` literal was returned for every type, which is not a valid initializer for vectors and matrices. `getDefaultNumericValue(NumberType)` returns `0u` instead of `0` for unsigned integers.
* The default implementation of `ASTVisitor.visitLiteralExpression` does not throw for string literals anymore. It passes the literal type (`StringType.INSTANCE` for strings) to `visitData` first.
* `NumericType.getImplicitCasts()` and `isImplicitlyCastableTo` follow the conversion tables of `GL_EXT_shader_explicit_arithmetic_types` for the latest GLSL version. Use `Conversions.canImplicitlyConvert` to check conversions for a specific version.

## Parsing fixes
* `#extension all : warn` and `#extension all : disable` are parsed. Previously the name `all` was rejected because it is also a keyword of the pragma directives.

## Traversal and printing fixes (behavior changes)
* The default traversal in `ASTVisitor` now visits the array specifier of `NumericTypeSpecifier`, `FixedTypeSpecifier`, `TypeReference` and `StructSpecifier` (`vec3[N] x`). Previously these nodes were skipped, so they kept their old root and stayed in its indexes when the surrounding tree was moved or deleted. Custom visitors and listeners now see `ArraySpecifier` nodes (and their contents) they did not see before.
* The default traversal now visits the declaration in a while loop condition (`while (bool b = f())`), with the same consequences.
* `ASTVisitor.visitTranslationUnit` returns the aggregated result of the version statement and all external declarations instead of only the result of the version statement.
* `EQUAL` and `NOT_EQUAL` have precedence 8 instead of sharing precedence 7 with the relational operators. The printer now emits the required parentheses for trees like `a < (b == c)`, which were previously printed as `a < b == c` and reparsed into a different tree.

## Migration from 3.0.0-pre3
This release contains breaking changes. Most of them are renames that can be applied mechanically.

| Before | After |
| --- | --- |
| `util.Type` | `ast.typing.NumericType`, the constants have the same names |
| `util.Type.NumberType` | `ast.typing.NumberType` |
| `Type.STRING`, `NumberType.STRING` | removed, string literals have the type `StringType.INSTANCE` |
| `BuiltinNumericTypeSpecifier` | `NumericTypeSpecifier` |
| `BuiltinFixedTypeSpecifier` | `FixedTypeSpecifier` |
| `BuiltinFixedTypeSpecifier.BuiltinType` | `ast.typing.FixedType`, the constants have the same names |
| `BuiltinFixedTypeSpecifier.BuiltinType.TypeKind` | `FixedType.TypeKind` |
| `BuiltinFixedTypeSpecifier.BuiltinType.ValueFormat` | `ast.typing.NumberType` |
| `SpecifierType.BUILTIN_NUMERIC`, `SpecifierType.BULTIN_FIXED` | `SpecifierType.NUMERIC`, `SpecifierType.FIXED` |
| `ASTVisitor.visitBuiltinNumericTypeSpecifier`, `visitBuiltinFixedTypeSpecifier` | `visitNumericTypeSpecifier`, `visitFixedTypeSpecifier` |
| `getType()` on `FunctionParameter`, `TypeAndInitDeclaration`, `StructMember` and `IterationConditionInitializer` | `getSpecifiedType()` |
| `LiteralExpression.getType()` | `getNumericType()`, or `getLiteralType()` to also handle strings |
| `LiteralExpression.getDefaultValue(...)` | `getDefaultNumericValue(...)`, which returns an `Expression` |
| `initialResult()` in `GeneralASTVisitor`, `ASTBaseVisitor`, `ASTVoidVisitor` and `ASTPrinterBase` | removed, remove overrides |
| `ASTPrinterBase.visitSafe(ASTNode)` returning `boolean` | `visitSafeSignal(ASTNode)` |
| grammar rules `builtinTypeSpecifierParseable` and `builtinTypeSpecifierFixed` | `numericTypeSpecifier` and `fixedTypeSpecifier`, with the generated parser methods, context classes and visitor and listener methods named accordingly |
| `org.apache.commons.collections4.*` (the vendored trie classes) | `io.github.douira.glsl_transformer.vendor.commons.collections4.*` |

Things to watch out for:

* `getType()` on the four node classes above was renamed to `getSpecifiedType()`, while `ASTNode.getType()` now exists on every node and returns the type from the type analysis. Code like `var type = parameter.getType()` still compiles but gets something different. Check all uses of `getType()` on these nodes.
* An override of a renamed visitor method that has no `@Override` annotation still compiles but is not called anymore.
* `LiteralExpression.getNumberType()` and `getNumericType()` throw for string literals. `isNonZero()` and `isPositive()` return false for string literals instead of throwing.
* `LiteralExpression.getDefaultNumericValue(NumericType)` can return a node with children and therefore has to be called while a root is active for building, for example inside of `root.indexNodes(...)`.
* The default traversal visits more nodes and `visitLiteralExpression` passes different data, see the sections above.
* The project is built with a Java 21 toolchain.

The public fields `NumericTypeSpecifier.type`, `FixedTypeSpecifier.type`, `StorageQualifier.storageType`, `VersionStatement.version`, `VersionStatement.profile` and `ExtensionDirective.behavior` remain. The new setters next to them additionally invalidate the cached type analysis.

According to the API report (`./gradlew apiReport`), compared to 3.0.0-pre3 and not counting the classes generated from the grammar and the vendored classes: 33 classes were added, 7 were removed (the renamed and moved ones in the table) and 23 were modified, 17 of them incompatibly. Of the generated grammar classes 2 were removed, 2 were added and 4 were modified incompatibly, all because of the two renamed rules.

## Other changes
* pragma optionNV support by @drouarb in https://github.com/IrisShaders/glsl-transformer/pull/21
* taskNV storage qualifier & enableMeshShaders flag by @drouarb in https://github.com/IrisShaders/glsl-transformer/pull/22
* Fix resource paths as noted here (but using my own implementation): https://github.com/IrisShaders/glsl-transformer/pull/20
* Add tests to make sure lexer options behave correctly (there was no bug, just lacking coverage)
* Add the `apiReport` Gradle task and a coverage gate for the type analysis, see `docs/development.md`.

**Full Changelog**: https://github.com/IrisShaders/glsl-transformer/compare/v3.0.0-pre2...v[Version]()
