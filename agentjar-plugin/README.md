# AgentJar Plugin

AgentJar provides agent-driven assistance for enterprise Spring Boot Java development with integrated model-agnostic agentic capabilities.

## Features

- **Agent Chat Tool Window**: Interactive agent interface for code assistance
- **Spring Boot Scaffolding**: Quick project setup with standard structure
- **Conventional Commits**: Git commit helper following conventional commit standards
- **Docker Workflows**: (Planned) Docker integration for containerized development
- **Model-Agnostic Agent Layer**: (Planned) Support for local and remote LLMs

## Current Status

This is an early prototype focusing on:
- Basic plugin infrastructure
- Tool window with agent interface
- Spring Boot project scaffolding
- Conventional commit assistance

## Roadmap

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
