# Build Configuration Notes

## Current Status

The AgentJar plugin has been set up with a standard Gradle-based build configuration using the IntelliJ Platform Gradle Plugin. However, due to network restrictions in certain environments (jetbrains.com domains may be blocked), the build cannot download the required IntelliJ platform SDK.

## Build Configuration

### Option 1: Standard Build (Requires Internet Access)

The plugin is configured to use:
- Gradle 8.7 (via wrapper)
- IntelliJ Platform Gradle Plugin 1.17.3
- Target Platform: IntelliJ IDEA Community 2023.3.6

To build with network access:
```bash
./gradlew build
```

### Option 2: Using Local IntelliJ Installation (Offline)

If you have IntelliJ IDEA installed locally, modify `build.gradle.kts`:

```kotlin
intellij {
    localPath.set("/path/to/your/intellij-idea")
    plugins.set(listOf("com.intellij.java", "Git4Idea"))
}
```

Then build:
```bash
./gradlew build
```

### Option 3: Integration with intellij-community Build System

Since this plugin resides within the intellij-community repository, it could be integrated into the main Bazel build system. This would require:

1. Creating a `BUILD.bazel` file in the agentjar-plugin directory
2. Defining the plugin module dependencies
3. Adding the plugin to the appropriate build targets

## Current Deprecation Warnings

When building with Gradle 9.2 (system gradle), the following deprecation warnings appear:

1. **StartParameter.isConfigurationCacheRequested** - Deprecated in favor of BuildFeatures service
   - This comes from the IntelliJ Platform Gradle Plugin
   - Will be fixed in future plugin versions

2. **Multi-string dependency notation** - Deprecated for Gradle 10
   - Affects how IntelliJ platform dependencies are resolved
   - Plugin needs update to use single-string notation

## Recommended Solution

The Gradle wrapper has been configured with Gradle 8.7, which is more stable with the IntelliJ Platform Gradle Plugin 1.17.3. This avoids the Gradle 9.2 deprecation warnings.

## Building Without Network Access

If you're in an environment without access to jetbrains.com:

1. **Pre-download the SDK**: On a machine with internet access:
   ```bash
   ./gradlew --refresh-dependencies
   ```
   Then copy the `~/.gradle/caches` directory to the restricted environment.

2. **Use local IDE build**: Point to a locally built or installed IntelliJ IDEA instance.

3. **Maven Local**: Download and install the IntelliJ SDK to Maven local repository:
   ```bash
   # On machine with internet
   ./gradlew publishToMavenLocal
   ```

## Next Steps

- [ ] Test build with internet access
- [ ] Configure offline build if needed
- [ ] Consider Bazel integration for seamless development within intellij-community
- [ ] Update to newer plugin version once Gradle 9+ compatibility is stable
