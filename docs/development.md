# Release Publishing

Releases are made by the manually triggered [`release` workflow](../.github/workflows/release.yml). It builds and tests the project, publishes it to Maven Central, and creates the tag `vX.Y.Z` and the GitHub release. Publishing and the GitHub release are handled by [JReleaser](https://jreleaser.org/), which is configured in `build.gradle`.

1. Set the version of the release in `gradle.properties`. This is the only place the version is defined.
2. Replace the release title and notes in `CHANGELOG.md` with those of the release. The file itself explains its format.
3. Commit and push both changes and wait for the `build` workflow to succeed.
4. Run the `release` workflow on the branch to release from, usually `main`: `gh workflow run release --ref main`

The workflow has a dry run option (`gh workflow run release --ref main -f dry-run=true`) that goes through all steps, including signing, without publishing or tagging anything. Versions containing `-pre` are marked as pre-releases on GitHub.

If the workflow fails after the artifacts were published to Maven Central, the version can't be published again. In that case create the tag and the GitHub release manually.

The workflow needs the following secrets in the repository or in its `release` environment:

- `JRELEASER_MAVENCENTRAL_USERNAME` and `JRELEASER_MAVENCENTRAL_PASSWORD`: a user token generated on the [Central Portal](https://central.sonatype.com/account)
- `JRELEASER_GPG_PUBLIC_KEY` and `JRELEASER_GPG_SECRET_KEY`: the ASCII armored signing key pair, as exported with `gpg --armor --export KEY_ID` and `gpg --armor --export-secret-keys KEY_ID`
- `JRELEASER_GPG_PASSPHRASE`: the passphrase of the signing key

With the same secrets and a `JRELEASER_GITHUB_TOKEN` in `~/.jreleaser/config.properties`, the release can be tested locally with `./gradlew jreleaserFullRelease --dryrun`. Don't run it without `--dryrun`, actual releases should only be made by the workflow.

# Updating Dependencies

The versions of all dependencies and Gradle plugins are pinned in `gradle/libs.versions.toml`, the Gradle version is pinned by the wrapper. Running `./update-dependencies.sh` updates all of them to their latest stable versions and then builds and tests the project. Review the resulting changes before committing them. The versions of the GitHub actions used in the workflows are updated manually.

# API Reports

`./gradlew apiReport` compares the public API of the current state with the released version that is set as `apiBaselineVersion` in `build.gradle`, using [japicmp](https://github.com/siom79/japicmp). The reports are written to `build/reports/japicmp`: `api.txt` and `api.html` for everything except the classes that are generated from the grammar, `api-grammar.txt` and `api-grammar.html` for those. The task never fails the build. Use the reports to write the migration notes in `CHANGELOG.md` and update the baseline version after a release.

# Type Analysis Tests

The classes in `ast.typing` are required to have full line and branch coverage. `./gradlew check` fails otherwise (`jacocoTestCoverageVerification`). The uncovered lines can be found in the report in `build/reports/jacoco/test/html`.

- `TypeAnalyzerTest.cases` contains the table-driven tests: expressions with their expected types and programs with their expected diagnostics. Known imprecisions of the analysis are pinned as cases with names that start with `imprecision`. When fixing one, update the case and the list of limitations in `docs/overview.md`.
- `BuiltinRegistryTest` generates a call for every builtin function prototype and pins the number of prototypes per environment. Update the numbers when changing the files in `src/main/resources/.../ast/typing/builtins`.
- `TypeCorpusTest` analyzes the files of the glslang test suite in `src/test/resources/glslang-test`. The outcome for every file is pinned in `TypeCorpusTest.outcomes`. After a deliberate change of the analysis, regenerate the file by running the test with `UPDATE_TYPE_CORPUS=1 ./gradlew test --tests TypeCorpusTest` and review the difference. Files that glslang accepts may only have diagnostics if they are listed in `TypeCorpusTest.allowlist` with a reason.
- `TypeDifferentialTest` compares the inferred types with those of glslang.
- The typed trees of the reprint cases are snapshots of `ASTReprintTest`.

`TypeCorpusTest.glslang` and `TypeDifferentialTest.glslang` are derived from the reference results of glslang. To regenerate them, clone [glslang](https://github.com/KhronosGroup/glslang) into `hidden/reference/glslang` (the `hidden` directory is ignored by git) and run `python3 scripts/update-typing-test-data.py`.

# Other

Commands for combining all files in a directory and subdirectories:

```bash
NAME=the_shader_name; cat ./$NAME/**/*.{vsh,fsh,gsh,glsl} > $NAME.glsl
```

Some cleanup maybe required. (this is sometimes useful when working with external test files)

Testing only one class, in this case `GrammarDebugTest` can be done like this:

```
./gradlew test --tests GrammarDebugTest
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
