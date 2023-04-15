# Release Publishing

See [publish-on-central](https://github.com/DanySK/publish-on-central) for docs on how it works.

1. Test javadoc, compilation and tests with `CI=1 gradle build --warning-mode all`
2. Bump the version in `build.gradle` and commit it
3. Make a tag `git tag vX.Y.Z`
4. Upload the tag `git push --tags`
5. Release the build `gradle releaseJavaMavenOnMavenCentralNexus`

# Other

Commands for combining all files in a directory and subdirectories:

```bash
NAME=the_shader_name; cat ./$NAME/**/*.{vsh,fsh,gsh,glsl} > $NAME.glsl
```

Some cleanup maybe required. (this is sometimes useful when working with external test files)

Setup signing: (secrets are kept in `~/.gradle/gradle.properties`)

```
gpg --keyring secring.gpg --export-secret-keys > ~/.gnupg/secring.gpg
gpg --list-keys --keyid-format short
```

Testing only one class, in this case `GrammarDebugTest` can be done like this:

```
gradle test --tests GrammarDebugTest
```

# Development Notes

## Conventions

### Generic Type Parameters

Generic type parameters are named with the following rules:

- `N` for `extends ASTNode`
- `C` for `extends ParserRuleContext`
- `T` for `extends ParseTree`
- `Child` for `extends ASTNode` if it's the child parameter of an `ASTNode` subclass
- `J` for `extends JobParameters`
- `E` for extending some kind of Enum
- `R` for some other return type
- `V` for generic (unconstrained) values that aren't any of the above

`P` can't be used because it makes javadoc think it's a `<p>` paragraph tag.

## AST Development

### AST Node Registration

Locations in which a new AST node class `Foo` has to be registered:

- `ASTVisitor`: method `default R visitFoo(Foo node)` that can be called by `Foo`'s `accept` method and visits all nested members of a `Foo` instance
- `ASTListener`: if `Foo` is a `InnerASTNode`, empty methods `default void enterFoo(Foo node)` and `default void exitFoo(Foo node)` that can be called by `Foo`'s `enterNode` and `exitNode` methods
- `ASTPrinter`: if `Foo` isn't just a superclass, a visitor and/or listener method implementation that emits tokens for printing a `Foo` instance
- `ASTBuilder`: if `Foo` isn't just a superclass, a parse tree visitor method implementation that constructs a new `Foo` instance from the parse tree

### AST Node Class Structure

Each `ASTNode` extending class has the following parts, some of which are optional:

- `public (abstract) class Foo extends ASTNode` or other subclass
- An internal enum and the corresponding abstract get method (repeat 0..n times)
- public static fields
- protected fields
- private fields
- constructors
- own abstract methods
- non-inherited getters and setters
- other non-inherited methods
- implementations of enum getters
- other inherited methods of the closest subclass
- inherited methods: `accept` or `footypeAccept`, optionally `enterFoo` and `exitFoo`

### Mass file generation

Use multi cursor to generate a file that has lots of class files and then use this piece of js from the REPL to write the files:

```js
fs.readFileSync("split").toString().split("//split_marker").map(str => str.trim() + "\n").forEach(str => fs.writeFileSync(str.match(/class (\w+)/)[1] + ".java", str))
```

### TODO
- Enum value index (index that finds nodes based on their enum values)
- Optional indexes: option to turn off indexes for performance reasons and only if necessary
- Partial indexes: indexes that only index certain enum values, class types or identifiers to reduce memory usage and improve AST build performance
- Configuration of partial indexes can happen at construction
- Make glsl-transformer thread safe so that it can be run in parallel on different transformation jobs
- Try to remove double detachParent call when removing items from a list
- More flexible list wildcards: nested wildcards that can run a predicate on how many times should be matched and other things
