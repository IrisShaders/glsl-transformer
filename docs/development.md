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

# Development Notes

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
- Implement `Object.clone` for all the AST nodes so that expression replacement can clone the parsed nodes instead of re-parsing the AST repeatedly
- Add wildcard matching for any number of items in an item list
- Add matching for ancestor that is part of a specific branch of a type of class (ancestor matching where the ancestor of the given type has to have been reached through a specific getter method of that ancestor or the direct child of the matched ancestor has to be of a specific type to avoid complications when the "branch" to require is a list)
- More powerful string based wildcards that can match classes maybe
