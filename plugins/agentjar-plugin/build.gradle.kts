plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.agentjar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://www.jetbrains.com/intellij-repository/releases")
    maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")
    
    testImplementation("junit:junit:4.13.2")
}

kotlin {
    jvmToolchain(17)
}

intellij {
    version.set("2024.2.4")
    type.set("IC")  // IntelliJ IDEA Community Edition
    plugins.set(listOf("com.intellij.java", "Git4Idea"))
}

tasks {
    patchPluginXml {
        sinceBuild.set("242")
        untilBuild.set("243.*")
    }
    
    buildSearchableOptions {
        enabled = false
    }
}
