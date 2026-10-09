This file holds the release notes of the next release. Update it together with the version in `gradle.properties`
before running the release workflow, see `docs/development.md`.
[Version]() is automatically replaced with the version being released, e.g. 3.0.0
The first line below the separator is the release title, which is shown as "v[Version]() - Title" on GitHub.
Everything above the separator is ignored. Everything below the title is the body of the GitHub release.
----------
Support for optionNV Pragma and Mesh Shader Extensions Support

## What's Changed
* pragma optionNV support by @drouarb in https://github.com/IrisShaders/glsl-transformer/pull/21
* taskNV storage qualifier & enableMeshShaders flag by @drouarb in https://github.com/IrisShaders/glsl-transformer/pull/22
* Fix resource paths as noted here (but using my own implementation): https://github.com/IrisShaders/glsl-transformer/pull/20
* Add tests to make sure lexer options behave correctly (there was no bug, just lacking coverage)

## Traversal and printing fixes (behavior changes)
* The default traversal in `ASTVisitor` now visits the array specifier of `NumericTypeSpecifier`, `FixedTypeSpecifier`, `TypeReference` and `StructSpecifier` (`vec3[N] x`). Previously these nodes were skipped, so they kept their old root and stayed in its indexes when the surrounding tree was moved or deleted. Custom visitors and listeners now see `ArraySpecifier` nodes (and their contents) they did not see before.
* The default traversal now visits the declaration in a while loop condition (`while (bool b = f())`), with the same consequences.
* `ASTVisitor.visitTranslationUnit` returns the aggregated result of the version statement and all external declarations instead of only the result of the version statement.
* `EQUAL` and `NOT_EQUAL` have precedence 8 instead of sharing precedence 7 with the relational operators. The printer now emits the required parentheses for trees like `a < (b == c)`, which were previously printed as `a < b == c` and reparsed into a different tree.

**Full Changelog**: https://github.com/IrisShaders/glsl-transformer/compare/v3.0.0-pre2...v[Version]()
