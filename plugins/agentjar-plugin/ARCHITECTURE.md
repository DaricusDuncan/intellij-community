# AgentJar Plugin Architecture

This document describes the architecture and design decisions of the AgentJar plugin.

## Overview

AgentJar is designed as a modular, extensible IntelliJ IDEA plugin that provides AI-powered assistance for Spring Boot development. The architecture follows IntelliJ Platform best practices and uses modern Kotlin features.

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     IntelliJ IDEA IDE                       │
├─────────────────────────────────────────────────────────────┤
│                      AgentJar Plugin                        │
│                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐    │
│  │   UI Layer   │  │    Actions   │  │   Services   │    │
│  │              │  │              │  │              │    │
│  │ - Tool Window│  │ - Scaffold   │  │ - Backend    │    │
│  │ - Dialogs    │  │ - Commit     │  │   Manager    │    │
│  │ - Chat UI    │  │ - Docker     │  │ - Context    │    │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘    │
│         │                 │                 │              │
│         └─────────────────┴─────────────────┘              │
│                           │                                │
│                  ┌────────▼────────┐                       │
│                  │  Agent Backend  │                       │
│                  │   (Interface)   │                       │
│                  └────────┬────────┘                       │
│                           │                                │
│         ┌─────────────────┼─────────────────┐             │
│         │                 │                 │             │
│    ┌────▼────┐      ┌────▼────┐      ┌────▼────┐        │
│    │  Mock   │      │ Ollama  │      │   MCP   │        │
│    │ Backend │      │ Backend │      │ Backend │        │
│    └─────────┘      └─────────┘      └─────────┘        │
│                          (Future)         (Future)        │
└─────────────────────────────────────────────────────────────┘
```

## Component Details

### 1. UI Layer

#### AgentJarToolWindowFactory
- **Purpose**: Creates and manages the main tool window
- **Type**: Factory class implementing ToolWindowFactory
- **Key Features**:
  - Chat interface with input/output
  - Asynchronous message handling
  - Conversation history display
  - Integration with backend service

**Flow:**
```
User Input → UI Thread → Coroutine Launch → 
Backend Service → Agent Backend → Response → 
UI Thread Update → Display
```

#### ConventionalCommitDialog
- **Purpose**: Interactive dialog for creating conventional commits
- **Type**: DialogWrapper subclass
- **Key Features**:
  - Form validation
  - Commit format preview
  - Breaking change support
  - Multi-field input

### 2. Actions Layer

All actions extend `AnAction` and follow the Command pattern.

#### ScaffoldSpringBootAction
```
┌─────────────────────────────────────┐
│    ScaffoldSpringBootAction        │
├─────────────────────────────────────┤
│ 1. Check if project exists          │
│ 2. Detect existing Spring Boot      │
│ 3. Create directory structure       │
│ 4. Generate DemoApplication.java    │
│ 5. Generate application.properties  │
│ 6. Generate README.md               │
│ 7. Show success notification        │
└─────────────────────────────────────┘
```

#### ConventionalCommitAction
```
┌─────────────────────────────────────┐
│   ConventionalCommitAction         │
├─────────────────────────────────────┤
│ 1. Check Git availability           │
│ 2. Show commit dialog                │
│ 3. Validate input                    │
│ 4. Format commit message             │
│ 5. Execute git commit                │
│ 6. Show result notification          │
└─────────────────────────────────────┘
```

#### GenerateDockerfileAction
```
┌─────────────────────────────────────┐
│   GenerateDockerfileAction         │
├─────────────────────────────────────┤
│ 1. Check project directory           │
│ 2. Detect build tool (Maven/Gradle) │
│ 3. Check existing Dockerfile        │
│ 4. Generate appropriate Dockerfile  │
│ 5. Generate .dockerignore           │
│ 6. Show build instructions          │
└─────────────────────────────────────┘
```

### 3. Services Layer

#### AgentBackendService
- **Type**: Project-level service
- **Purpose**: Central hub for agent interaction
- **Pattern**: Facade pattern
- **Thread Safety**: Uses coroutines for async operations

**Service Lifecycle:**
```
Project Open → Service Creation → Backend Registration →
Active Backend Selection → Ready for Use → Project Close
```

**Key Methods:**
```kotlin
interface AgentBackendService {
    fun registerBackend(backend: AgentBackend)
    fun getAvailableBackends(): List<AgentBackend>
    fun getActiveBackend(): AgentBackend?
    fun setActiveBackend(backendId: String): Boolean
    suspend fun chat(...)
    suspend fun generateCode(...)
    suspend fun suggestRefactorings(...)
}
```

### 4. Backend Abstraction

#### AgentBackend Interface
- **Purpose**: Model-agnostic interface for AI providers
- **Pattern**: Strategy pattern
- **Key Concepts**:
  - Pluggable backends
  - Async operations (suspend functions)
  - Type-safe data models

**Backend Contract:**
```kotlin
interface AgentBackend {
    val id: String
    val name: String
    suspend fun isAvailable(): Boolean
    suspend fun chat(...): ChatResponse
    suspend fun generateCode(...): CodeGenerationResponse
    suspend fun suggestRefactorings(...): List<RefactoringSuggestion>
}
```

**Data Flow:**
```
User Request → AgentBackendService → Active Backend →
AI Model → Parse Response → Return Structured Data →
Service → UI Update
```

## Design Patterns

### 1. Service Locator
```kotlin
val service = AgentBackendService.getInstance(project)
```
- Used for accessing project-level services
- Follows IntelliJ Platform conventions

### 2. Factory Pattern
```kotlin
class AgentJarToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(...)
}
```
- Used for creating UI components
- Allows lazy initialization

### 3. Strategy Pattern
```kotlin
interface AgentBackend { ... }
class MockAgentBackend : AgentBackend { ... }
class OllamaBackend : AgentBackend { ... }
```
- Enables multiple AI backend implementations
- Runtime backend selection

### 4. Command Pattern
```kotlin
class ScaffoldSpringBootAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent)
}
```
- All user actions are commands
- Enables undo/redo (future)

### 5. Observer Pattern (Future)
```
AgentBackendService → Listeners → UI Updates
```
- For real-time backend status
- Progress notifications

## Threading Model

### UI Thread
- All Swing/UI operations
- Action execution starts here
- UI updates must happen here

### Background Threads (Coroutines)
- Agent backend calls
- File I/O operations
- Network requests
- Long-running computations

**Threading Example:**
```kotlin
scope.launch {                              // Background thread
    val result = withContext(Dispatchers.IO) {
        backend.chat(message)               // Network/computation
    }
    ApplicationManager.getApplication().invokeLater {  // UI thread
        updateUI(result)                    // UI update
    }
}
```

## Data Models

### Core Models

```
AgentContext          → Project context (name, files, language)
ChatMessage           → Single conversation message
ChatResponse          → Agent's response with suggestions
CodeContext           → Code-specific context
CodeGenerationResponse→ Generated code with metadata
RefactoringSuggestion → Single refactoring suggestion
```

### Model Relationships
```
AgentContext
    └─ used by → AgentBackend.chat()

