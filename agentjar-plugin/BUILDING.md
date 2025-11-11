# Building AgentJar Plugin

## Overview

This document describes how to build the AgentJar plugin and addresses the Gradle deprecation warnings mentioned in the build failure.

## Gradle Configuration

### Gradle Version

The project uses **Gradle 8.7** via the Gradle Wrapper to avoid Gradle 10 deprecation warnings.

**Why Gradle 8.7?**
- Stable and well-tested with IntelliJ Platform Gradle Plugin
- Avoids deprecated features that will be removed in Gradle 10
- Compatible with the IntelliJ Platform Gradle Plugin 1.17.4

### IntelliJ Platform Gradle Plugin

**Version**: 1.17.4 (stable release)

This version:
- Is compatible with Gradle 8.7
- Does not use deprecated Gradle APIs
- Supports IntelliJ IDEA 2024.2.x

## Build Commands

### Standard Build

```bash
# Clean and build
./gradlew clean build

# Just build
./gradlew build
```

### Run Plugin in Sandbox

```bash
# Launch IntelliJ IDEA with plugin installed
./gradlew runIde
```

### Verify Plugin

```bash
# Run plugin verifier
./gradlew verifyPlugin
```

### Build Distribution

```bash
# Create plugin distribution ZIP
./gradlew buildPlugin
```

The distribution ZIP will be created in `build/distributions/`.

## First Build

The first build will download:
- IntelliJ Platform SDK (~900MB)
- Kotlin compiler dependencies
- Plugin dependencies

**Requirements**:
- Internet connectivity
- ~2GB free disk space
- Java 17 or higher

**Expected time**: 5-10 minutes (depending on internet speed)

## Subsequent Builds

Subsequent builds are much faster as dependencies are cached:
- Clean build: ~30 seconds
- Incremental build: ~10 seconds

## Addressing Deprecation Warnings

### Original Issue

```
Deprecated Gradle features were used in this build, making it incompatible with Gradle 10.
You can use '--warning-mode all' to show the individual deprecation warnings.
```

### Solution

We've addressed this by:

1. **Using Gradle 8.7**: The wrapper is configured to use Gradle 8.7, which is stable and compatible with our build configuration.

2. **Using stable plugin version**: IntelliJ Platform Gradle Plugin 1.17.4 does not use deprecated Gradle APIs.

3. **Proper task configuration**: All Gradle tasks use current APIs.

### Verifying No Deprecation Warnings

Run build with warning mode:

```bash
./gradlew build --warning-mode all
```

Expected output: No deprecation warnings should appear.

## Build Properties

### Kotlin Configuration

```kotlin
kotlin {
    jvmToolchain(17)  // Uses Java 17 toolchain
}
```

### IntelliJ Configuration

```kotlin
intellij {
    version.set("2024.2.4")              // Target IntelliJ version
    type.set("IC")                        // Community Edition
    plugins.set(listOf(
        "com.intellij.java",              // Java plugin
        "Git4Idea"                        // Git integration
    ))
}
```

### Tasks Configuration

```kotlin
tasks {
    patchPluginXml {
        sinceBuild.set("242")             // Minimum build number
    }
    
    buildSearchableOptions {
        enabled = false                   // Disabled for faster builds
    }
}
```

## Troubleshooting

### Problem: Gradle daemon crashes

**Solution**: Increase Gradle daemon memory in `gradle.properties`:

```properties
org.gradle.jvmargs=-Xmx2048m
```

### Problem: Build fails with "Could not resolve"

**Solution**: 
1. Check internet connectivity
2. Clear Gradle cache: `./gradlew --stop && rm -rf ~/.gradle/caches`
3. Rebuild: `./gradlew clean build`

### Problem: Java version mismatch

**Solution**: Ensure Java 17+ is installed:

```bash
# Check Java version
java -version

# Should show Java 17 or higher
```

## CI/CD

### GitHub Actions

Example workflow for building the plugin:

```yaml
name: Build Plugin

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Gradle
        run: |
          cd agentjar-plugin
          ./gradlew build
      - name: Upload plugin
        uses: actions/upload-artifact@v3
        with:
          name: plugin
          path: agentjar-plugin/build/distributions/*.zip
```

## Performance Tips

1. **Use Gradle daemon**: Keep it running for faster builds
2. **Configure Gradle caching**: Already configured in wrapper
3. **Parallel builds**: Use `--parallel` flag for multi-module builds
4. **Disable unnecessary tasks**: `buildSearchableOptions` is already disabled

## Development Workflow

### Recommended workflow:

1. Make code changes
2. Run incremental build: `./gradlew build`
3. Test in sandbox: `./gradlew runIde`
4. Create distribution: `./gradlew buildPlugin`

### Fast iteration:

```bash
# Terminal 1: Continuous build
./gradlew build --continuous

# Terminal 2: Run IDE when ready
./gradlew runIde
```

## Related Documentation

- [IntelliJ Platform SDK](https://plugins.jetbrains.com/docs/intellij/)
- [IntelliJ Platform Gradle Plugin](https://github.com/JetBrains/gradle-intellij-plugin)
- [Gradle User Manual](https://docs.gradle.org/8.7/userguide/userguide.html)
