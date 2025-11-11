# AgentJar Plugin Configuration Guide

## Overview

This guide explains how to configure and build the AgentJar plugin for IntelliJ IDEA.

## Prerequisites

- JDK 17 or later
- Internet access to download IntelliJ platform SDK (first build only)
- Git (for conventional commit feature)

## Quick Start

### 1. Build the Plugin

```bash
cd agentjar-plugin
./gradlew build
```

The first build will download the IntelliJ platform SDK (~500MB). Subsequent builds will be faster.

### 2. Run the Plugin in Development IDE

```bash
./gradlew runIde
```

This launches a new IntelliJ IDEA instance with the AgentJar plugin loaded.

### 3. Test the Plugin

Once the development IDE opens:

1. **Open Agent Chat**: 
   - Go to Tools > AgentJar > Open Agent Chat
   - Or find "AgentJar" tool window on the right side

2. **Scaffold Spring Boot Project**:
   - Tools > AgentJar > Scaffold Spring Boot Project
   - Creates src/main/java and resources with basic Spring Boot app

3. **Conventional Commit**:
   - Tools > AgentJar > Conventional Commit...
   - Follow prompts to create a properly formatted commit

## Build Configuration Details

### Gradle Wrapper

The project uses Gradle 8.7 via wrapper for reproducibility. No need to install Gradle separately.

### IntelliJ Platform Version

Currently targets IntelliJ IDEA Community 2023.3.6, compatible with:
- since-build: 233 (2023.3)
- until-build: 243.* (2024.3)

### Dependencies

The plugin depends on:
- `com.intellij.java` - Java language support
- `Git4Idea` - Git integration

## Gradle 9+ Compatibility

If using system Gradle 9.2 or later, you may see deprecation warnings. These are harmless and come from the IntelliJ Platform Gradle Plugin. Use the wrapper (`./gradlew`) to avoid these warnings.

## Troubleshooting

### Build fails with network error

**Problem**: Cannot download IntelliJ platform SDK

**Solutions**:
1. Check internet connection and firewall settings
2. Configure proxy in `gradle.properties`:
   ```properties
   systemProp.http.proxyHost=proxy.company.com
   systemProp.http.proxyPort=8080
   systemProp.https.proxyHost=proxy.company.com
   systemProp.https.proxyPort=8080
   ```
3. Use local IntelliJ installation (see BUILD_NOTES.md)

### Kotlin compilation errors

**Problem**: Cannot resolve IntelliJ platform classes

**Solution**: Ensure the build has completed successfully at least once to download dependencies.

### Plugin doesn't appear in development IDE

**Problem**: Plugin.xml not properly configured

**Solution**: Check that `plugin.xml` is in `src/main/resources/META-INF/` and contains valid XML.

## Development Workflow

### 1. Make Code Changes

Edit files in `src/main/kotlin/com/agentjar/`

### 2. Test Changes

```bash
./gradlew runIde
```

### 3. Build Distribution

```bash
./gradlew buildPlugin
```

Output: `build/distributions/agentjar-plugin-0.0.1.zip`

### 4. Install in IntelliJ IDEA

1. Open IntelliJ IDEA
2. Go to Settings > Plugins
3. Click gear icon > Install Plugin from Disk
4. Select the ZIP file from build/distributions

## Customization

### Change Plugin Name

Edit `build.gradle.kts`:
```kotlin
intellijPlatform {
    pluginConfiguration {
        name = "Your Custom Name"
    }
}
```

### Add More Dependencies

Edit `build.gradle.kts`:
```kotlin
dependencies {
    intellijPlatform {
        // ... existing dependencies
        bundledPlugin("org.jetbrains.plugins.yaml")
    }
}
```

### Change Target Platform Version

Edit `build.gradle.kts`:
```kotlin
intellij {
    version.set("2024.1")  // Change version here
}
```

Then update `plugin.xml` since-build accordingly.

## Next Development Steps

1. **Interactive Chat UI**: Replace static JTextArea with input field and message history
2. **Agent Backend**: Implement AgentBackend interface with LLM integrations
3. **Enhanced Scaffolding**: Add templates for controllers, services, repositories
4. **Docker Support**: Generate Dockerfiles and run configurations
5. **GitHub Integration**: Push, branch management, PR creation

See README.md for the full roadmap.
