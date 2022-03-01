# Todo

- Improve transformation phase scheduling by moving to a dependency graph-based system. The transformation phases define which they depend on which forms a DAG that can be traversed starting from transformations that have no dependencies. This can lead to an even more efficient walk packing scheme.
- Test Transformation.append and .merge (and that job parameters are available in phases added this way)
- Call init on handler targets, also other things like before/after search and activation
- AST structures for GLSL types: Continue work on Tensor (tensor manipulation methods and "widening" until it hits the maximum)
- AST: Structs
- More Phase functionality (abstract declaration replacement into a separate class)

- DynamicParseTreeWalker: have transformation phase tell it about movements in the child array it caused to avoid inserting placeholder nodes (and then also avoid even doing the compacting step if it's not necessary)

- Performance ideas:
  - Stop tree walk if all participating walk phases signal that they're done
  - Limit depth of tree walk if participating walk phases agree
  - Limit tree walk to certain nodes (/node types) or exclude some node types from being visited if participating walk phases agree (maybe also dynamically adjust)

## Future Goals

- Make the Lexer parse #define directives (and others?) better
- Macro expansion (preprocessor) and other functions?
- Macro substitution by parsing preprocessor directives

## Testing/Meta

- Exclude generated classes from jacoco coverage reports (because they are very large)
- Test everything that isn't covered yet
- Test WrapIdentifier
- Test job parameter system
- Test ReplaceTerminals static methods
- Test addConcurrentPhase on Transformation
- Test the token filters and all their associated functionality (on transformation manager, printer and all the token filters themselves)
- Test removal/replacement of single terminal node (tests ExtendedTerminalNode)
- Figure out if and how removal of local roots works (nothing needs to be omitted since they are additions, trying to omit them may break. a placeholder still needs to be placed though)
- Write tests that matching still works even after injection (does it work before placeholder cleanup? and after?)
- Test node removal, replacement and injection in local roots (in `TransformationPhaseTest`)

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

## Notes

### Execution with dependency resolution

Initial state:

- Transformation phases know nothing about dependencies.
- Transformations define dependencies between phases (and other transformations) using nodes which consist of a phase and a dependency array.
- Transformations can be assigned dependencies and dependents by the enclosing transformation
- This means the DAG can be composed of phases and other transformations connected by overarching transformations.
- Transformations have a (not necessarily real) root node that has no transformation phase that depends on all other nodes. This is used as the execution entrypoint.
- Transformations have an end node that all nodes depend on (or at least all nodes without other dependencies depend on) that can be used to depend on other nodes in a higher level

Execution setup:

- The ExecutionPlanner calculates the execution order of the phases ahead of time and then stores the result for fast repeated execution.
- It makes sure that all dependencies of each node are executed before it is executed itself.
- It tries to execute as many non-dependent phases at the same time as possible.
- Graph collection: resolve the DAG by looking at the nested nodes in transformations

Node marking:

- Start at the global roots (or all nodes that have no dependents)
- Add all of them to a queue
- Repeat while there are nodes in the queue:
- Remove a node from the queue
- If the node is marked with a lower index than the index removed with the node from the queue, mark it as the higher index and add all dependencies of the node to the queue with the next index
- Idea: depth first search could also be used with the maximum seen depth for each node being the metric

Execution plan:

- The levels of the execution plan are all nodes that have the same index marking. The first level to be executed is the level with the highest index
- This results in rather small levels at the beginning (deep trees) and larger ones at the end
- Possible optimization: group levels into walk-phase and non-walk-phase levels in order to avoid needing to do a walk in some levels
- Avoid double execution of transformations: the execution planner has to globally track the transformation instances being depended on all point them all to the same node
- Remove null-content nodes during planning that are only used for organizing dependencies

Lifecycle:

- Init and reset state is performed by the execution planner after the execution plan has been generated in order to avoid repeated init for nodes accessible through multiple dependencies
- Before the first run: set planner, init, reset state
- Before each following run: set planner, reset state (no init)


TODO: since multiple transformations may reference the same phase/transformation as a dependency, the execution planner has to deduplicate this based on lifecycle user object identities.
