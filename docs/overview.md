# Documentation Overview

As mentioned earlier, the JavaDoc contains detailed information on individual classes and methods. However, it doesn't fully discuss the general function and usage of `glsl-transformer` by API consumers. This section will attempt to give an overview of how it works.

The documentation and code examples in it are governed by the same license the rest of the code is also under (GPLv3 with exception).

The ANTLR4 grammars can be found in `src.main.antlr` and give a helpful overview of how lexing and parsing works. Note that the AST will be significantly different from the parsed CST.

## Packages

- `ast`: The AST nodes and AST transformation classes
  - `data`: Shared classes for managing data structures in the AST
  - `node`: The `ASTNode` class and all its subclasses that form the AST structure
  - `print`: Classes for printing the AST back into a string and formatting it
  - `query`: `Root` and related index classes that allow for accelerated AST traversal
  - `transform`: The `ASTTransformer` and subclasses for single or multiple sources, classes for parsing the CST and building the AST
  - `traversal`: General AST visiting, listening and walking classes
  - `typing`: The type system and the type analysis that determines the type of every expression
- `basic`: Base classes for the parser and lexer, job parameter classes, abstractions for parsing and transformation
- `token_filter`: Token filters that can be applied between lexing and parsing
- `util`: Other classes, polyfills for Java 8 APIs, generic utility classes

## AST Transformation Pipeline

The source string is parsed with a generated ANTLR parser. The resulting CST is built into an AST and the corresponding `Root` instance builds indexes over the tree nodes in the process. Now a transformation can make queries, match nodes and insert new structures. The changed AST is then printed back into a string.

There are [a number of AST transformation examples](AST-examples.md).

## Principles of AST Transformation in `glsl-transformer`

### 1. Use Queries

Use queries to `Root`'s indexes whenever possible. These indexes are built anyway and usually make finding certain nodes or structures much faster. The `IdentifierIndex` can find identifiers by their content, even just a prefix or also other parts if you choose a more powerful backing structure like `PermutermTrie` for the identifier index. The `NodeIndex` can find nodes by their classes. In combination with `ASTNode::getAncestor*` methods, most tree traversal operations can be reduced to queries and brief filtering operations. Most index-related methods can return streams so filtering and combining streams is helpful.

### 2. Use AST Node Fields

AST nodes with children expose their children nodes as lists or individual fields. Descending into nodes when necessary can easily be done by simply accessing their fields and checking the classes of the children if they are ambiguous.

### 3. Use `Matcher`

When you have to find certain structures like `int foo = 4;` in the tree, it's much easier to match structures using a prepared `AutoHintedMatcher` (a subclass that automatically tells `Root` which identifier to query for) instance than traversing or walking the tree. The AST is not designed to be walked by users of the API and doing so is potentially slow. `Matcher` has features for identifier, list and (predicated) class wildcards.

### 4. Mutate the AST

The AST structure is built around being mutable. Changing it is easy and efficient. Lists of nodes can also be easily edited. The root is updated automatically when nodes are removed or added using the provided methods.

### 5. Use Cloning and `Template`

AST nodes can be cloned, either into a new root or into the current root. This is easier than manually duplicating the content of nodes. Furthermore, structures that need to be inserted repeatedly with small variations can use `Template` which works more efficiently than repeatedly parsing a string. It does so by hooking into the cloning process of a node and inserting custom provided nodes in the specified places.

### 6. Keep the Structures Consistent

If you are extending or modifying the AST classes like `ASTVisitor`, `ASTNode` or any of its subclasses, `ASTBuilder` or `Root`, make sure to keep the AST structure consistent or there will breakage. The necessary invariants are described in `ASTNode`'s javadoc. Basically, the root, parent and replacement method reference should correspond (except for in some specific cases).

One place in which this consistency is actively enforced is in `Root`: Calling AST node constructors without first starting (and later cleaning up) a session with an appropriate root will often cause an exception.

### 7. Avoid Creating New Roots

When creating new AST nodes through a template, calling constructors, cloning or parsing there is usually the option to create a new `Root`, something "separate", or to re-use an existing root. Since each `TranslationUnit`, the top-most node of a complete AST, has a reference to a root, creating the new nodes with the existing root easy. Nodes created with separate instances will need to be (automatically) re-indexed, which involves walking the subtree and index operations, once they are added to a tree with a different root which should be avoided.

## Type Analysis

The `ast.typing` package contains a type inference pass over the AST. It determines the type of every expression, resolves every reference to its declaration and reports problems instead of throwing.

```java
var expression = root.nodeIndex.getOne(AdditionExpression.class);
Type type = expression.getType(); // for example NumericType.F32VEC3

TypeAnalysis analysis = expression.getTypeAnalysis();
analysis.typeOf(node);        // the type of an expression or of what a node declares
analysis.symbolOf(node);      // the variable, function or type a node refers to or declares
analysis.declarationOf(node); // the node that declares what a reference or call refers to
analysis.signatureOf(call);   // the overload a function call was resolved to
analysis.scopeOf(node);       // the innermost scope around a node
analysis.diagnostics();       // the problems that were found
```

### Types

`Type` is a sealed interface. Types are immutable and can be compared with `equals`.

