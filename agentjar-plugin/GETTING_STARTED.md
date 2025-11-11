# Getting Started with AgentJar Plugin Development

## What Has Been Created

A complete IntelliJ IDEA plugin skeleton with the following structure:

```
agentjar-plugin/
├── gradle/
│   └── wrapper/           # Gradle 8.7 wrapper for reproducible builds
├── src/
│   └── main/
│       ├── kotlin/com/agentjar/
│       │   ├── AgentJarToolWindowFactory.kt
│       │   └── actions/
│       │       ├── ConventionalCommitAction.kt
│       │       ├── OpenAgentChatAction.kt
│       │       └── ScaffoldSpringBootAction.kt
│       └── resources/META-INF/
│           └── plugin.xml
├── build.gradle.kts       # Gradle build configuration
├── settings.gradle.kts    # Gradle settings
├── gradlew               # Gradle wrapper script
├── .gitignore            # Git ignore patterns
├── README.md             # Main documentation
├── CONFIGURATION_GUIDE.md # Setup instructions
├── BUILD_NOTES.md        # Build system details
└── GETTING_STARTED.md    # This file
```

## What Each Component Does

### Plugin Configuration (plugin.xml)

Defines:
- Plugin ID: `com.agentjar.plugin`
- Plugin name and description
- Tool window: "AgentJar" (right sidebar)
- Three actions in Tools menu:
  1. Open Agent Chat
  2. Scaffold Spring Boot Project
  3. Conventional Commit

### Tool Window (AgentJarToolWindowFactory.kt)

Creates a simple UI with a text area showing:
> "AgentJar Agent is ready. Ask me to help with your Spring Boot project!"

### Actions

1. **OpenAgentChatAction**: Activates the AgentJar tool window
2. **ScaffoldSpringBootAction**: Creates minimal Spring Boot project structure
   - Creates `src/main/java/com/example/demo/`
   - Creates `src/main/resources/`
   - Generates `application.properties`
   - Generates `DemoApplication.java` with @SpringBootApplication
3. **ConventionalCommitAction**: Interactive conventional commit helper
   - Prompts for type (feat, fix, docs, etc.)
   - Prompts for optional scope
   - Prompts for description
   - Executes `git commit -am "type(scope): description"`

## Building the Plugin

### Requirements

- JDK 17 or later
- Internet access (first build only, to download IntelliJ SDK)
- No Gradle installation needed (uses wrapper)

### Build Commands

```bash
cd agentjar-plugin

# Clean build
./gradlew clean build

# Run plugin in development IDE
./gradlew runIde

# Build distributable ZIP
./gradlew buildPlugin

# Output: build/distributions/agentjar-plugin-0.0.1.zip
```

## Testing the Plugin

### In Development IDE (./gradlew runIde)

1. A new IntelliJ IDEA window opens with the plugin loaded
2. Look for "AgentJar" in the right sidebar
3. Try Tools > AgentJar menu items

### Test Scenarios

**Scenario 1: Tool Window**
1. Click "AgentJar" in right sidebar OR
2. Tools > AgentJar > Open Agent Chat
3. Verify tool window opens with welcome message

**Scenario 2: Spring Boot Scaffolding**
1. Create/open any project
2. Tools > AgentJar > Scaffold Spring Boot Project
3. Verify files are created:
   - src/main/java/com/example/demo/DemoApplication.java
   - src/main/resources/application.properties

**Scenario 3: Conventional Commit**
1. Make changes in a Git repository
2. Tools > AgentJar > Conventional Commit...
3. Enter type: "feat"
4. Enter scope: "ui" (or skip)
5. Enter description: "add new button"
6. Verify commit is created with message: "feat(ui): add new button"

## Known Limitations

### Network Restrictions

In certain sandbox environments (like GitHub Actions or corporate networks), JetBrains domains may be blocked:
- download.jetbrains.com
- cache-redirector.jetbrains.com

This prevents downloading the IntelliJ platform SDK. Solutions:

1. **Pre-cache dependencies**: Build once on a machine with internet, then copy `~/.gradle/caches/`
2. **Use local IDE**: Point to locally installed IntelliJ IDEA
3. **Corporate proxy**: Configure proxy in gradle.properties

### Gradle 9+ Deprecations

When using Gradle 9.2+, you'll see deprecation warnings. These are harmless and come from the IntelliJ Platform Gradle Plugin. The project uses Gradle 8.7 wrapper to avoid these.

## Next Development Steps

### Phase 1: Enhanced UI (Immediate)
- [ ] Replace static text area with interactive chat
- [ ] Add input field for user messages
- [ ] Add message history display
- [ ] Add send button with keyboard shortcut

### Phase 2: Agent Backend (Near-term)
- [ ] Define AgentBackend interface
- [ ] Implement EchoBackend (test implementation)
- [ ] Add configuration UI for selecting backend
- [ ] Implement Ollama adapter for local LLMs

### Phase 3: Enhanced Features (Medium-term)
- [ ] Expand Spring Boot scaffolding (controllers, services, repositories)
- [ ] Add Docker support (Dockerfile generation, run configs)
- [ ] Implement GitHub push and PR creation
- [ ] Add diff preview before applying agent changes

### Phase 4: Distribution (Long-term)
- [ ] Design custom branding (icon, name)
- [ ] Create custom IDE distribution
- [ ] Bundle required plugins
- [ ] Build installers for Windows/Mac/Linux

## Project Structure for Future Development

Recommended package organization:

```
com.agentjar/
├── actions/              # Action classes (existing)
├── toolwindow/          # Tool window UI components
├── agent/               # Agent backend interfaces and implementations
│   ├── AgentBackend.kt
│   ├── AgentMessage.kt
│   ├── AgentResponse.kt
│   ├── backends/
│   │   ├── EchoBackend.kt
│   │   ├── OllamaBackend.kt
│   │   ├── MCPBackend.kt
│   │   └── OpenAIBackend.kt
├── scaffold/            # Code scaffolding templates
│   ├── SpringBootScaffolder.kt
│   ├── templates/
│   │   ├── Controller.kt
│   │   ├── Service.kt
│   │   └── Repository.kt
├── git/                 # Git and GitHub integration
│   ├── ConventionalCommit.kt
│   └── GitHubClient.kt
├── docker/              # Docker integration
│   └── DockerfileGenerator.kt
└── config/              # Plugin settings and configuration
    └── AgentJarSettings.kt
```

## Tips for Development

### Hot Reload
When running `./gradlew runIde`, most changes require restarting the IDE. To speed up:
- Test logic in unit tests first
- Use breakpoints and debugger

### Debugging
Run with debug flag:
```bash
./gradlew runIde --debug-jvm
```

Then attach debugger on port 5005.

### Plugin Development Resources

- [IntelliJ Platform Plugin SDK](https://plugins.jetbrains.com/docs/intellij/welcome.html)
- [IntelliJ Platform Gradle Plugin](https://github.com/JetBrains/intellij-platform-gradle-plugin)
- [Plugin Samples](https://github.com/JetBrains/intellij-sdk-code-samples)

## Support and Questions

For issues or questions:
1. Check BUILD_NOTES.md for build troubleshooting
2. Check CONFIGURATION_GUIDE.md for configuration help
3. Review IntelliJ Platform Plugin SDK documentation
4. Check the intellij-community repository for examples

## License

This plugin is part of the intellij-community repository and follows the same Apache 2.0 license.
