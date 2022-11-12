# AST Transformation Examples

TODO: make sure these actually work, or at least compile

## Queries

### By Name

TODO: use IdentifierIndex

### By Class

TODO: use NodeIndex

### By Name and Ancestor Class

TODO: use IdentifierIndex and .getAncestor

### By Class and Structure

TODO: use NodeIndx and AutoHintedMatcher

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
