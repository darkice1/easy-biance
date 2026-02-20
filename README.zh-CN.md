# easy-biance 中文文档

## 项目简介
`easy-biance` 是一个 Kotlin 编写的 Binance 现货 API SDK，封装了常用交易、行情、资产管理接口，并兼容官方 Binance Connector Java 库。项目提供轻量级的签名、时间同步与 JSON 处理能力，帮助开发者快速集成交易策略或资产管理工具。

## 快速开始
1. 克隆仓库：`git clone https://github.com/darkice1/easy-biance.git`
2. 配置凭证：复制 `config.txt`，在本地或环境变量中填入 `BIANCE_URL`、`BIANCE_KEY`、`BIANCE_SECRET`。如需代理重试，可额外设置 `BIANCE_HTTP_PROXY=socks5h://127.0.0.1:2080`。
3. 构建并运行示例：
   ```bash
   ./gradlew build
   ./gradlew test
   ```
4. 在 `src/test/java/Test.kt` 中查看调用示例，或在 IDE 中运行以验证账户接口。

## 目录结构
- `src/main/java/easy/biance`：核心客户端、签名与 JSON 封装代码。
- `src/main/resources`：运行期资源文件（如日志配置）。
- `src/test/java`：示例与测试代码，建议将集成测试放在 `integration/` 子包，并通过 `@Ignore` 控制密钥依赖。
- `build.gradle.kts`：Kotlin JVM、依赖与发布配置，包含 Sonatype 签名与发布任务。

## 构建与测试
- `./gradlew build`：编译源码、运行测试并打包 JAR 到 `build/libs/`。
- `./gradlew test`：单独运行测试套件，适用于本地回归。
- `./gradlew publiclocal`：发布当前制品到本地 Maven 仓库以便下游项目调试。
- `./gradlew publishAndCloseSonatype`：推送到 OSSRH 并关闭暂存库，需在 `~/.gradle/gradle.properties` 或环境变量中设置发布账号与签名密钥。

## 发布到 Maven Central
1. 确认 `signingKey`、`signingPassword`、`centralUsername`、`centralPassword` 已配置。
2. 运行 `./gradlew publishAndCloseSonatype` 上传制品并关闭 staging。
3. 通过 `./gradlew release` 自动关闭并发布，或在 Sonatype 控制台手动确认。

## 常见问题
- **无密钥运行测试**：使用桩或模拟响应，不要将真实密钥提交至仓库。
- **时间同步错误**：使用 `BianceClient.time()` 校验服务器时间，或在请求中加入 `timestamp` 参数。
- **`code=-2015` 报错**：SDK 会在检测到 `{"code":-2015,...}` 后自动读取 `BIANCE_HTTP_PROXY`（或 JVM 参数 `-Dbiance.http.proxy=...`）并通过代理重试一次。
- **依赖冲突**：调整 `build.gradle.kts` 中的依赖版本并运行 `./gradlew dependencies` 检查树状结构。

## 相关链接
- Binance 官方 REST 文档（英文）：https://github.com/binance/binance-spot-api-docs/blob/master/rest-api.md
- Binance 官方 REST 文档（中文）：https://github.com/binance/binance-spot-api-docs/blob/master/rest-api_CN.md
