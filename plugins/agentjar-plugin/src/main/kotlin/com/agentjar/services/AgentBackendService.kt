package com.agentjar.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Service for managing agent backends across the IDE.
 * Provides a centralized way to interact with different AI models.
 */
@Service(Service.Level.PROJECT)
class AgentBackendService(private val project: Project) {
    
    private val backends = mutableMapOf<String, AgentBackend>()
    private var activeBackendId: String? = null
    
    init {
        // Register default backends
        registerBackend(MockAgentBackend())
        // TODO: Add real backends when implemented
        // registerBackend(OllamaBackend())
        // registerBackend(MCPBackend())
        // registerBackend(OpenAIBackend())
        
        // Set mock as default for now
        activeBackendId = "mock"
    }
    
    /**
     * Register a new agent backend
     */
    fun registerBackend(backend: AgentBackend) {
        backends[backend.id] = backend
    }
    
    /**
     * Get all available backends
     */
    fun getAvailableBackends(): List<AgentBackend> = backends.values.toList()
    
    /**
     * Get the currently active backend
     */
    fun getActiveBackend(): AgentBackend? {
        return activeBackendId?.let { backends[it] }
    }
    
    /**
     * Set the active backend by ID
     */
    fun setActiveBackend(backendId: String): Boolean {
        return if (backends.containsKey(backendId)) {
            activeBackendId = backendId
            true
        } else {
            false
        }
    }
    
    /**
     * Send a chat message using the active backend
     */
    suspend fun chat(
        message: String,
        context: AgentContext? = null,
        conversationHistory: List<ChatMessage> = emptyList()
    ): ChatResponse {
        val backend = getActiveBackend() ?: throw IllegalStateException("No active backend")
        return withContext(Dispatchers.IO) {
            backend.chat(message, context, conversationHistory)
        }
    }
    
    /**
     * Generate code using the active backend
     */
    suspend fun generateCode(
        prompt: String,
        context: CodeContext
    ): CodeGenerationResponse {
        val backend = getActiveBackend() ?: throw IllegalStateException("No active backend")
        return withContext(Dispatchers.IO) {
            backend.generateCode(prompt, context)
        }
    }
    
    /**
     * Get refactoring suggestions using the active backend
     */
    suspend fun suggestRefactorings(
        code: String,
        context: CodeContext
    ): List<RefactoringSuggestion> {
        val backend = getActiveBackend() ?: throw IllegalStateException("No active backend")
        return withContext(Dispatchers.IO) {
            backend.suggestRefactorings(code, context)
        }
    }
    
    companion object {
        fun getInstance(project: Project): AgentBackendService {
            return project.getService(AgentBackendService::class.java)
        }
    }
}

/**
 * Mock implementation for testing and development.
 * Provides canned responses without requiring an actual AI backend.
 */
class MockAgentBackend : AgentBackend {
    override val id: String = "mock"
    override val name: String = "Mock Agent (for testing)"
    
    override suspend fun isAvailable(): Boolean = true
    
    override suspend fun chat(
        message: String,
        context: AgentContext?,
        conversationHistory: List<ChatMessage>
    ): ChatResponse {
        // Simulate processing time
        kotlinx.coroutines.delay(500)
        
        val response = when {
            message.contains("spring boot", ignoreCase = true) -> {
                """
                I can help you with Spring Boot development! Here are some common tasks:
                
                1. Create REST controllers
                2. Set up service layer
                3. Configure data sources
                4. Add security with Spring Security
                5. Create JPA entities
                
                What would you like to work on?
                """.trimIndent()
            }
            message.contains("controller", ignoreCase = true) -> {
                """
                I can help you create a Spring Boot controller. Here's a basic example:
                
                ```java
                @RestController
                @RequestMapping("/api")
                public class DemoController {
                    
                    @GetMapping("/hello")
                    public ResponseEntity<String> hello() {
                        return ResponseEntity.ok("Hello from AgentJar!");
                    }
                }
                ```
                
                Would you like me to customize this for your specific use case?
                """.trimIndent()
            }
            message.contains("docker", ignoreCase = true) -> {
                """
                For Spring Boot, I recommend a multi-stage Dockerfile:
                
                ```dockerfile
                FROM eclipse-temurin:17-jdk-alpine AS build
                WORKDIR /app
                COPY . .
                RUN ./mvnw package -DskipTests
                
                FROM eclipse-temurin:17-jre-alpine
                WORKDIR /app
                COPY --from=build /app/target/*.jar app.jar
                EXPOSE 8080
                ENTRYPOINT ["java", "-jar", "app.jar"]
                ```
                """.trimIndent()
            }
            else -> {
                """
                I'm AgentJar, your AI assistant for Spring Boot development!
                
                I can help with:
                • Creating controllers, services, and repositories
                • Database configuration and JPA entities
                • Docker containerization
                • Best practices and code reviews
                
                (Note: Currently using mock backend for testing. Real AI backends coming soon!)
                
                Your message: "$message"
                """.trimIndent()
            }
        }
        
        return ChatResponse(
            message = response,
            suggestions = listOf(
                "Generate a REST controller",
                "Create a Spring Boot service",
                "Set up Docker configuration"
            )
        )
    }
    
    override suspend fun generateCode(
        prompt: String,
        context: CodeContext
    ): CodeGenerationResponse {
        kotlinx.coroutines.delay(700)
        
        val code = when {
            prompt.contains("controller", ignoreCase = true) -> {
                """
                @RestController
                @RequestMapping("/api/${context.projectType ?: "demo"}")
                public class ${context.projectType?.capitalize() ?: "Demo"}Controller {
                    
                    @GetMapping
                    public ResponseEntity<List<String>> getAll() {
                        // TODO: Implement
                        return ResponseEntity.ok(List.of());
                    }
                    
                    @PostMapping
                    public ResponseEntity<String> create(@RequestBody String data) {
                        // TODO: Implement
                        return ResponseEntity.ok("Created");
                    }
                }
                """.trimIndent()
            }
            else -> {
                "// Generated code for: $prompt\n// TODO: Implement"
            }
        }
        
        return CodeGenerationResponse(
            code = code,
            explanation = "This is mock-generated code. In production, this would be generated by an AI model.",
            language = "java",
            imports = listOf(
                "org.springframework.web.bind.annotation.*",
                "org.springframework.http.ResponseEntity"
            )
        )
    }
    
    override suspend fun suggestRefactorings(
        code: String,
        context: CodeContext
    ): List<RefactoringSuggestion> {
        kotlinx.coroutines.delay(600)
        
        return listOf(
            RefactoringSuggestion(
                title = "Extract method",
                description = "Consider extracting this logic into a separate method",
                originalCode = code.take(100),
                refactoredCode = "// Refactored version would go here",
                confidence = 0.85,
                category = RefactoringCategory.STRUCTURE
            )
        )
    }
}
