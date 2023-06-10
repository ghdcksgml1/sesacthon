import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.21"
	kotlin("plugin.spring") version "1.8.21"
	kotlin("kapt") version "1.8.21"
}

group = "com.haechi"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-oauth2-client:6.0.1")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// https://mvnrepository.com/artifact/org.aspectj/aspectjweaver
	runtimeOnly("org.aspectj:aspectjweaver:1.9.9.1")

	// jwt 라이브러리 추가
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// QueryDSL
	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	implementation("com.querydsl:querydsl-core:5.0.0")
	kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
	kapt("com.querydsl:querydsl-kotlin-codegen:5.0.0")

	// https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

//querydsl 추가
val querydslDir = "$buildDir/generated/querydsl"

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}
