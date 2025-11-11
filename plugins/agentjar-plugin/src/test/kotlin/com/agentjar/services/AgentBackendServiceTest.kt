package com.agentjar.services

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests for AgentBackendService and MockAgentBackend
 */
class AgentBackendServiceTest {
    
    private lateinit var mockBackend: MockAgentBackend
    
    @Before
    fun setUp() {
        mockBackend = MockAgentBackend()
    }
    
    @Test
    fun `test mock backend is available`() = runBlocking {
        assertTrue("Mock backend should always be available", mockBackend.isAvailable())
    }
    
    @Test
    fun `test mock backend has correct id and name`() {
        assertEquals("mock", mockBackend.id)
        assertEquals("Mock Agent (for testing)", mockBackend.name)
    }
    
    @Test
    fun `test chat returns response`() = runBlocking {
        val response = mockBackend.chat("Hello", null, emptyList())
        
        assertNotNull("Response should not be null", response)
        assertNotNull("Response message should not be null", response.message)
        assertTrue("Response should contain text", response.message.isNotEmpty())
    }
    
    @Test
    fun `test chat about spring boot returns relevant response`() = runBlocking {
        val response = mockBackend.chat("Tell me about Spring Boot", null, emptyList())
        
        assertTrue(
            "Response should mention Spring Boot",
            response.message.contains("Spring Boot", ignoreCase = true)
        )
        assertTrue(
            "Response should have suggestions",
            response.suggestions.isNotEmpty()
        )
    }
    
    @Test
    fun `test chat about controller returns code example`() = runBlocking {
        val response = mockBackend.chat("Create a controller", null, emptyList())
        
        assertTrue(
            "Response should contain controller code",
            response.message.contains("@RestController", ignoreCase = true)
        )
    }
    
    @Test
    fun `test generate code returns valid response`() = runBlocking {
        val context = CodeContext(
            filePath = "/test/Controller.java",
            language = "java",
            projectType = "spring-boot"
        )
        
        val response = mockBackend.generateCode("Create a REST controller", context)
        
        assertNotNull("Code should not be null", response.code)
        assertTrue("Code should not be empty", response.code.isNotEmpty())
        assertEquals("Language should be java", "java", response.language)
        assertTrue("Explanation should be provided", response.explanation.isNotEmpty())
        assertTrue("Imports should be provided", response.imports.isNotEmpty())
    }
    
    @Test
    fun `test suggest refactorings returns suggestions`() = runBlocking {
        val context = CodeContext(
            filePath = "/test/Service.java",
            language = "java"
        )
        
        val suggestions = mockBackend.suggestRefactorings("public class Test {}", context)
        
        assertNotNull("Suggestions should not be null", suggestions)
        assertTrue("Should have at least one suggestion", suggestions.isNotEmpty())
        
        val suggestion = suggestions.first()
        assertNotNull("Suggestion title should not be null", suggestion.title)
        assertTrue("Confidence should be between 0 and 1", 
            suggestion.confidence >= 0.0 && suggestion.confidence <= 1.0)
    }
    
    @Test
    fun `test conversation history is maintained`() = runBlocking {
        val history = mutableListOf<ChatMessage>()
        
        // First message
        history.add(ChatMessage(MessageRole.USER, "Hello"))
        val response1 = mockBackend.chat("Hello", null, history)
        history.add(ChatMessage(MessageRole.ASSISTANT, response1.message))
        
        // Second message
        val response2 = mockBackend.chat("Tell me more", null, history)
        
        assertNotNull("Second response should not be null", response2)
        assertEquals("History should have 2 messages", 2, history.size)
    }
    
    @Test
    fun `test agent context is properly structured`() {
        val context = AgentContext(
            projectName = "test-project",
            currentFile = "Controller.java",
            selectedText = "public void test() {}",
            language = "java",
            additionalInfo = mapOf("framework" to "spring-boot")
        )
        
        assertEquals("test-project", context.projectName)
        assertEquals("Controller.java", context.currentFile)
        assertEquals("java", context.language)
        assertEquals("spring-boot", context.additionalInfo["framework"])
    }
    
    @Test
    fun `test refactoring categories are properly defined`() {
        val categories = RefactoringCategory.values()
        
        assertTrue("Should have refactoring categories", categories.isNotEmpty())
        assertTrue("Should have NAMING category", 
            categories.contains(RefactoringCategory.NAMING))
        assertTrue("Should have SECURITY category", 
            categories.contains(RefactoringCategory.SECURITY))
    }
}
