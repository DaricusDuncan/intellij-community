# AgentJar Plugin - Quick Start Guide

## Prerequisites

- Java 17+
- Internet connection (first build only)
- ~2GB free disk space

## Quick Commands

### Build Plugin
```bash
./gradlew build
```

### Run in IntelliJ Sandbox
```bash
./gradlew runIde
```

### Create Distribution
```bash
./gradlew buildPlugin
# Output: build/distributions/agentjar-plugin-0.0.1.zip
```

### Verify No Deprecation Warnings
```bash
./gradlew build --warning-mode all
```

### Clean Build
```bash
./gradlew clean build
```

## First Time Setup

1. **Clone or navigate to agentjar-plugin directory**
   ```bash
   cd agentjar-plugin
   ```

2. **Run verification script** (optional)
   ```bash
   ./verify-build.sh
   ```

3. **Build the plugin**
   ```bash
   ./gradlew build
   ```
   This will download IntelliJ Platform SDK (~900MB) on first run.

4. **Test the plugin**
   ```bash
   ./gradlew runIde
   ```
   This launches IntelliJ IDEA with your plugin installed.

## Plugin Features

Once running, access AgentJar features via:

1. **Tool Window**: View → Tool Windows → AgentJar
2. **Menu Actions**: Tools → AgentJar
   - Open Agent Chat
   - Scaffold Spring Boot
   - Conventional Commit

## Development Workflow

### Making Changes

1. Edit source files in `src/main/kotlin/com/agentjar/`
2. Rebuild: `./gradlew build`
3. Test: `./gradlew runIde`

### Project Structure
```
src/main/
├── kotlin/com/agentjar/
│   ├── AgentJarToolWindowFactory.kt    # Tool window UI
│   └── actions/
│       ├── OpenAgentChatAction.kt       # Chat action
│       ├── ScaffoldSpringBootAction.kt  # Spring scaffolding
│       └── ConventionalCommitAction.kt  # Git commits
└── resources/META-INF/
    └── plugin.xml                       # Plugin configuration
```

## Troubleshooting

### "Deprecated Gradle features" warning
**Fixed!** We use Gradle 8.7 which avoids Gradle 10 deprecation issues.

### Build fails - network error
Check internet connection. First build downloads dependencies.

### Java version error
Ensure Java 17+ is installed:
```bash
java -version  # Should show 17 or higher
```

### Build is slow
First build takes 5-10 minutes (downloads SDK).
Subsequent builds: ~30 seconds.

## Next Steps

- See `README.md` for detailed features
- See `BUILDING.md` for advanced build configuration
- See plugin.xml to add new actions/extensions

## Gradle Tips

### Continuous Build
```bash
./gradlew build --continuous
# Rebuilds automatically when files change
```

### Build Specific Task
```bash
./gradlew compileKotlin      # Compile only
./gradlew test               # Run tests
./gradlew verifyPlugin       # Verify plugin compatibility
```

### Debug Gradle Build
```bash
./gradlew build --info       # Detailed logging
./gradlew build --debug      # Debug level logging
```

### Clean All Caches
```bash
./gradlew clean
./gradlew --stop             # Stop daemon
rm -rf ~/.gradle/caches      # Clear global cache (if needed)
```

## IDE Setup

### IntelliJ IDEA

1. Open agentjar-plugin in IntelliJ IDEA
2. Gradle auto-import will configure the project
3. Use Gradle tool window for tasks
4. Run 'runIde' configuration to debug

### VS Code

1. Install Kotlin and Gradle extensions
2. Open agentjar-plugin folder
3. Use terminal for Gradle commands

## Distribution

### Install Locally
```bash
./gradlew buildPlugin
```
Then install ZIP from `build/distributions/` in IntelliJ via:
Settings → Plugins → ⚙️ → Install Plugin from Disk

### Publish to Marketplace
See [JetBrains Plugin Repository](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html)