| Type | Description |
| --- | --- |
| `NumericType` | The scalar, vector and matrix types. This is the enum that is also used by `NumericTypeSpecifier` and `LiteralExpression`. |
| `FixedType` | `void` and the opaque types like samplers and images. This is the enum that is also used by `FixedTypeSpecifier`. |
| `ArrayType` | An array with an element type and an `ArraySize`, which is known, unsized (`float a[]`) or unknown (the size expression could not be evaluated). Arrays of arrays are nested. |
| `StructType` | A struct with its ordered fields. Structs are nominal and compared by identity. |
| `InterfaceBlockType` | The type of an instance of an interface block. |
| `SubroutineType` | The type of a subroutine variable. |
| `StringType` | The type of string literals, which exist for debug printf extensions. |
| `ErrorType` | The type of anything that could not be typed. |

### Errors

The analysis is lenient. It never throws on invalid shaders: something that cannot be typed gets the `ErrorType` and a `Diagnostic` with a `DiagnosticCode`, a message and the node is recorded. The error type absorbs further errors, so that one mistake produces one diagnostic. With `new TypeAnalyzer().setStrict(true)` a `TypeAnalysisException` that carries the diagnostic is thrown for the first problem instead.

### Environment

What a shader means depends on things that are not all part of the tree. The `TypeEnvironment` consists of the version, the profile, the shader stage and the enabled extensions. It decides which implicit conversions exist (none in GLSL 1.10, `int` to `uint` only from 4.00 on, and so on) and which builtin functions and variables are available.

By default, the environment is derived from the version statement and the extension directives of the translation unit. As the specification requires, a translation unit without a version statement is GLSL 1.10. Trees that are not translation units, like a single parsed expression, are typed as the latest version. The AST does not know which stage a shader is for. Without a stage the builtins of all stages are available. Set the environment to provide the stage or to override anything else:

```java
root.setTypeEnvironment(TypeEnvironment.of(translationUnit).withStage(ShaderStage.FRAGMENT));

// or without the root
var analysis = new TypeAnalyzer().setEnvironment(environment).analyze(translationUnit);
```

### Caching

`node.getType()` and `node.getTypeAnalysis()` analyze the whole tree the node is in. The result is cached in the `Root` and discarded when a node of the root is added, removed, moved or renamed, or when a setter of a node is used. Writing to public fields of nodes directly (`specifier.type = ...`) is not noticed. Use the setters (`setNumericType`, `setFixedType`, `setStorageType`, `setVersion`, `setBehavior`, the setters of `LiteralExpression`) or call `root.invalidateTypeAnalysis()` after such changes.

A `TypeAnalysis` describes the tree as it was when it was analyzed. Don't hold on to it across modifications of the tree, get it from the node again instead.

### Builtins

The builtin functions, variables, constants and types are defined in GLSL in the resource files in `ast/typing/builtins`. They cover GLSL 4.60 and GLSL ES 3.20 with the compatibility profile and a number of extensions, among them the explicit arithmetic types, 64 bit integers, half floats, subgroups, memory scope semantics, ray tracing and debug printf. The format of the files is described in the documentation of `BuiltinRegistry`.

### Limitations

The analysis is a type inference, not a complete validation of a shader. The following is known to be imprecise. Each item is pinned by a test, mostly in the cases of `TypeAnalyzerTest` with names that start with `imprecision`.

- The preprocessor is not evaluated. Uses of macros are reported as undeclared identifiers and both branches of conditional compilation are analyzed.
- Only integer and boolean constant expressions are evaluated. The sizes of arrays that depend on floating point constants, on the values of builtin constants like `gl_MaxDrawBuffers` or on layout qualifiers (the inputs of geometry shaders) are unknown and accesses to them are not checked against the bounds.
- Extension directives are not positional. An extension that is enabled anywhere in the translation unit is enabled for all of it.
- Types and syntax are not gated on the version. `double`, the explicitly sized types, arrays of arrays, initializer lists and swizzles of scalars are accepted in every version. Only the implicit conversions and the builtins depend on the version.
- Functions with the same name in enclosing scopes stay visible. A call to a function that has user defined overloads still resolves to a builtin overload if none of the user defined overloads fit, as it does in glslang.
- Qualifiers are only checked for assignability: `const`, `uniform`, global `in` and `attribute` variables cannot be assigned to. Writes to `varying` variables in fragment shaders and to `readonly` buffers are not reported. Precision, interpolation and layout qualifiers are not checked.
- Control flow is not checked: missing return statements, recursion, `break` and `continue` outside of loops, `discard` outside of fragment shaders, duplicate case labels and the types of case labels compared to the switch value.
- Restrictions on opaque types are not checked, for example samplers in structs or as local variables.
- The names of interface blocks are not checked for conflicts.
- The compatibility profile state like `gl_ModelViewMatrix` is available in GLSL 1.40, as if `GL_ARB_compatibility` was enabled.
- Overload resolution ranks the conversions of `out` parameters like those of `in` parameters.
- The grammar parses a statement that only consists of a name or an indexed name, like `a;` or `a[i];`, as a declaration without declared names. Such a statement is not reported as an error if the name is a variable, but it is also not typed as an expression.
- The grammar cannot parse `#extension all : ...` and does not have the separate texture and sampler types of Vulkan (`texture2D`, `sampler`, `subpassInput`). They are parsed as references to undeclared types.
- Builtins of extensions that are not listed in the builtin definition files are reported as undeclared. `TypeCorpusTest.allowlist` lists the files of the glslang test suite that are affected by this.
