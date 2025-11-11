# AgentJar Plugin

**Agentic tooling for enterprise Java and Spring Boot development**

AgentJar is an IntelliJ IDEA plugin that brings AI-powered assistance to your Java development workflow. It provides intelligent agents for code generation, Spring Boot scaffolding, conventional commits, and Docker integration.

## Features

### 🤖 AI Agent Chat
- Interactive chat interface for code assistance
- Context-aware code suggestions
- Model-agnostic backend (supports Ollama, MCP, remote APIs)

### 🌱 Spring Boot Scaffolding
- Quick project setup with standard structure
- Automatic component generation (controllers, services, repositories)
- Best practices and enterprise patterns

### 📝 Conventional Commits
- Easy-to-use dialog for creating semantic commits
- Validation and formatting according to Conventional Commits spec
- Support for breaking changes and detailed descriptions

### 🐳 Docker Integration (Coming Soon)
- Automatic Dockerfile generation
- Build and run containers directly from IDE
- View container logs in real-time

## Getting Started

### Prerequisites
- IntelliJ IDEA 2024.3 or later
- Java 17 or higher
- Git (for conventional commits)

### Building from Source

1. Clone the repository:
```bash
git clone https://github.com/DaricusDuncan/intellij-community.git
cd intellij-community/plugins/agentjar-plugin
```

2. Build the plugin:
```bash
./gradlew buildPlugin
```

3. Install in IntelliJ:
   - Go to `Settings > Plugins > ⚙️ > Install Plugin from Disk...`
   - Select `build/distributions/agentjar-plugin-1.0-SNAPSHOT.zip`

### Usage

#### Opening Agent Chat
- From menu: `AgentJar > Open Agent Chat`
- From editor: Right-click and select `Ask AgentJar`
- Or use the tool window on the right side

#### Scaffolding Spring Boot Project
1. Open your project in IntelliJ
2. Select `AgentJar > Scaffold Spring Boot Project`
3. Follow the prompts to create the structure

#### Creating Conventional Commits
1. Make your code changes
2. Select `AgentJar > Create Conventional Commit`
3. Fill in the commit details:
   - **Type**: feat, fix, docs, etc.
   - **Scope**: Optional component name
   - **Description**: Brief summary (imperative, lowercase)
   - **Body**: Optional detailed explanation
4. Click OK to commit

## Architecture

### Package Structure
```
com.agentjar/
├── actions/           # IDE actions (menu items, shortcuts)
│   ├── OpenAgentChatAction.kt
│   ├── ScaffoldSpringBootAction.kt
│   ├── ConventionalCommitAction.kt
│   └── GenerateDockerfileAction.kt
├── services/          # Backend services
│   ├── AgentBackend.kt (interface)
│   ├── AgentBackendService.kt (manager + mock implementation)
│   ├── OllamaService.kt (planned)
│   └── MCPService.kt (planned)
└── ui/               # UI components
    └── AgentJarToolWindowFactory.kt (with async chat)
```

### Roadmap

#### Phase 1: Foundation ✅
- [x] Plugin structure and build configuration
- [x] Basic UI with tool window
- [x] Spring Boot scaffolding action
- [x] Conventional commit action

#### Phase 2: Agent Backend ✅
- [x] Agent backend interface and abstraction
- [x] Mock backend for testing and development
- [x] Async chat with conversation history
- [x] Code generation and refactoring suggestions
- [ ] Ollama integration for local models (planned)
- [ ] MCP server support (planned)
- [ ] Remote API support (OpenAI, etc.) (planned)

#### Phase 3: Enhanced Features (In Progress)
- [x] Docker support (Dockerfile generation)
- [x] Multi-stage Docker builds for Maven and Gradle
- [x] .dockerignore generation
- [ ] Docker build/run integration
- [ ] Spring component generation (controllers, services, repos)
- [ ] Enhanced code refactoring UI
- [ ] Test generation

#### Phase 4: Security & Enterprise
- [ ] Offline mode support
- [ ] Diff preview for agent changes
- [ ] Security audit logging
- [ ] Permission gates for actions

#### Phase 5: Distribution
- [ ] Branding and product configuration
- [ ] Full IDE distribution
- [ ] Plugin marketplace publication

## Development

### Running in Development Mode
```bash
./gradlew runIde
```

This will start a new IntelliJ instance with the plugin installed.

### Running Tests
```bash
./gradlew test
```

### Code Style
This plugin follows the standard Kotlin coding conventions and IntelliJ Platform plugin development best practices.

## Contributing

Contributions are welcome! Please feel free to submit issues and pull requests.

### Development Guidelines
1. Follow existing code structure and patterns
2. Add tests for new features
3. Update documentation as needed
4. Use conventional commits for your changes

## License

This plugin is built on the IntelliJ Platform and follows the same licensing terms.

## Documentation

- **[USAGE.md](USAGE.md)** - Comprehensive user guide with examples
- **[CONTRIBUTING.md](CONTRIBUTING.md)** - Developer guide for contributors
- **[ARCHITECTURE.md](ARCHITECTURE.md)** - Technical architecture and design
- **[CHANGELOG.md](CHANGELOG.md)** - Version history and changes

## Quick Start Guide

### 1. Install the Plugin
```bash
cd plugins/agentjar-plugin
./build-plugin.sh
```
Then install the generated ZIP in IntelliJ IDEA.

### 2. Open Agent Chat
Click the AgentJar tool window on the right side or go to `AgentJar > Open Agent Chat`.

### 3. Try a Command
Type: "Create a Spring Boot controller" and press Enter.

### 4. Scaffold a Project
Go to `AgentJar > Scaffold Spring Boot Project` to create a new Spring Boot structure.

### 5. Create a Conventional Commit
After making changes, use `AgentJar > Create Conventional Commit` for well-formatted commits.

## Support

For issues and questions:
- GitHub Issues: https://github.com/DaricusDuncan/intellij-community/issues
- Email: support@agentjar.dev
- Documentation: See [USAGE.md](USAGE.md)

## License

This plugin is built on the IntelliJ Platform and follows the same licensing terms as the IntelliJ Community Edition.

## Acknowledgments

- Built with ❤️ for the Java and Spring Boot community
- Powered by the IntelliJ Platform
- Inspired by the need for better AI integration in enterprise development

---

**AgentJar** - Empowering Java developers with intelligent agents 🚀

*"Clean up the mess, build better software."*
