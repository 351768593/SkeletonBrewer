# Skeleton Brewer

![logo](doc/logo.png)

基于 APT 技术的单例代码生成器.

> "Singleton" "Skeleton"

> 这 **不是** Minecraft 的 mod

* [Readme - English](readme-en.md)
* [内置代码模板](doc/pattern.md)
* [变更记录](doc/changelog.md)
* [示例项目](https://github.com/351768593/SkeletonBrewerDemo)

## 使用方式

* 从 [GitHub releases](https://github.com/351768593/SkeletonBrewer/releases) 下载编译好的 .jar,
  或 clone repo 后自行执行 `mvn install` 构建安装依赖
* 在项目中引入 Skeleton Brewer 依赖
* 在 `META-INF/services/` 目录下建立文件 `javax.annotation.processing.Processor`
  内容为
  ```text
  firok.skeletonbrewer.SkeletonProcessor
  ```
* 启用开发环境中的 APT 功能, 这通常需要在 IDE 中进行配置
* 在需要创建单例的类标注 `@Skeleton` 注解
  * 这些类需要提供一个 **无参构造方法**
* 重新编译并启动项目

> 此工具依赖 [Topaz 库](https://github.com/351768593/Topaz) \[3.8.0, \) 版本

