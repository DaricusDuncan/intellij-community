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
│   └── ConventionalCommitAction.kt
├── services/          # Backend services (coming soon)
│   ├── AgentBackend.kt
│   ├── OllamaService.kt
│   └── MCPService.kt
└── ui/               # UI components
    └── AgentJarToolWindowFactory.kt
```

### Roadmap

#### Phase 1: Foundation ✅
- [x] Plugin structure and build configuration
- [x] Basic UI with tool window
- [x] Spring Boot scaffolding action
- [x] Conventional commit action

#### Phase 2: Agent Backend (In Progress)
- [ ] Agent backend interface and abstraction
- [ ] Ollama integration for local models
- [ ] MCP server support
- [ ] Remote API support (OpenAI, etc.)

#### Phase 3: Enhanced Features
- [ ] Docker support (Dockerfile generation, build/run)
- [ ] Spring component generation (controllers, services, repos)
- [ ] Code refactoring suggestions
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

## Support

For issues and questions:
- GitHub Issues: https://github.com/DaricusDuncan/intellij-community/issues
- Email: support@agentjar.dev

---

**AgentJar** - Empowering Java developers with intelligent agents 🚀
