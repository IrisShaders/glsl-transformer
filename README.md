# GLSL Parsing and Program Transformation with ANTLR4

This is a work in progress library that parses GLSL using an ANTLR4 grammar and provides the facilities for transforming and re-printing the resulting parse tree.

## Credit

This repo is based on https://github.com/gabriele-tomassetti/antlr-mega-tutorial which is part of the nice [Java Setup section of the ANTLR Mega Tutorial](https://tomassetti.me/antlr-mega-tutorial/#java-setup).

The files in `glslang-test` are from [glslang](https://github.com/KhronosGroup/glslang/tree/master/Test) which seems to be appropriately licensed that they can be used here. HLSL and other files have been removed.

## Goals

All of these should be fulfilled at the same time.

- Print out the entire original input and preserve line numbers
- Find certain syntax structures
- Delete, replace or modify certain syntax structures
- Print out the modified input and also preserve line numbers. (each unmodified token should be printed on the line it was parsed at)
- Return lexing/parsing errors if something is wrong
- Optionally return a parsing error if PREPROCESSOR-channel items are detected

## Demonstration

Try it out with `gradle run`. It will parse a GLSL file from `resources` and print the corresponding parse tree. WIP: The tests will be a demonstration of this too.

### An example

One program transformation job that this project wants to achieve is the following. (from coderbot on Discord)

Match all declarations of the form:

`layout (location = 0) in vec4 Name;` or `layout (location = 0) attribute vec4 Name;`

Then:

1. delete that declaration
2. replace all references to that attribute / input with a function call to iris_getModelSpaceVertexPosition()
3. Add the function iris_getModelSpaceVertexPosition() with a specified payload
4. Add a different vertex attribute declaration called iris_Position, Assuming:
5. there are no functions already called iris_getModelSpaceVertexPosition and there are no vertex attributes with the name iris_Position - if any are found, flag an error

## TODO

- Add matching system that can trigger a callback method in a phase at the right moment.
- AST structures for generating GLSL Code. In particular structs and types. (construction of vector types from lengths)
- low priority: implement more extensions, like `GL_EXT_shader_explicit_arithmetic_types`

## Notes

All files in a directory can be joined using `find . -type f -exec cat {} \; `. The output is printed to the terminal. Some cleanup maybe required.

### Tree printing idea

1. only record added tokens during print visitation. keep a counter of how many tokens have been encountered. record the added tokens to a list that also stored the index of each new token in the final printing list. (without deleted tokens having been removed)
2. then go through all tokens, filtering out deleted ones and adding in added ones. the added ones are taken from a queue as their index in the token list appears and inserted before the next regular token. they are added according to the counter of tokens that doesn't exclude deleted tokens. this means no intervals have to be merged, just a list of indexes and tokens. interval set of the deleted ones still needs to be created.

difficulty:

- keeping track of the current token count. this will still require visitChildren to be used and before, between and after intervals to be inspected.
- how are deletion placeholders handled? can we count the number of tokens they replace? before that wasn't necessary but now it would be.
- is the interval system more flexible for some other application?

This is probably not necessary because of the aforementioned difficulties and the fact that the current interval-joining printer is already very fast. The parsing takes 5 to 20 times longer than printing. Parsing speed is largely determined by ANTLR's performance and how the grammar is constructed.

Caching transformed shaders should be considered. Maybe even on a per-file basis.

## Thoughts on tree matching
After parsing the code, I have a parse tree that I'm basically treating as an AST. I'm try to avoid actually transforming the parse tree into a real AST where the syntax elements are represented in java because that would probably break the line number preserving behavior of the printing that I have currently. It might be possible but it's basically just too much work. Writing the grammar was a lot already.

This is the parse tree for `layout (location = 0) attribute vec4 Name;`:
```
. 2/4 in TranslationUnitContext
. layout(location=0)attributevec4Name;
. . ---ExternalDeclarationContext
. . . ---DeclarationContext
. . . 0/2 in DeclarationContext
. . . layout(location=0)attributevec4Name
. . . . ---InitDeclaratorListContext
. . . . 0/2 in InitDeclaratorListContext
. . . . layout(location=0)attributevec4
. . . . . ---FullySpecifiedTypeContext
. . . . . 0/2 in FullySpecifiedTypeContext
. . . . . layout(location=0)attribute
. . . . . . ---TypeQualifierContext
. . . . . . 0/2 in TypeQualifierContext
. . . . . . layout(location=0)
. . . . . . . ---LayoutQualifierContext
. . . . . . . 0/4 in LayoutQualifierContext
. . . . . . . layout
. . . . . . . layout
. . . . . . . 1/4 in LayoutQualifierContext
. . . . . . . (
. . . . . . . (
. . . . . . . 2/4 in LayoutQualifierContext
. . . . . . . location=0
. . . . . . . . ---LayoutQualifierIdContext
. . . . . . . . 0/3 in LayoutQualifierIdContext
. . . . . . . . location
. . . . . . . . location
. . . . . . . . 1/3 in LayoutQualifierIdContext
. . . . . . . . =
. . . . . . . . =
. . . . . . . . 2/3 in LayoutQualifierIdContext
. . . . . . . . 0
. . . . . . . . . . . . . . . . . . . . . . . . 0
. . . . . . . 3/4 in LayoutQualifierContext
. . . . . . . )
. . . . . . . )
. . . . . . 1/2 in TypeQualifierContext
. . . . . . attribute
. . . . . . . ---StorageQualifierContext
. . . . . . . attribute
. . . . . 1/2 in FullySpecifiedTypeContext
. . . . . vec4
. . . . . . ---TypeSpecifierContext
. . . . . . . ---TypeSpecifierNonarrayContext
. . . . . . . . ---BuiltinTypeSpecifierNonarrayContext
. . . . . . . . vec4
. . . . 1/2 in InitDeclaratorListContext
. . . . Name
. . . . . ---DeclarationMemberContext
. . . . . Name
. . . 1/2 in DeclarationContext
. . . ;
. . . ;
```

So now I have this nested parse tree that I want to find specific syntax structures in. Before parsing that way pretty easy, a regex could do it. Now it's not a string anymore though but a pretty wacky tree. In order to find structures like `layout (location = 0) in vec4 Name;` or `layout (location = 0) attribute vec4 Name;` a lot of stuff needs to happen. How much stuff also depends on how accurate these matches have to be. For example just matching the identifier inside any declaration is easy but that may also match declarations that don't have the desired format.

I've thought of three rough ways in which complex tree matching could be done in this situation. The first one would be to find a library that basically implements XPath (that is for XML) in Java for things other than XML trees. I'm not sure if that would even work because in some situations I need code to run in between.

The second way is to write a bunch of code, maybe using some utility functions here and there, that basically just looks for the structure. This would probably be mostly non-generalizable problem-specific code. Given that some elements of the declarations can have multiple children (like multiple identifiers etc) this isn't totally straightforwards but would probably work ok.

The third option is implementing a proper abstract tree matching system that uses a tree of matching rules to look for the desired structure and report back when it's found. Here's a mockup of how such a matching rule tree would look:
```java
 new MatchPhase(
  new AnyDepthMatch(
    GLSLParser.ExternalDeclarationContext.class,
    new ImmediateMatch(
      GLSLParser.DeclarationContext.class,
      new AllMatch(
        new AnyDepthMatch(
          GLSLParser.DeclarationMemberContext.class,
          new MatchCapture(
            (GLSLParser.DeclarationMemberContext member) ->
              //capture the declaration name identifier
          )
        ),
        new AnyDepthMatch(
          GLSLParser.FullySpecifiedTypeContext.class,
          new ImmediateMatch(
            GLSLParser.TypeQualifierContext.class,
            new AllMatch(
              new ChainImmediateMatch(
                new MatchTest(
                  (GLSLParser.LayoutQualifierContext member) ->
                    //check that the location is correct
                ),
                GLSLParser.LayoutQualifierContext.class,
                GLSLParser.LayoutQualifierId.class,
              ),
              new ImmediateMatch(
                GLSLParser.StorageQualifierContext.class,
                new MatchTest(
                  (GLSLParser.LayoutQualifierContext member) ->
                    //check that the storage qualifier is "attribute" or "in"
                )
              )
            )
          )
        )
      )
    )
  )
)
```

Making this work is probably even more work than option 2 but it is probably more extensible to other situations. 

I don't know if option 3 is worth it and I'm not 100% sure it would even work, but I have some ideas and some initial thoughts implemented in `MatchPhase`. And I don't know of a library that does option 1 but my search was cursory at best until now.

I'm also not sure if this matching API is even usable. It's certainly not very concise but short of implementing my own xpath I can't think of a better way of doing something like this.
