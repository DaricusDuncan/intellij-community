package com.agentjar.services

/**
 * Model-agnostic interface for AI agent backends.
 * Implementations can support local models (Ollama), MCP servers, remote APIs (OpenAI, etc.)
 */
interface AgentBackend {
    /**
     * Unique identifier for this backend (e.g., "ollama", "openai", "mcp")
     */
    val id: String
    
    /**
     * Human-readable name for this backend (e.g., "Local Ollama", "OpenAI GPT-4")
     */
    val name: String
    
    /**
     * Check if this backend is currently available and configured
     */
    suspend fun isAvailable(): Boolean
    
    /**
     * Send a chat message and receive a response
     * 
     * @param message The user's message
     * @param context Optional context about the current project/file
     * @param conversationHistory Previous messages in the conversation
     * @return The agent's response
     */
    suspend fun chat(
        message: String,
        context: AgentContext? = null,
        conversationHistory: List<ChatMessage> = emptyList()
    ): ChatResponse
    
    /**
     * Request code completion or generation
     * 
     * @param prompt Description of what code to generate
     * @param context Current code context
     * @return Generated code with explanation
     */
    suspend fun generateCode(
        prompt: String,
        context: CodeContext
    ): CodeGenerationResponse
    
    /**
     * Suggest refactorings for given code
     * 
     * @param code The code to analyze
     * @param context Additional context
     * @return List of suggested refactorings
     */
    suspend fun suggestRefactorings(
        code: String,
        context: CodeContext
    ): List<RefactoringSuggestion>
}

/**
 * Context information about the agent's environment
 */
data class AgentContext(
    val projectName: String,
    val currentFile: String? = null,
    val selectedText: String? = null,
    val language: String? = null,
    val additionalInfo: Map<String, String> = emptyMap()
)

/**
 * Context specifically for code-related operations
 */
data class CodeContext(
    val filePath: String,
    val language: String,
    val existingCode: String? = null,
    val cursorPosition: Int? = null,
    val projectType: String? = null  // e.g., "spring-boot", "maven", "gradle"
)

/**
 * A single message in a conversation
 */
data class ChatMessage(
    val role: MessageRole,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MessageRole {
    USER,
    ASSISTANT,
    SYSTEM
}

/**
 * Response from a chat interaction
 */
data class ChatResponse(
    val message: String,
    val suggestions: List<String> = emptyList(),  // Follow-up suggestions
    val codeBlocks: List<CodeBlock> = emptyList()  // Any code included in response
)

/**
 * A code block extracted from a response
 */
data class CodeBlock(
    val language: String,
    val code: String,
    val description: String? = null
)

/**
 * Response from code generation request
 */
data class CodeGenerationResponse(
    val code: String,
    val explanation: String,
    val language: String,
    val imports: List<String> = emptyList(),
    val tests: String? = null  // Optional generated tests
)

/**
 * A suggested refactoring
 */
data class RefactoringSuggestion(
    val title: String,
    val description: String,
    val originalCode: String,
    val refactoredCode: String,
    val confidence: Double,  // 0.0 to 1.0
    val category: RefactoringCategory
)

enum class RefactoringCategory {
    NAMING,
    STRUCTURE,
    PERFORMANCE,
    SECURITY,
    BEST_PRACTICES,
    DESIGN_PATTERN
}
