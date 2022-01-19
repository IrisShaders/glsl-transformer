# Documentation

As mentioned earlier, the JavaDoc contains detailled information on individual classes and methods. However it doesn't fully discuss the general function and usage of `glsl-transformer` by API consumers. This section will attempt to give an overview of how it works.

## Lexing and Parsing with ANTLR

TODO:

- how does the grammar work
- ANTLR generates code from the grammar (which makes it part of the API surface)
- lexing (on chars) vs parsing (on tokens)
- details of the glsl lexer and parser
- hidden token channels
- what is a parse tree
- parse tree vs AST (see wikipedia, the terms are not entirely disjoint)
- how can parse nodes be used

## Tree change tracking

TODO:

- the issue with removing nodes without replacement (concurrent modification issues, NPE), is being worked on
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
