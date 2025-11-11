# AgentJar Plugin - Architecture

## Overview

```
┌─────────────────────────────────────────────────────────────┐
│                     IntelliJ IDEA                            │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              AgentJar Plugin                         │   │
│  │                                                       │   │
│  │  ┌──────────────────┐    ┌────────────────────┐    │   │
│  │  │   Tool Window    │    │      Actions       │    │   │
│  │  │                  │    │                    │    │   │
│  │  │  AgentJarTool    │    │  • Open Chat       │    │   │
│  │  │  WindowFactory   │    │  • Scaffold Spring │    │   │
│  │  │                  │    │  • Conv. Commit    │    │   │
│  │  └──────────────────┘    └────────────────────┘    │   │
│  │                                                       │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                              │
│  ┌─────────────────────────────────────────────────────┐   │
│  │         IntelliJ Platform APIs                       │   │
│  │  • VFS  • PSI  • Editor  • VCS  • Project Model    │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

## Component Breakdown

### 1. Tool Window (`AgentJarToolWindowFactory.kt`)

**Purpose**: Provides the main UI for AgentJar

**Implementation**:
```kotlin
class AgentJarToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(
        project: Project, 
        toolWindow: ToolWindow
    ) {
        // Creates JPanel with text area
        // Future: Chat interface
    }
}
```

**Location**: Right sidebar in IntelliJ

**Future Extensions**:
- Chat input field
- Message history
- Agent status indicator
- Configuration panel

---

### 2. Actions

#### OpenAgentChatAction

**Purpose**: Activate the AgentJar tool window

**Flow**:
```
User clicks action
    ↓
AnActionEvent triggered
    ↓
Get ToolWindowManager
    ↓
Find "AgentJar" tool window
    ↓
Activate tool window
```

**Code**:
```kotlin
toolWindow?.activate(null)
```

---

#### ScaffoldSpringBootAction

**Purpose**: Create basic Spring Boot structure

**Flow**:
```
User clicks action
    ↓
Get project base path
    ↓
WriteAction.runAndWait:
  • Create src/main/resources/application.properties
  • Create src/main/java/.../DemoApplication.java
    ↓
Refresh VFS
```

**Files Created**:
- `application.properties` with `server.port=8080`
- `DemoApplication.java` with `@SpringBootApplication`

**Future Extensions**:
- Template selection (REST API, web app, microservice)
- Entity/Controller/Service/Repository generation
- Maven/Gradle build file creation
- Docker configuration
- Kubernetes manifests

---

#### ConventionalCommitAction

**Purpose**: Create git commits following conventional commit format

**Flow**:
```
User clicks action
    ↓
Prompt for type (feat, fix, etc.)
    ↓
Prompt for scope (optional)
    ↓
Prompt for description
    ↓
Format: type(scope): description
    ↓
Execute: git commit -am "message"
    ↓
Refresh VCS
```

**Format**: `type(scope): description`
- Types: feat, fix, docs, style, refactor, test, chore
- Scope: optional context
- Description: brief summary

**Future Extensions**:
- Breaking change indicator
- Multi-line commit body
- Git hook integration
- Commit template management
- AI-generated commit messages

---

## Plugin Configuration (`plugin.xml`)

```xml
<idea-plugin>
  <id>com.agentjar</id>
  <name>AgentJar</name>
  
  <!-- Dependencies -->
  <depends>com.intellij.modules.platform</depends>
  <depends>com.intellij.java</depends>
  <depends>Git4Idea</depends>
  
  <!-- Extensions -->
  <extensions defaultExtensionNs="com.intellij">
    <toolWindow id="AgentJar" anchor="right" 
                factoryClass="...AgentJarToolWindowFactory"/>
  </extensions>
  
  <!-- Actions -->
  <actions>
    <group id="AgentJar.ActionGroup" text="AgentJar">
      <add-to-group group-id="ToolsMenu" anchor="last"/>
      <action id="..." class="...Action" text="..." />
    </group>
  </actions>
</idea-plugin>
```

---

## Build System

### Gradle Configuration

```
build.gradle.kts
├── Plugins
│   ├── Kotlin JVM (1.9.24)
│   └── IntelliJ Platform (1.17.4)
│
├── Dependencies
│   ├── Kotlin stdlib
│   └── IntelliJ Platform (2024.2.4)
│       ├── Core APIs
│       ├── Java Plugin
│       └── Git4Idea Plugin
│
└── Tasks
    ├── compileKotlin
    ├── buildPlugin
    ├── runIde
    └── verifyPlugin
```

### Build Flow

```
./gradlew build
    ↓
Compile Kotlin sources
    ↓
Process resources (plugin.xml)
    ↓
Create JAR
    ↓
Instrument bytecode (IntelliJ)
    ↓
Package plugin
    ↓
