plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.agentjar"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

kotlin {
    jvmToolchain(17)
}

intellij {
    version.set("2024.2.4")
    type.set("IC")
    plugins.set(listOf("com.intellij.java", "Git4Idea"))
}

tasks {
    patchPluginXml {
        sinceBuild.set("242")
    }
    
    buildSearchableOptions {
        enabled = false
    }
}
