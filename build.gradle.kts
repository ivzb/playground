import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.3.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))

    // JUnit 5 API and engine
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.13.2")

    // Parameterized tests support
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.13.2")
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("-Xmx4g")
}

application {
    mainClass.set("MainKt")
}