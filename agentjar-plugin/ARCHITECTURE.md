# AgentJar Plugin Architecture

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    IntelliJ IDEA IDE                        │
│  ┌───────────────────────────────────────────────────────┐  │
│  │                  AgentJar Plugin                      │  │
│  │                                                       │  │
│  │  ┌─────────────────┐    ┌────────────────────────┐  │  │
│  │  │  Tool Window    │    │   Actions Menu         │  │  │
│  │  │  ┌───────────┐  │    │  ┌──────────────────┐  │  │  │
│  │  │  │ Chat UI   │  │    │  │ Open Agent Chat  │  │  │  │
│  │  │  │ (Static)  │  │    │  ├──────────────────┤  │  │  │
│  │  │  └───────────┘  │    │  │ Scaffold Spring  │  │  │  │
│  │  │                 │    │  │ Boot Project     │  │  │  │
│  │  │  [Future: AI    │    │  ├──────────────────┤  │  │  │
│  │  │   interactions] │    │  │ Conventional     │  │  │  │
│  │  │                 │    │  │ Commit...        │  │  │  │
│  │  └─────────────────┘    │  └──────────────────┘  │  │  │
│  │                         └────────────────────────┘  │  │
│  │                                                       │  │
│  │  ┌─────────────────────────────────────────────────┐ │  │
│  │  │         Core Functionality (Current)            │ │  │
│  │  │  ┌─────────────────────────────────────────┐   │ │  │
│  │  │  │ AgentJarToolWindowFactory              │   │ │  │
│  │  │  │ - Creates tool window UI               │   │ │  │
│  │  │  │ - Static welcome message               │   │ │  │
│  │  │  └─────────────────────────────────────────┘   │ │  │
│  │  │                                                 │ │  │
│  │  │  ┌─────────────────────────────────────────┐   │ │  │
│  │  │  │ OpenAgentChatAction                    │   │ │  │
│  │  │  │ - Activates tool window                │   │ │  │
│  │  │  └─────────────────────────────────────────┘   │ │  │
│  │  │                                                 │ │  │
│  │  │  ┌─────────────────────────────────────────┐   │ │  │
│  │  │  │ ScaffoldSpringBootAction               │   │ │  │
│  │  │  │ - Creates directory structure          │   │ │  │
│  │  │  │ - Generates application.properties     │   │ │  │
│  │  │  │ - Creates DemoApplication.java         │   │ │  │
│  │  │  └─────────────────────────────────────────┘   │ │  │
│  │  │                                                 │ │  │
│  │  │  ┌─────────────────────────────────────────┐   │ │  │
│  │  │  │ ConventionalCommitAction               │   │ │  │
│  │  │  │ - Prompts for commit details           │   │ │  │
│  │  │  │ - Formats commit message               │   │ │  │
│  │  │  │ - Executes git commit                  │   │ │  │
│  │  │  └─────────────────────────────────────────┘   │ │  │
│  │  └─────────────────────────────────────────────────┘ │  │
│  └───────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

## Component Interaction Flow

### 1. Opening Agent Chat

```
User clicks "Tools > AgentJar > Open Agent Chat"
           ↓
    OpenAgentChatAction.actionPerformed()
           ↓
    ToolWindowManager.getToolWindow("AgentJar")
           ↓
    toolWindow.activate()
           ↓
    AgentJarToolWindowFactory.createToolWindowContent()
           ↓
    Displays welcome message in JTextArea
```

### 2. Scaffolding Spring Boot Project

```
User clicks "Tools > AgentJar > Scaffold Spring Boot Project"
           ↓
    ScaffoldSpringBootAction.actionPerformed()
           ↓
    Get project.basePath
           ↓
    Create directories:
    - src/main/java/com/example/demo
    - src/main/resources
           ↓
    Write application.properties:
    - spring.application.name=demo
    - server.port=8080
           ↓
    Write DemoApplication.java:
    - @SpringBootApplication
    - main() method
           ↓
    VirtualFileManager.refresh()
           ↓
    Show success message dialog
```

### 3. Creating Conventional Commit

```
User clicks "Tools > AgentJar > Conventional Commit..."
           ↓
    ConventionalCommitAction.actionPerformed()
           ↓
    Prompt for commit type (feat/fix/docs/etc.)
           ↓
    Prompt for scope (optional)
           ↓
    Prompt for description
           ↓
    Format message: "type(scope): description"
           ↓
    ProgressManager.run(BackgroundTask)
           ↓
    GitRepositoryManager.getInstance()
           ↓
    Execute: git commit -am "message"
           ↓
    Show success/error dialog
```