ChatMessage[]
    └─ forms → ConversationHistory
        └─ passed to → AgentBackend.chat()

CodeContext
    └─ used by → AgentBackend.generateCode()
                 AgentBackend.suggestRefactorings()
```

## Extension Points

### Adding a New Backend

1. **Implement AgentBackend**
```kotlin
class MyBackend : AgentBackend {
    override val id = "my-backend"
    override val name = "My AI Backend"
    // Implement methods
}
```

2. **Register in Service**
```kotlin
class AgentBackendService {
    init {
        registerBackend(MyBackend())
    }
}
```

### Adding a New Action

1. **Create Action Class**
```kotlin
class MyAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) { }
}
```

2. **Register in plugin.xml**
```xml
<action id="AgentJar.MyAction" 
        class="com.agentjar.actions.MyAction"
        text="My Action"/>
```

### Adding a New Service

1. **Create Service**
```kotlin
@Service(Service.Level.PROJECT)
class MyService(project: Project)
```

2. **Register in plugin.xml**
```xml
<projectService serviceImplementation="..."/>
```

## Security Considerations

### Current Measures
- Mock backend only (no external calls)
- Input validation on all user inputs
- WriteAction for file modifications
- No credential storage yet

### Future Security Features
- Offline mode toggle
- API key encryption
- Audit logging
- Rate limiting
- Diff preview before applying changes
- Permission gates for destructive operations

## Performance Considerations

### Optimization Strategies

1. **Lazy Initialization**
   - Services created on-demand
   - Backends registered but not initialized until used

2. **Async Operations**
   - All backend calls are async
   - UI remains responsive
   - Proper cancellation support

3. **Caching** (Future)
   - Response caching for repeated queries
   - Context caching for current file

4. **Batch Operations** (Future)
   - Bulk code generation
   - Multi-file refactoring

## Testing Strategy

### Unit Tests
- Service logic
- Backend implementations
- Data model validation
- Utility functions

### Integration Tests (Future)
- Action → Service → Backend flow
- UI interaction tests
- File system operations

### Manual Testing
- Plugin in development IDE
- Real-world scenarios
- Performance testing

## Dependencies

### Runtime Dependencies
```
org.jetbrains.kotlin:kotlinx-coroutines-core
org.jetbrains.kotlin:kotlinx-coroutines-swing
```

### IntelliJ Platform Dependencies
```
com.intellij.modules.platform
com.intellij.java
Git4Idea
```

### Test Dependencies
```
junit:junit:4.13.2
```

## Configuration

### plugin.xml Structure
```xml
<idea-plugin>
    <id>           → Plugin identifier
    <name>         → Display name
    <vendor>       → Publisher info
    <description>  → Feature description
    <depends>      → Platform dependencies
    <extensions>   → Extension points
        <toolWindow>    → UI extensions
        <projectService>→ Services
    <actions>      → User actions
        <group>         → Menu organization
        <action>        → Individual actions
</idea-plugin>
```

## Future Architecture Enhancements

### 1. Plugin Marketplace
- Downloadable backend plugins
- Community-contributed agents
- Extension API

### 2. Settings UI
- Backend configuration
- API key management
- Feature toggles

### 3. Persistence Layer
- Conversation history storage
- User preferences
- Cache management

### 4. Analytics (Optional, Privacy-Respecting)
- Feature usage metrics
- Error reporting
- Performance monitoring

## Debugging

### Log Files
- IDE log: `Help > Show Log in Files`
- Plugin logs: Search for "AgentJar"

### Breakpoints
- Set in IntelliJ IDEA
- Debug with `./gradlew runIde --debug-jvm`

### Useful Logging
```kotlin
private val log = Logger.getInstance(MyClass::class.java)
log.info("Info message")
log.warn("Warning message")
log.error("Error message", exception)
```

## Deployment

### Build Process
```
Source Code → Kotlin Compilation → JAR Creation →
Plugin XML Validation → ZIP Packaging → Distribution
```

### Distribution Channels
1. **Manual Installation**: ZIP file
2. **Plugin Marketplace** (Future): Direct install from IDE
3. **GitHub Releases**: Versioned releases

## Resources

- [IntelliJ Platform SDK](https://plugins.jetbrains.com/docs/intellij/welcome.html)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Conventional Commits](https://www.conventionalcommits.org/)

---

**Last Updated**: 2025-11-11
**Version**: 1.0-SNAPSHOT
