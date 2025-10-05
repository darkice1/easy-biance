@file:Suppress("VulnerableLibrariesLocal")

plugins {
	kotlin("jvm") version "2.1.21"                 // 与 pom 中 <kotlin.version> 对齐
	`java-library`
	id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
	`maven-publish`
	signing
}

group   = "com.github.darkice1"
version = "1.0.76"                                  // 与 pom.version 对齐

val projectName = "easy-biance"
val projectDesc = "Neo easy Biance SDK."
val repoName    = projectName                       // 方便拼接 GitHub 链接

java {
	toolchain { languageVersion.set(JavaLanguageVersion.of(21)) }
	withSourcesJar()
	withJavadocJar()
}

// ---------- 仓库 ----------
repositories {
	mavenCentral()
	mavenLocal()
	// pom.xml 中的 <pluginRepositories> → jcenter（如无老包依赖可删除）
	maven { url = uri("https://jcenter.bintray.com/"); name = "jcenter" }
}

// ---------- 依赖 ----------
dependencies {
	// 自家核心库
	api("com.github.darkice1:easy:1.0.86")

	// Kotlin & 测试
	api(kotlin("stdlib"))            // 已涵盖 kotlin-stdlib-jdk8
	testImplementation(kotlin("test"))

	// Binance SDK
	api("io.github.binance:binance-connector-java:3.4.1")
//	// https://mvnrepository.com/artifact/com.alibaba.fastjson2/fastjson2

	// Log4j 2
/*	api("org.apache.logging.log4j:log4j-api:2.23.1")
	api("org.apache.logging.log4j:log4j-core:2.23.1")
	api("org.apache.logging.log4j:log4j-slf4j-impl:2.23.1")*/
}

// ---------- 编译/Javadoc ----------
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
	compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
}

tasks.withType<Javadoc>().configureEach {
	(options as StandardJavadocDocletOptions).run {
		encoding = "UTF-8"
		docEncoding = "UTF-8"
		addStringOption("Xdoclint:none", "-quiet")  // 与 pom 参数一致
	}
}


// ---------- 发布 & 签名 ----------
publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
			artifactId = projectName
			pom {
				name.set(projectName)
				description.set(projectDesc)
				url.set("https://github.com/darkice1/$repoName")
				licenses {
					license {
						name.set("Apache License 2.0")      // 与 pom 保持一致
						url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						id.set("neo")
						name.set("neo")
						email.set("starneo@gmail.com")
					}
				}
				scm {
					connection.set("scm:git:https://github.com/darkice1/$repoName.git")
					url.set("https://github.com/darkice1/$repoName")
				}
			}
		}
	}
}

signing {
	// 与示例脚本一致：CI 首选 in-memory，其他环境 fallback 至 gpg 命令
	val inMemKey: String? = providers.gradleProperty("signingKey").orNull
	val inMemPwd: String? = providers.gradleProperty("signingPassword").orNull
	when {
		inMemKey != null && inMemPwd != null -> useInMemoryPgpKeys(inMemKey, inMemPwd)
		else                                  -> useGpgCmd()
	}
	sign(publishing.publications["mavenJava"])
}

// ---------- Sonatype 发布流水线 ----------
val coords = "${project.group}:$projectName:$version"

tasks.register("publishAndCloseSonatype") {
	group       = "mypublishing"
	description = "Publish artifacts to Sonatype OSSRH, then close the staging repository."
	dependsOn("publishToSonatype", "closeSonatypeStagingRepository")
	doLast { println("close:[$coords]") }
}

tasks.register("publiclocal") {
	group       = "mypublishing"
	description = "Publish artifacts to local Maven repository."
	dependsOn("publishMavenJavaPublicationToMavenLocal")
	doLast { println("public local:[$coords]") }
}

tasks.register("release") {
	group       = "mypublishing"
	description = "Close & release Sonatype staging repo, then print coordinates."
	dependsOn("publishToSonatype", "closeAndReleaseSonatypeStagingRepository")
	doLast { println("release:[$coords]") }
}

nexusPublishing {
	repositoryDescription.set("${project.group}.$projectName:${project.version}")
	repositories {
		sonatype {
			// staging / snapshots 地址与示例保持一致
			nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
			snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
			username.set(providers.gradleProperty("centralUsername"))
			password.set(providers.gradleProperty("centralPassword"))
		}
	}
}