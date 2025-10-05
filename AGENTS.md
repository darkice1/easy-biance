# Repository Guidelines

你们交互使用中文。

## 项目结构与模块划分
	- `build.gradle.kts` 负责 Kotlin JVM 与发布流水线配置；先在此更新插件、依赖与任务再同步至其他文件。该脚本同时声明 Java toolchain、签名与 Sonatype 发布流程，修改后请运行 `./gradlew tasks` 验证没有断链。
	- `src/main/java/easy/biance` 存放 Binance 客户端与 JSON 封装；新增接口请按功能细分子包，保持与交易领域模型一致。提交前请确认公开 API 的参数命名与枚举值与 Binance 官方文档一致。
	- `src/main/resources` 用于运行期资源（日志、默认配置）；避免在其他目录散落配置文件，并使用 UTF-8 编码保证跨平台一致性。
	- `src/test/java` 目前包含可执行示例；请将新实验转化为可重复测试，并通过测试夹具或模拟器隔离敏感凭证，确保 CI 可无密钥运行。
	- `config.txt` 是本地凭证模板；真实密钥请通过环境变量或忽略文件维护，同时记录示例值便于新贡献者快速启动。

## 构建、测试与开发指令
	- `./gradlew build` 编译 Kotlin 代码、运行全部测试并在 `build/libs/` 产出制品；适合作为本地回归入口。若需调试特定模块，可结合 `--info` 查看详细日志。
	- `./gradlew test` 单独执行测试套件；每次提交前必须通过，并建议在变更依赖后附带 `--tests` 过滤关键用例重复确认。
	- `./gradlew publiclocal` 推送制品到本地 Maven 仓库，便于下游项目验证；依赖项目可在 `build.gradle` 中临时指向 `mavenLocal()`。
	- `./gradlew publishAndCloseSonatype` 发布到 OSSRH 并关闭暂存库；需预先配置 `centralUsername`、`centralPassword` 与签名密钥，并在成功后检查 Sonatype 控制台状态。
	- `./gradlew clean` 清理构建输出，以解决缓存异常或依赖漂移；常与 `--refresh-dependencies` 结合使用以排查环境差异。

## 代码风格与命名
	- 遵循 Kotlin 官方格式：使用 4 空格等效缩进、必要处保留尾随逗号、命名简洁有意义；建议配合 `ktlint` 或 IDE 的 Kotlin Formatting 支持自动化校验。
	- 生产代码保持在 `easy.biance` 包内，针对交易所概念优先采用枚举或密封类以维持二进制兼容，并减少魔法字符串。
	- 面向外部的公共 API 应补充精炼 KDoc，保证生成的 Javadoc 与 Maven 元数据完整，同时为 SDK 使用者提供清晰示例。

## 测试规范
	- 使用 `kotlin.test`（如 `@Test`、`assertEquals`）并在 `src/test/java` 中按照生产包结构放置测试；命名遵循 Given-When-Then 思路增强可读性。
	- 测试文件命名遵循 `<Feature>Test.kt`；集成测试置于 `integration/` 子目录并以 `@Ignore` 标注需要真实密钥的用例，必要时加入 `Assume` 以在 CI 环境跳过。
	- 优先模拟或桩化 Binance 响应；敏感参数通过环境变量或 `gradle.properties` 注入，同时记录期望的最小权限与速率限制。

## 提交与合并请求
	- 沿用 Conventional Commits，例如 `feat(biance): 添加提币历史查询功能`，确保信息清晰可执行；若涉及破坏性更新请使用 `feat!` 或 `fix!` 标记。
	- 提交前整理历史，合并细碎修复，保持单次提交主题聚焦，并在必要时附带 `BREAKING CHANGE:` 描述。
	- PR 描述需包含变更摘要、执行过的验证指令、相关日志或截图，并关联对应 Issue，同时说明配置影响与回滚策略。
	- 修改公共 API、构建流程或用户文档逻辑时，务必同步更新 `README.md` 与 `README.zh-CN.md`，保持多语文档一致。

## 安全与配置提示
	- 将 `config.txt` 视作示例文件；生产地址、密钥与机密禁止入库，并使用 `.gitignore` 保护本地覆盖文件。
	- 通过内存签名密钥（`signingKey`、`signingPassword`）或 `~/.gradle/gradle.properties` 配置发布凭证；CI 环境建议使用加密变量或 secrets manager。
	- 扩展网络功能时记录限频策略与错误处理方案，便于后续代理或自动化安全集成；必要时补充重试与退避策略说明。

## 代理协作提醒
	- 多名代理协作时，请通过 Issue 或 Draft PR 同步任务划分，并维持中文沟通。建议在描述中列出 blocking 条件、负责人以及预计交付时间，减少重复劳动。