## Data Flow

### Current Implementation

```
User Input → Action → File System / Git
                 ↓
           UI Feedback (Dialogs)
```

### Future Implementation (with Agent Backend)

```
User Input → Chat UI → Agent Backend → LLM API
                           ↓
                      Code Analysis
                           ↓
                   Code Generation / Refactoring
                           ↓
                      Diff Preview
                           ↓
                  User Confirmation
                           ↓
                File System / Git / Docker
                           ↓
                      UI Feedback
```

## Plugin Dependencies

```
AgentJar Plugin
    ├── com.intellij.modules.platform (IntelliJ Platform)
    ├── com.intellij.java (Java language support)
    └── Git4Idea (Git integration)
```

## Future Architecture (Planned)

```
┌─────────────────────────────────────────────────────────────┐
│                    AgentJar Plugin                          │
│                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │
│  │  UI Layer    │  │ Action Layer │  │  Backend Layer   │  │
│  │              │  │              │  │                  │  │
│  │ - Chat UI    │→ │ - Actions    │→ │ - Agent API     │  │
│  │ - Dialogs    │  │ - Validators │  │ - LLM Adapters  │  │
│  │ - Tool Win   │  │ - Handlers   │  │ - Code Analysis │  │
│  └──────────────┘  └──────────────┘  └──────────────────┘  │
│         ↓                  ↓                    ↓           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │
│  │ Scaffold     │  │  Git Layer   │  │  Docker Layer    │  │
│  │ Templates    │  │              │  │                  │  │
│  │              │  │ - Commits    │  │ - Dockerfile Gen │  │
│  │ - Controller │  │ - GitHub API │  │ - Run Configs   │  │
│  │ - Service    │  │ - Branch Mgmt│  │ - Logs Viewer   │  │
│  │ - Repository │  │              │  │                  │  │
│  └──────────────┘  └──────────────┘  └──────────────────┘  │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐  │
│  │              Configuration & Settings                 │  │
│  │  - Backend selection (Ollama/OpenAI/MCP)             │  │
│  │  - API keys (secure storage)                         │  │
│  │  - Template preferences                              │  │
│  │  - Audit logging                                     │  │
│  └───────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

## Package Structure (Current)

```
com.agentjar/
├── AgentJarToolWindowFactory.kt
└── actions/
    ├── OpenAgentChatAction.kt
    ├── ScaffoldSpringBootAction.kt
    └── ConventionalCommitAction.kt
```

## Package Structure (Planned)

```
com.agentjar/
├── actions/                 # UI actions
│   ├── OpenAgentChatAction.kt
│   ├── ScaffoldSpringBootAction.kt
│   └── ConventionalCommitAction.kt
├── toolwindow/             # Tool window components
│   ├── AgentJarToolWindowFactory.kt
│   ├── ChatPanel.kt
│   ├── MessageRenderer.kt
│   └── InputField.kt
├── agent/                  # Agent backend
│   ├── AgentBackend.kt
│   ├── AgentMessage.kt
│   ├── AgentResponse.kt
│   ├── CodeContext.kt
│   └── backends/
│       ├── EchoBackend.kt
│       ├── OllamaBackend.kt
│       ├── MCPBackend.kt
│       └── OpenAIBackend.kt
├── scaffold/               # Code scaffolding
│   ├── SpringBootScaffolder.kt
│   ├── TemplateEngine.kt
│   └── templates/
│       ├── ControllerTemplate.kt
│       ├── ServiceTemplate.kt
│       └── RepositoryTemplate.kt
├── git/                    # Git integration
│   ├── ConventionalCommit.kt
│   ├── CommitValidator.kt
│   └── GitHubClient.kt
├── docker/                 # Docker integration
│   ├── DockerfileGenerator.kt
│   ├── DockerComposeGenerator.kt
│   └── ContainerManager.kt
├── config/                 # Configuration
│   ├── AgentJarSettings.kt
│   ├── AgentJarConfigurable.kt
│   └── SecureStorage.kt
└── util/                   # Utilities
    ├── FileUtils.kt
    ├── MarkdownRenderer.kt
    └── DiffPreview.kt
