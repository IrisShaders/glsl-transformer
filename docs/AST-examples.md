# AST Transformation Examples

Many of these examples are first prototyped in a test harness and their most relevant parts are presented here. The corresponding tests are noted on each example.

## Queries

### By Name

Using the `IdentifierIndex` nodes can be queried by name.

Query for a node with the identifier `a`, and rename it to `b`.

```java
// IdentifierIndexTest::testIdentifierChange
root.identifierIndex.getOne("a").setName("b")
```

Query a stream of nodes with the identifier `texture` and rename all nodes that are not direct children of a function call expression to `gtexture`.

```java
// MatcherTest::testMatchOnlyUniform
root.process(
  root.identifierIndex.getStream("texture")
    .filter(id -> !(id.getParent() instanceof FunctionCallExpression)),
    id -> {
      id.setName("gtexture");
    });
```

If a prefix query is necessary, a different index like can be used by changing the index factories on `Root` with `PrefixIdentifierIndex.withPrefix`, `PrefixIdentifierIndex.withPermuterm`, and others. There are also variants that use a `LinkedHashSet` in case ordering is of interest. Note that this won't stay ordered if things are added and removed.

### By Class

Query for a node of type `LiteralExpression` and make sure there is exactly one by using `getUnique`.

```java
// NodeIndexTest::testGetUnique
root.nodeIndex.getUnique(LiteralExpression.class)
```

Note that the following code will not return a result (and in fact throw since it's using `getUnique`) because in the default `NodeIndex`, super classes aren't in the index and can't be used in queries.

```java
// NodeIndexTest::testAdd
root.nodeIndex.getUnique(Expression.class)
```

Customizing `Root` to use a super class node index and using it during construction of a specific node makes this possible.

```java
new Root(SuperclassNodeIndex.withUnordered(), IdentifierIndex.withOnlyExact())
```

or by setting a default index factories.

```java
Root.identifierIndexFactory = SuperclassNodeIndex::withUnordered;
```

The same is possible for variants of the identifier index.

### By Name and Ancestor Class

Query for a node with the identifier `foo` and find the containing external declaration.

```java
root.identifierIndex.getOne("a").getAncestor(ExternalDeclaration.class)
```

`getAncestor` also has more signatures that enable skipping some ancestors initially or limiting the search to a certain number of steps. This is useful if for example, a structure like a compound statement is nested multiple times but only a specific one is of interest.

Using `getBranchAncestor`, a predicate can be tested that takes both the current node and the previous child. For example, finding the body of a function definition by its name requires making sure a call to this function within a different function definition is excluded.

```java
// TranslationUnit::getFunctionDefinitionBody
getRoot().identifierIndex.getStream(functionName)
  .map(id -> id.getBranchAncestor(FunctionDefinition.class, FunctionDefinition::getFunctionPrototype))
  .filter(Objects::nonNull).findAny().map(FunctionDefinition::getBody).orElse(null)
```

### By Class and Structure

TODO: use NodeIndex and AutoHintedMatcher

### With Wildcards

TODO: use Matcher's node wildcards

### Node Children Iteration

TODO: iterate TranslationUnit's children

## New Nodes

### External Declaration Injection

TODO: use parseAndInjectNode

### Main Function Statements

TODO: insert using .appendMain

### External Declarations With Template

TODO: insert custom external declaration 10 times using Template

### Cloning

TODO: clone a node and insert it as a sibling

## Node Modification

### Enum Change

TODO: change a token-like enum

### Identifier Change

TODO: Update an Identifier's content

### Child Ordering

TODO: Move Children around in the list

### Non-Sibling Swap

TODO: use ASTNode.swap or similar semantics

### Multi-Step Movement

TODO: Move a node using detach but without removing it from the Root entirely

## Node Removal

### Query and Delete

TODO: find a node by its identifier, find the parent external declaration and detachAndDelete

### Query Stream Filter and Processing

TODO: find all nodes with a type, filter the stream and delete using Root.process