build/libs/agentjar-plugin-0.0.1.jar
```

---

## Future Architecture

### Phase 1: Current (MVP)
```
Tool Window + Basic Actions
```

### Phase 2: Agent Backend
```
┌─────────────────────┐
│   Agent Interface   │
├─────────────────────┤
│  • Local (Ollama)   │
│  • Remote (OpenAI)  │
│  • MCP Server       │
└─────────────────────┘
```

### Phase 3: Advanced Features
```
┌─────────────────────────────────────┐
│         AgentJar IDE                │
├─────────────────────────────────────┤
│  Chat UI  │  Code Gen  │  Docker   │
├───────────┼────────────┼───────────┤
│  Agent    │  Spring    │  Git      │
│  Backend  │  Tools     │  Tools    │
├───────────┼────────────┼───────────┤
│  Security │  Audit     │  Offline  │
└─────────────────────────────────────┘
```

### Phase 4: Branded IDE
```
AgentJar IDE Distribution
├── IntelliJ Platform Core
├── AgentJar Plugin (bundled)
├── Java Support (bundled)
├── Spring Boot Tools (bundled)
├── Docker Integration (bundled)
└── Custom Branding
```

---

## Package Structure

```
com.agentjar
│
├── AgentJarToolWindowFactory.kt
│   └── Main tool window UI
│
├── actions/
│   ├── OpenAgentChatAction.kt
│   ├── ScaffoldSpringBootAction.kt
│   └── ConventionalCommitAction.kt
│
├── (future) agent/
│   ├── AgentInterface.kt
│   ├── LocalModelAgent.kt
│   ├── RemoteApiAgent.kt
│   └── McpServerAgent.kt
│
├── (future) spring/
│   ├── SpringProjectDetector.kt
│   ├── TemplateGenerator.kt
│   └── DependencyManager.kt
│
├── (future) docker/
│   ├── DockerfileGenerator.kt
│   ├── DockerBuildAction.kt
│   └── DockerRunAction.kt
│
└── (future) git/
    ├── ConventionalCommitValidator.kt
    ├── GitHubPushAction.kt
    └── CommitMessageGenerator.kt
```

---

## Extension Points

### Custom Extension Points (Future)

```kotlin
// Agent backend providers
<extensionPoint name="agentProvider" 
                interface="com.agentjar.agent.AgentProvider"/>

// Spring templates
<extensionPoint name="springTemplate" 
                interface="com.agentjar.spring.Template"/>

// Docker builders
<extensionPoint name="dockerBuilder" 
                interface="com.agentjar.docker.Builder"/>
```

---

## Data Flow

### Current: Scaffold Spring Boot

```
User Action
    ↓
ScaffoldSpringBootAction.actionPerformed()
    ↓
Get project.basePath
    ↓
WriteAction.runAndWait {
    Create directories
    Write files
    Refresh VFS
}
    ↓
Files appear in project
```

### Future: AI-Assisted Code Generation

```
User Input (Chat)
    ↓
AgentInterface.process()
    ↓
Route to Agent Backend
    ↓
Generate Response
    ↓
Parse Actions
    ↓
Show Diff Preview
    ↓
User Approval
    ↓
Execute Actions (create/edit files)
    ↓
Update UI
```

---

## Threading Model

### Current
- **EDT (Event Dispatch Thread)**: UI operations
- **Write Actions**: File modifications (via WriteAction.runAndWait)
- **Background**: None yet

### Future
```
EDT Thread
    │
    ├── UI Updates
    │   └── Tool Window, Dialogs, Notifications
    │
Background Thread
    │
    ├── Agent API Calls
    ├── File Analysis
    └── Code Generation
    
Write Thread
    │
    └── File Modifications (via WriteAction)
```

---

## Security Architecture (Future)

```
┌────────────────────────────────────┐
│        User Request                │
└────────────┬───────────────────────┘
             ↓
┌────────────────────────────────────┐
│     Permission Gate                │
│  • Show what will change           │
│  • Require explicit approval       │
└────────────┬───────────────────────┘
             ↓
┌────────────────────────────────────┐
│        Diff Preview                │
│  • Show file changes               │
│  • Highlight security concerns     │
└────────────┬───────────────────────┘
             ↓
┌────────────────────────────────────┐
│     Audit Log                      │
│  • Record all actions              │
│  • Track approvals/rejections      │
└────────────┬───────────────────────┘
             ↓
┌────────────────────────────────────┐
│     Execute Action                 │
└────────────────────────────────────┘
```

---

## Technology Stack

### Current
- **Language**: Kotlin 1.9.24
- **Platform**: IntelliJ Platform 2024.2.4
- **Build**: Gradle 8.7
- **JDK**: 17

### Future Additions
- **Agent**: Ollama, OpenAI API, Anthropic API
- **Protocol**: MCP (Model Context Protocol)
- **Docker**: Docker Java API
- **Git**: JGit + VCS APIs
- **HTTP**: OkHttp or similar

---

## Performance Considerations

### Current
- Lightweight: Minimal overhead
- Fast activation: Tool window loads instantly
- Efficient actions: Direct file I/O

### Future Optimizations
- Cache agent responses
- Background file scanning
- Incremental code generation
- Smart context extraction

---

## Extensibility

### Plugin Can Be Extended Via

1. **IntelliJ Extension Points**
   - Tool windows
   - Actions
   - Intentions
   - Inspections

2. **Custom Extension Points** (future)
   - Agent providers
   - Template engines
   - Docker builders

3. **External Integration**
   - MCP servers
   - Local models
   - Remote APIs

---

## References

- [IntelliJ Platform SDK](https://plugins.jetbrains.com/docs/intellij/)
- [IntelliJ Platform UI Guidelines](https://jetbrains.design/intellij/)
- [Kotlin Plugin Development](https://plugins.jetbrains.com/docs/intellij/kotlin.html)
- [Action System](https://plugins.jetbrains.com/docs/intellij/basic-action-system.html)
- [Tool Windows](https://plugins.jetbrains.com/docs/intellij/tool-windows.html)
