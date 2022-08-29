# Skeleton Brewer

![logo](doc/logo.png)

A small tool based on APT tech
and used for generating singleton code.

> "Singleton" "Skeleton"

> This **IS NOT** a mod of Minecraft

* [Readme - Chinese](readme.md)
* [internal code templates](doc/pattern.md)
* [changelog](doc/changelog.md)
* [demo project](https://github.com/351768593/SkeletonBrewerDemo)

## Usage

* Download pre-built .jar from [GitHub releases](https://github.com/351768593/SkeletonBrewer/releases),
  or you could build and install via `mvn install` by yourself after cloning repo
* Import Skeleton Brewer as dependency
* Create a file named `javax.annotation.processing.Processor` in directory `META-INF/services/`
  Content is:
  ```text
  firok.skeletonbrewer.SkeletonProcessor
  ```
* Enable APT in your develop environment
* Create classes marked with `@Skeleton`
* Re-build and start project

> This tool depends on [Topaz lib](https://github.com/351768593/Topaz) \[3.8.0, \)
