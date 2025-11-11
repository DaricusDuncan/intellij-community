# Changelog

All notable changes to the AgentJar plugin will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Planned Features
- Ollama backend integration for local models
- MCP (Model Context Protocol) server support
- Remote API support (OpenAI, Anthropic, etc.)
- Enhanced Spring Boot component generation (services, repositories, controllers)
- Docker build and run integration from IDE
- Code diff preview before applying agent changes
- Test generation for Spring Boot components
- Security audit logging
- Offline mode toggle
- Settings UI for backend configuration

## [1.0.0-SNAPSHOT] - 2025-11-11

Initial development release of AgentJar plugin.

### Added

#### Core Features
- **Agent Chat Interface**: Interactive tool window for AI-powered assistance
  - Async message handling with Kotlin coroutines
  - Conversation history tracking
  - Real-time response display
  - Follow-up suggestions

- **Agent Backend System**: Model-agnostic architecture for AI providers
  - `AgentBackend` interface for implementing AI providers
  - `AgentBackendService` for managing multiple backends
  - `MockAgentBackend` for testing and development
  - Support for chat, code generation, and refactoring suggestions

- **Spring Boot Scaffolding**: Quick project setup
  - Automatic directory structure creation
  - `DemoApplication.java` with `@SpringBootApplication`
  - `application.properties` with sensible defaults
  - Project README.md generation
  - Detection of existing Spring Boot projects

- **Conventional Commits**: Git commit helper
  - Interactive dialog with form validation
  - Support for all conventional commit types (feat, fix, docs, etc.)
  - Optional scope field
  - Breaking change flag
  - Multi-line commit body support
  - Commit message format validation (length, case)

- **Docker Integration**: Container support for Spring Boot
  - Automatic Dockerfile generation
  - Multi-stage builds for Maven and Gradle
  - Optimized layer caching
  - Non-root user for security
  - Health check configuration
  - `.dockerignore` file generation
  - Build tool auto-detection

#### Actions
- `OpenAgentChatAction`: Opens and activates the agent chat tool window
- `ScaffoldSpringBootAction`: Creates minimal Spring Boot project structure
- `ConventionalCommitAction`: Creates conventional Git commits
- `GenerateDockerfileAction`: Generates Dockerfiles for Spring Boot apps

#### Services
- `AgentBackendService`: Project-level service for agent management
  - Backend registration and selection
  - Async chat interface
  - Code generation interface
  - Refactoring suggestions interface

#### UI Components
- `AgentJarToolWindowFactory`: Main tool window with chat interface
- `ConventionalCommitDialog`: Dialog for creating conventional commits

#### Data Models
- `AgentContext`: Project and file context for agents
- `ChatMessage`: Individual conversation messages
- `ChatResponse`: Agent responses with suggestions
- `CodeContext`: Code-specific context information
- `CodeGenerationResponse`: Generated code with metadata
- `RefactoringSuggestion`: Individual refactoring suggestions
- `MessageRole`: User/Assistant/System role enum
- `RefactoringCategory`: Categories for refactorings

#### Testing
- `AgentBackendServiceTest`: Comprehensive tests for backend service
  - Mock backend availability tests
  - Chat functionality tests
  - Code generation tests
  - Refactoring suggestion tests
  - Conversation history tests
  - Data model validation tests

#### Documentation
- `README.md`: Project overview, installation, and features
- `USAGE.md`: Detailed user guide with examples
- `CONTRIBUTING.md`: Developer guide for contributors
- `ARCHITECTURE.md`: Technical architecture documentation
- `CHANGELOG.md`: This file

#### Build System
- Gradle build configuration with IntelliJ Plugin v1.17.4
- Kotlin 1.9.24 support
- Java 17 toolchain
- Gradle wrapper for consistent builds
- Build script (`build-plugin.sh`) for easy distribution

### Dependencies
- `kotlinx-coroutines-core:1.7.3`: Async operations
- `kotlinx-coroutines-swing:1.7.3`: UI thread integration
- `junit:4.13.2`: Testing framework

### Platform Requirements
- IntelliJ IDEA 2024.2 or later
- Java 17 or higher

### Known Issues
- Network access required for building (IntelliJ Platform dependencies)
- Mock backend only - real AI backends not yet implemented
- Docker build/run integration not yet implemented (generation only)

### Notes
- This is a development snapshot
- Not yet published to JetBrains Marketplace
- Manual installation required via "Install Plugin from Disk"

## Development Timeline

### Phase 1: Foundation (Completed)
- [x] Plugin structure and build system
- [x] Core UI components
- [x] Action framework
- [x] Service architecture

### Phase 2: Core Features (Completed)
- [x] Spring Boot scaffolding
- [x] Conventional commits
- [x] Docker file generation
- [x] Agent backend abstraction

### Phase 3: AI Integration (Planned)
- [ ] Ollama backend
- [ ] MCP server support
- [ ] OpenAI integration
- [ ] Response streaming

### Phase 4: Enhanced Features (Planned)
- [ ] Component generation
- [ ] Test generation
- [ ] Docker build/run
- [ ] Code refactoring UI

### Phase 5: Polish & Security (Planned)
- [ ] Settings UI
- [ ] Diff preview
- [ ] Audit logging
- [ ] Error handling improvements

### Phase 6: Distribution (Planned)
- [ ] JetBrains Marketplace submission
- [ ] Automated releases
- [ ] Update notifications

## Migration Guide

### From Nothing to 1.0.0-SNAPSHOT
This is the initial release, no migration needed.

## Support

- **Issues**: https://github.com/DaricusDuncan/intellij-community/issues
- **Email**: support@agentjar.dev
- **Documentation**: See README.md and USAGE.md

## Contributors

- Project created by the AgentJar team
- Built on the IntelliJ Platform
- Inspired by the need for better AI integration in Java development

---

For more detailed information about features and usage, see:
- [README.md](README.md) - Project overview
- [USAGE.md](USAGE.md) - User guide
- [CONTRIBUTING.md](CONTRIBUTING.md) - Developer guide
- [ARCHITECTURE.md](ARCHITECTURE.md) - Technical details
