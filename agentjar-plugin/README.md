# AgentJar Plugin

AgentJar is an IntelliJ IDEA plugin providing agent-driven assistance for enterprise Spring Boot Java development with integrated model-agnostic agentic capabilities (local and remote LLMs).

## 🎯 Vision

Build a custom IntelliJ Platform-based IDE focused on enterprise Spring Boot Java development with AI-powered code assistance, scaffolding, Git workflows, and Docker integration. Starting as a plugin, evolving into a branded IDE distribution potentially marketable to JetBrains.

## ✨ Current Features

- **Agent Chat Tool Window**: Interactive agent interface for code assistance
- **Spring Boot Scaffolding**: Quick project setup with standard structure
  - Creates src/main/java and src/main/resources
  - Generates application.properties
  - Creates basic @SpringBootApplication entry point
- **Conventional Commits**: Git commit helper following conventional commit standards
  - Type selection (feat, fix, docs, etc.)
  - Optional scope
  - Automatic commit message formatting

## 📋 Planned Features

- **Docker Workflows**: Docker integration for containerized development
- **Model-Agnostic Agent Layer**: Support for Ollama, MCP server, OpenAI, Anthropic
- **GitHub Integration**: Push, branch management, PR creation
- **Safe Code Refactoring**: Diff preview before applying changes
- **Enhanced Scaffolding**: Controllers, services, repositories, REST endpoints

## 🚀 Quick Start

See [CONFIGURATION_GUIDE.md](CONFIGURATION_GUIDE.md) for detailed setup instructions.

```bash
# Build the plugin
./gradlew build

# Run in development IDE
./gradlew runIde

# Build distribution
./gradlew buildPlugin
```

## 📦 Installation

1. Build the plugin: `./gradlew buildPlugin`
2. In IntelliJ IDEA: Settings > Plugins > Install Plugin from Disk
3. Select `build/distributions/agentjar-plugin-0.0.1.zip`
4. Restart IDE

## 🔧 Usage

### Open Agent Chat
- **Menu**: Tools > AgentJar > Open Agent Chat
- **Tool Window**: Find "AgentJar" on the right sidebar

### Scaffold Spring Boot Project
- **Menu**: Tools > AgentJar > Scaffold Spring Boot Project
- Creates minimal Spring Boot structure in current project

### Create Conventional Commit
- **Menu**: Tools > AgentJar > Conventional Commit...
- Follow prompts to create properly formatted commits

## 📚 Documentation

- [CONFIGURATION_GUIDE.md](CONFIGURATION_GUIDE.md) - Setup and configuration
- [BUILD_NOTES.md](BUILD_NOTES.md) - Build system details and troubleshooting

## 🗺️ Development Roadmap

### Immediate Next Steps
- [ ] Fix Gradle build deprecation warnings
- [ ] Add Gradle wrapper for reproducible builds
- [ ] Implement interactive chat UI (input field, message history)
- [ ] Define agent backend interface

### Agent Backend Interface (Draft)
- [ ] AgentBackend interface
- [ ] EchoBackend implementation
- [ ] Ollama adapter (local models)
- [ ] MCP server adapter
- [ ] Remote HTTP adapter (OpenAI/Anthropic)

### Spring Boot Features
- [ ] Project detector (verify pom.xml or build.gradle.kts)
- [ ] Expanded scaffolding: controllers, services, repositories
- [ ] REST endpoint wizard

### Git & GitHub Integration
- [ ] Enhanced conventional commit validation
- [ ] GitHub push integration
- [ ] Branch management
- [ ] PR draft helper

### Docker Support
- [ ] Dockerfile generation
- [ ] Run configuration
- [ ] Logs tool window

### Code Safety
- [ ] Diff preview before applying edits
- [ ] Safe write path with WriteCommandAction
- [ ] DiffRequest UI integration

### Enterprise Features
- [ ] Offline mode
- [ ] Audit logging of agent actions
- [ ] Permission gating
- [ ] Persistent agent session transcripts

### Distribution
- [ ] Custom IDE branding
- [ ] Product icons
- [ ] Custom build target
- [ ] Installer generation

## Building

```bash
./gradlew build
```

## Running

```bash
./gradlew runIde
```

## Testing

```bash
./gradlew test
```
