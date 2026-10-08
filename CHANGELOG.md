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

**Full Changelog**: https://github.com/IrisShaders/glsl-transformer/compare/v3.0.0-pre2...v[Version]()
