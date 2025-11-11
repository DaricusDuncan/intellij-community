# AgentJar Plugin - Usage Guide

This guide provides detailed instructions on using the AgentJar plugin for Spring Boot development.

## Table of Contents
1. [Getting Started](#getting-started)
2. [Agent Chat](#agent-chat)
3. [Spring Boot Scaffolding](#spring-boot-scaffolding)
4. [Conventional Commits](#conventional-commits)
5. [Docker Integration](#docker-integration)
6. [Configuration](#configuration)
7. [Keyboard Shortcuts](#keyboard-shortcuts)
8. [Troubleshooting](#troubleshooting)

## Getting Started

After installing the AgentJar plugin, you'll see a new tool window on the right side of your IDE labeled "AgentJar". You'll also have a new "AgentJar" menu in the main menu bar.

### First Time Setup

1. **Open the Agent Chat**: Click the AgentJar tool window or go to `AgentJar > Open Agent Chat`
2. **Choose your backend**: Currently using Mock Agent (real AI backends coming soon)
3. **Start chatting**: Type a message in the input field and press Enter or click Send

## Agent Chat

The Agent Chat window is your main interface for interacting with AI assistants.

### Features

- **Contextual Conversations**: The agent remembers your conversation history
- **Code Suggestions**: Get code examples for Spring Boot components
- **Follow-up Questions**: Agent provides suggestions for next steps
- **Async Processing**: The UI remains responsive while the agent thinks

### Example Interactions

**Creating a REST Controller:**
```
You: Create a REST controller for user management

Agent: I can help you create a Spring Boot controller. Here's a basic example:
[Shows code with @RestController, endpoints, etc.]

Suggestions:
• Generate a REST controller
• Create a Spring Boot service
• Set up Docker configuration
```

**Getting Docker Help:**
```
You: How do I containerize my Spring Boot app?

Agent: For Spring Boot, I recommend a multi-stage Dockerfile...
[Shows Dockerfile example]
```

### Tips

- Be specific in your questions
- Mention the technology stack you're using
- Ask for examples when you need code
- Use follow-up questions to refine responses

## Spring Boot Scaffolding

Quickly create a minimal Spring Boot project structure.

### Using the Scaffolding Action

1. **From Menu**: `AgentJar > Scaffold Spring Boot Project`
2. **From Right-Click**: Right-click in project view (coming soon)

### What Gets Created

```
your-project/
├── src/
│   └── main/
│       ├── java/com/agentjar/demo/
│       │   └── DemoApplication.java
│       └── resources/
│           └── application.properties
└── README.md
```

### Generated Files

**DemoApplication.java** - Main Spring Boot application class with @SpringBootApplication annotation

**application.properties** - Basic Spring Boot configuration including:
- Application name
- Server port (8080)
- Logging configuration

**README.md** - Project documentation with:
- Prerequisites
- How to run the application
- Next steps

### After Scaffolding

You'll need to add your build configuration:

**For Maven (pom.xml):**
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

**For Gradle (build.gradle.kts):**
```kotlin
plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.21"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
}
```

## Conventional Commits

Create well-formatted Git commits following the Conventional Commits specification.

### Opening the Commit Dialog

1. **From Menu**: `AgentJar > Create Conventional Commit`
2. Make sure you have staged changes in Git

### Commit Format

```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

### Commit Types

- **feat**: A new feature
- **fix**: A bug fix
- **docs**: Documentation only changes
- **style**: Code style changes (formatting, etc.)
- **refactor**: Code refactoring
- **perf**: Performance improvements
- **test**: Adding or updating tests
- **build**: Build system changes
- **ci**: CI configuration changes
- **chore**: Other changes that don't modify src or test files

### Using the Dialog

1. **Select Type**: Choose from dropdown (feat, fix, docs, etc.)
2. **Enter Scope** (optional): Component name (e.g., api, auth, ui)
3. **Write Description**: Short summary (imperative mood, lowercase)
4. **Mark Breaking Change** (optional): Check if this breaks backward compatibility
5. **Add Body** (optional): Detailed explanation
6. **Click OK**: Commit is created

### Examples

**Simple Feature:**
```
feat(api): add user registration endpoint
```

**Bug Fix with Scope:**
```
fix(auth): resolve token expiration issue
```

**Breaking Change:**
```
feat(api)!: change response format to JSON:API spec

BREAKING CHANGE: API responses now follow JSON:API specification.
Clients need to update their response parsers.
```

### Validation Rules

- Description is required
- Description must be 72 characters or less
- Description should start with lowercase
- Scope is optional but recommended

## Docker Integration

Generate production-ready Dockerfiles for your Spring Boot applications.

### Generating a Dockerfile

1. **From Menu**: `AgentJar > Generate Dockerfile`
2. **Confirm**: If a Dockerfile exists, you'll be asked to confirm overwrite
3. **Review**: Check the generated Dockerfile and .dockerignore

### What Gets Generated

**Dockerfile** - Multi-stage build optimized for:
- **Maven** projects (if pom.xml exists)
- **Gradle** projects (if build.gradle or build.gradle.kts exists)

**Features of Generated Dockerfile:**
- Multi-stage builds (smaller final image)
- Layer caching for faster builds
- Non-root user for security
- Health checks for monitoring
- Java 17 (Alpine-based, minimal size)

**.dockerignore** - Excludes:
- Build artifacts (target/, build/)
- IDE files (.idea/, *.iml)
- Git repository
- Logs and temporary files

### Building and Running

After generation, use these commands:

```bash
# Build the Docker image
docker build -t my-spring-app .

# Run the container
docker run -p 8080:8080 my-spring-app

# Run with environment variables
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  my-spring-app
```

### Customizing the Dockerfile

You can modify the generated Dockerfile to:
- Change Java version
- Add additional dependencies
- Configure environment variables
- Adjust health check settings
- Add volume mounts

## Configuration

### Agent Backend Selection

Currently, the plugin uses a Mock Agent for testing. In the future, you'll be able to configure:

- **Ollama**: Local models running on your machine
- **MCP Server**: Model Context Protocol servers
- **Remote APIs**: OpenAI, Anthropic, etc.

Configuration will be available in: `Settings > Tools > AgentJar`

## Keyboard Shortcuts

Default keyboard shortcuts (can be customized in Settings):

- **Open Agent Chat**: (Not set by default)
- **Scaffold Spring Boot**: (Not set by default)
- **Conventional Commit**: (Not set by default)

### Setting Custom Shortcuts

1. Go to `Settings > Keymap`
2. Search for "AgentJar"
3. Right-click on an action
4. Select "Add Keyboard Shortcut"
5. Press your desired key combination

## Troubleshooting

### Agent Chat Not Responding

**Issue**: Messages sent but no response appears

**Solutions**:
- Check if the Mock Agent backend is active
- Look at the IDE's Event Log for errors
- Restart the IDE and try again

### Scaffolding Fails

**Issue**: "Cannot find project directory" error

**Solutions**:
- Make sure you have a project open
- Ensure the project has a base directory
- Check file permissions

### Conventional Commit Not Working

**Issue**: Commit button does nothing

**Solutions**:
- Make sure you have changes staged in Git
- Check if Git is properly configured for the project
- Verify Git4Idea plugin is enabled

### Docker Generation Creates Wrong Build Tool

**Issue**: Generated Maven Dockerfile but project uses Gradle

**Solutions**:
- Make sure build.gradle or build.gradle.kts exists in project root
- If both pom.xml and build.gradle exist, the plugin prefers Maven
- You can manually edit the generated Dockerfile

### Plugin Actions Not Visible

**Issue**: Can't find AgentJar menu or actions

**Solutions**:
- Verify plugin is installed: `Settings > Plugins`
- Check if plugin is enabled
- Restart the IDE
- Ensure you're using IntelliJ IDEA 2024.2 or later

## Getting Help

### Documentation
- README.md - Installation and overview
- USAGE.md - This file
- Inline code comments

### Support Channels
- GitHub Issues: Report bugs and request features
- Email: support@agentjar.dev

### Contributing
- Source code: https://github.com/DaricusDuncan/intellij-community
- Plugin path: plugins/agentjar-plugin/

## Tips and Best Practices

### 1. Use Descriptive Commit Messages
Even with conventional commits, make your descriptions clear and actionable.

### 2. Scaffold Early
Start new projects with the scaffolding action to ensure consistent structure.

### 3. Review Generated Code
Always review what the agent generates. It's there to assist, not replace your judgment.

### 4. Leverage Docker Multi-Stage Builds
The generated Dockerfiles use multi-stage builds. Understand this pattern for better Docker usage.

### 5. Keep Conversations Contextual
The agent remembers conversation history, so you can ask follow-up questions naturally.

## What's Next?

Upcoming features:
- Real AI backend integration (Ollama, OpenAI)
- Spring Boot component generation (services, repositories, controllers)
- Code refactoring suggestions
- Test generation
- Docker build/run integration
- Offline mode
- Security audit logging

Stay tuned for updates!