```

## IntelliJ Platform Integration Points

### Extension Points Used

1. **Tool Window Factory**
   - Extension: `com.intellij.toolWindow`
   - Factory: `AgentJarToolWindowFactory`
   - Anchor: Right sidebar

2. **Actions**
   - Extension: `com.intellij.actions`
   - Group: `AgentJar.Actions` in Tools menu
   - Three actions: OpenChat, Scaffold, Commit

### IntelliJ Platform APIs Used

1. **UI Components**
   - `ToolWindowFactory` - Tool window creation
   - `ContentFactory` - Tool window content
   - `Messages` - Dialog boxes
   - `JPanel`, `JTextArea` - Swing components

2. **Project Model**
   - `Project` - Current project reference
   - `VirtualFileManager` - File system operations

3. **Git Integration**
   - `GitRepositoryManager` - Git repository access
   - `ProcessBuilder` - Git command execution

4. **Background Tasks**
   - `ProgressManager` - Background task execution
   - `Task.Backgroundable` - Non-blocking operations

5. **Application Services**
   - `ApplicationManager` - Application-level operations
   - `invokeLater` - UI thread operations

## Thread Safety

### Current Implementation

- **UI Thread**: All UI operations use `invokeLater()`
- **Background Thread**: Git operations use `Task.Backgroundable`
- **File I/O**: Executed on EDT via `invokeLater()`

### Best Practices

1. File system writes → `WriteCommandAction`
2. Long operations → Background tasks
3. UI updates → EDT (Event Dispatch Thread)
4. Read-only operations → Read actions

## Error Handling

### Current Approach

```kotlin
try {
    // Operation
    Messages.showInfoMessage("Success")
} catch (ex: Exception) {
    Messages.showErrorDialog("Error: ${ex.message}")
}
```

### Future Improvements

1. Structured error types
2. User-friendly error messages
3. Retry mechanisms
4. Error reporting/telemetry
5. Rollback on failure

## Testing Strategy

### Unit Tests (Planned)

- Action handlers
- Template generation
- Commit message formatting
- Backend adapters

### Integration Tests (Planned)

- Tool window creation
- File scaffolding
- Git operations
- Agent backend communication

### UI Tests (Planned)

- Chat interaction
- Dialog flows
- Tool window behavior

## Performance Considerations

### Current

- Minimal overhead (tool window lazy-loaded)
- Quick actions (<100ms)
- File operations synchronous (acceptable for small files)

### Future Optimization

1. Lazy loading of agent backends
2. Caching of templates
3. Incremental project analysis
4. Background indexing
5. Connection pooling for LLM APIs

## Security Architecture

### Current

- Local file system access only
- No network calls
- No credential storage
- Git uses system credentials

### Future Security Features

1. **API Key Management**
   - Use IntelliJ PasswordSafe
   - Encrypted storage
   - Per-project keys

2. **Agent Actions**
   - User confirmation required
   - Audit logging
   - Permission model
   - Sandbox for generated code

3. **Network Security**
   - HTTPS only
   - Certificate validation
   - Request signing
   - Rate limiting

4. **Data Privacy**
   - Local-first mode
   - Configurable data sharing
   - Anonymization options
   - GDPR compliance

## Deployment Architecture

### Plugin Distribution

```
Development
    ↓
./gradlew buildPlugin
    ↓
agentjar-plugin-0.0.1.zip
    ↓
JetBrains Marketplace
    ↓
User Installation
```

### Custom IDE Distribution (Future)

```
Development
    ↓
Platform Build + Plugin
    ↓
Branding (icons, name)
    ↓
Platform Installers
    ↓
Windows/Mac/Linux Distributables
    ↓
User Installation
```

## Scalability Considerations

### Current Scale

- Single user
- Local operations
- No server dependencies

### Future Scale

- Multi-project support
- Team settings sync
- Shared templates
- Telemetry collection
- Usage analytics

## Monitoring & Observability

### Future Requirements

1. **Logging**
   - IntelliJ logger integration
   - Log level configuration
   - Structured logging

2. **Metrics**
   - Action usage statistics
   - Agent response times
   - Error rates
   - User engagement

3. **Diagnostics**
   - Debug mode
   - Performance profiling
   - Network diagnostics
   - Error reports

---

**Architecture Version**: 1.0 (Initial)
**Last Updated**: November 2024
**Status**: Foundation Complete, Ready for Enhancement
