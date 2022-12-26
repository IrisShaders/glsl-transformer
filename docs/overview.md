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
