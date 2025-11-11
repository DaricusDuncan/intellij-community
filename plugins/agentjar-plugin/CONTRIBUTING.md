# Contributing to AgentJar

Thank you for your interest in contributing to AgentJar! This document provides guidelines and instructions for contributing to the project.

## Table of Contents
1. [Getting Started](#getting-started)
2. [Development Setup](#development-setup)
3. [Project Structure](#project-structure)
4. [Making Changes](#making-changes)
5. [Testing](#testing)
6. [Submitting Changes](#submitting-changes)
7. [Code Style](#code-style)
8. [Adding New Features](#adding-new-features)

## Getting Started

### Prerequisites
- JDK 17 or higher
- IntelliJ IDEA 2024.2 or later
- Gradle 8.10
- Git
- Basic understanding of IntelliJ Platform plugin development

### Helpful Resources
- [IntelliJ Platform Plugin SDK](https://plugins.jetbrains.com/docs/intellij/welcome.html)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Conventional Commits](https://www.conventionalcommits.org/)

## Development Setup

### 1. Clone the Repository

```bash
git clone https://github.com/DaricusDuncan/intellij-community.git
cd intellij-community/plugins/agentjar-plugin
```

### 2. Build the Project

```bash
./gradlew build
```

### 3. Run in Development Mode

```bash
./gradlew runIde
```

This will start a new IntelliJ IDEA instance with the plugin installed.

### 4. Run Tests

```bash
./gradlew test
```

## Project Structure

```
agentjar-plugin/
├── src/
│   ├── main/
│   │   ├── kotlin/com/agentjar/
│   │   │   ├── actions/          # User-triggered actions
│   │   │   │   ├── OpenAgentChatAction.kt
│   │   │   │   ├── ScaffoldSpringBootAction.kt
│   │   │   │   ├── ConventionalCommitAction.kt
│   │   │   │   └── GenerateDockerfileAction.kt
│   │   │   ├── services/         # Backend services
│   │   │   │   ├── AgentBackend.kt
│   │   │   │   └── AgentBackendService.kt
│   │   │   └── ui/               # UI components
│   │   │       └── AgentJarToolWindowFactory.kt
│   │   └── resources/
│   │       └── META-INF/
│   │           └── plugin.xml    # Plugin manifest
│   └── test/
│       └── kotlin/com/agentjar/
│           └── services/
│               └── AgentBackendServiceTest.kt
├── build.gradle.kts              # Build configuration
├── settings.gradle.kts           # Gradle settings
├── README.md                     # Main documentation
├── USAGE.md                      # User guide
└── CONTRIBUTING.md               # This file
```

## Making Changes

### Branch Naming Convention

- `feature/your-feature-name` - New features
- `fix/bug-description` - Bug fixes
- `refactor/what-you-refactored` - Code refactoring
- `docs/what-you-documented` - Documentation updates

### Commit Message Format

We use Conventional Commits:

```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

**Examples:**
```
feat(agent): add Ollama backend integration
fix(scaffold): resolve directory creation issue
docs(readme): update installation instructions
```

## Testing

### Writing Tests

Tests should be placed in `src/test/kotlin/com/agentjar/` mirroring the source structure.

**Example Test:**

```kotlin
package com.agentjar.services

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MyFeatureTest {
    
    @Before
    fun setUp() {
        // Setup code
    }
    
    @Test
    fun `test feature does something correctly`() {
        // Test implementation
        assertTrue("Feature should work", true)
    }
}
```

### Running Specific Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "AgentBackendServiceTest"

# Run with debug output
./gradlew test --info
```

### Test Coverage

Aim for:
- **80%+ coverage** for services and core logic
- **60%+ coverage** for actions and UI components
- **100% coverage** for critical security features

## Submitting Changes

### 1. Create a Fork

Fork the repository on GitHub.

### 2. Create a Branch

```bash
git checkout -b feature/my-new-feature
```

### 3. Make Your Changes

- Write clean, readable code
- Add tests for new functionality
- Update documentation as needed
- Follow the code style guidelines

### 4. Test Your Changes

```bash
# Run tests
./gradlew test

# Test in development IDE
./gradlew runIde

# Verify plugin builds
./gradlew buildPlugin
```

### 5. Commit Your Changes

```bash
git add .
git commit -m "feat(scope): add new feature"
```

### 6. Push to Your Fork

```bash
git push origin feature/my-new-feature
```

### 7. Create a Pull Request

1. Go to the original repository on GitHub
2. Click "New Pull Request"
3. Select your fork and branch
4. Fill in the PR template
5. Submit for review

### Pull Request Guidelines

**Title Format:**
```
[TYPE] Brief description of changes
```

**Description Should Include:**
- What changes were made
- Why the changes were necessary
- Any breaking changes
- Screenshots (for UI changes)
- Related issues

**Example:**
```markdown
## Changes
- Added Ollama backend integration
- Implemented connection pooling
- Added configuration UI

## Why
Users requested local model support for offline usage.

## Breaking Changes
None

## Screenshots
[Attach screenshots if UI changed]

## Related Issues
Closes #123
```

## Code Style

### Kotlin Style Guide

Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html).

**Key Points:**

1. **Naming**
   - Classes: PascalCase
   - Functions: camelCase
   - Constants: UPPER_SNAKE_CASE

2. **Formatting**
   - Indentation: 4 spaces
   - Max line length: 120 characters
   - Opening braces on same line

3. **Documentation**
   - Use KDoc for public APIs
   - Include examples where helpful

**Example:**

```kotlin
/**
 * Service for managing agent backends.
 *
 * @param project The IntelliJ project instance
 */
@Service(Service.Level.PROJECT)
class AgentBackendService(private val project: Project) {
    
    /**
     * Registers a new agent backend.
     *
     * @param backend The backend to register
     */
    fun registerBackend(backend: AgentBackend) {
        backends[backend.id] = backend
    }
}
```

### IntelliJ Platform Best Practices

1. **Use Services for Stateful Components**
   ```kotlin
   @Service(Service.Level.PROJECT)
   class MyService(private val project: Project)
   ```

2. **Use WriteAction for File Modifications**
   ```kotlin
   WriteAction.run<IOException> {
       file.createChildData(this, "example.txt")
   }
   ```

3. **Use Application.invokeLater for UI Updates**
   ```kotlin
   ApplicationManager.getApplication().invokeLater {
       // Update UI
   }
   ```

4. **Use Coroutines for Async Operations**
   ```kotlin
   scope.launch {
       val result = withContext(Dispatchers.IO) {
           // Async work
       }
   }
   ```

## Adding New Features

### Adding a New Action

1. **Create Action Class**

```kotlin
package com.agentjar.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class MyNewAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        // Implementation
    }
    
    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = e.project != null
    }
}
```

2. **Register in plugin.xml**

```xml
<action 
    id="AgentJar.MyNewAction" 
    class="com.agentjar.actions.MyNewAction" 
    text="My New Action" 
    description="Description of what this does"/>
```

3. **Add to Menu**

```xml
<group id="AgentJar.Menu">
    <!-- Existing actions -->
    <action id="AgentJar.MyNewAction" .../>
</group>
```

### Adding a New Agent Backend

1. **Implement AgentBackend Interface**

```kotlin
package com.agentjar.services

class OllamaBackend : AgentBackend {
    override val id: String = "ollama"
    override val name: String = "Ollama (Local)"
    
    override suspend fun isAvailable(): Boolean {
        // Check if Ollama is running
    }
    
    override suspend fun chat(...): ChatResponse {
        // Implement Ollama chat
    }
    
    // Implement other methods
}
```

2. **Register in AgentBackendService**

```kotlin
init {
    registerBackend(MockAgentBackend())
    registerBackend(OllamaBackend())  // Add here
}
```

3. **Add Configuration UI** (optional)

Create a settings page in `Settings > Tools > AgentJar`.

### Adding a New Service

1. **Create Service Class**

```kotlin
@Service(Service.Level.PROJECT)
class MyService(private val project: Project) {
    
    fun doSomething() {
        // Implementation
    }
    
    companion object {
        fun getInstance(project: Project): MyService {
            return project.getService(MyService::class.java)
        }
    }
}
```

2. **Register in plugin.xml**

```xml
<extensions defaultExtensionNs="com.intellij">
    <projectService serviceImplementation="com.agentjar.services.MyService"/>
</extensions>
```

3. **Use in Code**

```kotlin
val service = MyService.getInstance(project)
service.doSomething()
```

## Common Tasks

### Updating Dependencies

Edit `build.gradle.kts`:

```kotlin
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    // Add new dependency
}
```

### Adding a New Menu

Edit `plugin.xml`:

```xml
<actions>
    <group id="AgentJar.NewMenu" text="My Menu">
        <add-to-group group-id="MainMenu" anchor="after" relative-to-action="ToolsMenu"/>
        <!-- Actions here -->
    </group>
</actions>
```

### Debugging

1. **Set breakpoints** in your code
2. **Run in debug mode:**
   ```bash
   ./gradlew runIde --debug-jvm
   ```
3. **Attach debugger** to port 5005

### Logging

```kotlin
import com.intellij.openapi.diagnostic.Logger

class MyClass {
    private val log = Logger.getInstance(MyClass::class.java)
    
    fun doSomething() {
        log.info("Doing something")
        log.warn("Warning message")
        log.error("Error message", exception)
    }
}
```

## Getting Help

### Questions?

- **GitHub Discussions**: Ask questions and discuss ideas
- **Issues**: Report bugs and request features
- **Email**: support@agentjar.dev

### Useful Links

- [IntelliJ Platform SDK](https://plugins.jetbrains.com/docs/intellij/welcome.html)
- [Plugin Development Forum](https://intellij-support.jetbrains.com/hc/en-us/community/topics/200366979-IntelliJ-IDEA-Open-API-and-Plugin-Development)
- [Kotlin Slack](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up)

## Recognition

Contributors will be recognized in:
- README.md contributors section
- Release notes
- GitHub contributor graph

Thank you for contributing to AgentJar! 🚀
