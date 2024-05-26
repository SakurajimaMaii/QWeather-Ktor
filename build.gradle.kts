import org.jetbrains.dokka.DokkaConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    id("org.jetbrains.dokka") version "1.9.20"
    id("convention.publication")
}

group = "io.github.sakurajimamaii"
version = "1.1.2"

sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}

repositories {
    mavenCentral()
    mavenLocal()
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:1.9.20")
    }
}

dependencies {
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.cio.jvm)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.encoding)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.vastcore)
    testImplementation("org.slf4j:slf4j-api:1.6.1")
    testImplementation("org.slf4j:slf4j-simple:1.6.1")
    testImplementation("io.github.sakurajimamaii:log-core:1.3.3")
    testImplementation("io.github.sakurajimamaii:log-desktop:1.3.3")
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets.configureEach {
        notCompatibleWithConfigurationCache("https://github.com/Kotlin/dokka/issues/1217")
        jdkVersion.set(17)
        languageVersion.set("1.9.20")
        suppressInheritedMembers.set(true)
        suppressObviousFunctions.set(true)
        sourceRoots.from(file("src"))
        documentedVisibilities.set(
            setOf(DokkaConfiguration.Visibility.PUBLIC, DokkaConfiguration.Visibility.PROTECTED, DokkaConfiguration.Visibility.PRIVATE, DokkaConfiguration.Visibility.INTERNAL)
        )

        sourceLink {
            localDirectory.set(projectDir.resolve("src"))
            remoteUrl.set(URL("https://github.com/SakurajimaMaii/QWeatherSDK-Ktor/tree/canary/src"))
            remoteLineSuffix.set("#L")
        }
    }
}

tasks.dokkaHtml {
    outputDirectory.set(layout.buildDirectory.get().asFile.resolve("documentation/html"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = JavaVersion.VERSION_17.toString()
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = JavaVersion.VERSION_17.toString()
}

extra["PUBLISH_ARTIFACT_ID"] = "QWeatherSDK-Ktor"
extra["PUBLISH_DESCRIPTION"] = "QWeatherSDK built on ktor"
extra["PUBLISH_URL"] = "https://github.com/SakurajimaMaii/QWeatherSDK-Ktor"

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "io.github.sakurajimamaii"
            artifactId = "qweather-sdk-ktor"
            version = "1.1.2"

            afterEvaluate {
                from(components["java"])
            }
        }
    }
}