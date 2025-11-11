# AgentJar Plugin

An IntelliJ Platform-based, agentic Spring Boot IDE plugin.

## Features

- **Agent Tool Window**: Interactive chat interface for AI-powered development assistance
- **Spring Boot Scaffolding**: Quickly generate Spring Boot project structures
- **Conventional Commits**: Easily create standardized git commits
- **Docker Integration**: Build and run applications in Docker containers (planned)

## Prerequisites

- Java 17 or higher
- Internet connectivity (for first build to download IntelliJ Platform SDK)
- macOS, Linux, or Windows

## Building

The project uses Gradle 8.7 (via wrapper) to avoid Gradle 10 deprecation warnings.

```bash
# First build (downloads IntelliJ Platform SDK ~900MB)
./gradlew build

# Subsequent builds
./gradlew build
```

## Running

Launch the plugin in a sandboxed IntelliJ IDEA instance:

```bash
./gradlew runIde
```

## Development

### Project Structure

```
agentjar-plugin/
├── src/main/
│   ├── kotlin/com/agentjar/
│   │   ├── AgentJarToolWindowFactory.kt
│   │   └── actions/
│   │       ├── OpenAgentChatAction.kt
│   │       ├── ScaffoldSpringBootAction.kt
│   │       └── ConventionalCommitAction.kt
│   └── resources/META-INF/
│       └── plugin.xml
├── build.gradle.kts
└── settings.gradle.kts
```

### Adding New Features

1. Define actions in `plugin.xml`
2. Implement action classes in `src/main/kotlin/com/agentjar/actions/`
3. Update tool window UI in `AgentJarToolWindowFactory.kt`

### Testing

```bash
# Run plugin verifier
./gradlew verifyPlugin

# Run tests
./gradlew test
```

## Configuration

### Gradle Version

This project uses Gradle 8.7 to ensure compatibility and avoid deprecation warnings:
- Compatible with Gradle 8.x and 9.x
- Avoids Gradle 10 deprecation issues
- Wrapper included for consistent builds

### IntelliJ Version

- Target: IntelliJ IDEA Community 2024.2.4
- Minimum build: 242
- Plugin dependencies: Java, Git4Idea

## Development Roadmap

- [x] Basic plugin structure with Gradle
- [x] Tool window implementation
- [x] Spring Boot scaffolding action
- [x] Conventional commit action
- [x] Gradle wrapper configuration (8.7)
- [ ] Chat UI implementation
- [ ] Agent backend abstraction
- [ ] Local model integration (Ollama)
- [ ] MCP server adapter
- [ ] Docker integration
- [ ] GitHub push functionality
- [ ] Diff preview before writes
- [ ] Security and audit logging
- [ ] Branded IDE distribution

## Troubleshooting

### Build Issues

**Problem**: "Deprecated Gradle features were used"
**Solution**: Use the provided Gradle wrapper (`./gradlew`) which uses Gradle 8.7

**Problem**: "Could not resolve com.jetbrains.intellij.idea:ideaIC"
**Solution**: Ensure internet connectivity for first build. The IntelliJ Platform SDK will be downloaded (~900MB)

**Problem**: Build fails with Java version error
**Solution**: Ensure Java 17 or higher is installed and JAVA_HOME is set correctly
