# Release Publishing

See [publish-on-central](https://github.com/DanySK/publish-on-central) for docs on how it works.

1. Make sure the javadocs are up to date and mostly complete `gradle javadoc`
2. Test that everything builds ok `gradle build --warning-mode all`
3. Run the tests `gradle test`
4. Bump the version in `build.gradle` and commit it
5. Make a tag `git tag vX.Y.Z`
6. Upload the tag `git push --tags`
7. Release the build `gradle releaseJavaMavenOnMavenCentralNexus`

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

Locations in which a new AST node class `Foo` has to be registered:

- `ASTVisitor`: method `default R visitFoo(Foo node)` that can be called by `Foo`'s `accept` method and visits all nested members of a `Foo` instance
- `ASTListener`: if `Foo` is a `InnerASTNode`, empty methods `default void enterFoo(Foo node)` and `default void exitFoo(Foo node)` that can be called by `Foo`'s `enterNode` and `exitNode` methods
- `ASTPrinter`: if `Foo` isn't just a superclass, a visitor and/or listener method implementation that emits tokens for printing a `Foo` instance
- `ASTBuilder`: if `Foo` isn't just a superclass, a parse tree visitor method implementation that constructs a new `Foo` instance from the parse tree
