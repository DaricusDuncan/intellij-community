package com.agentjar.ui

import com.agentjar.services.AgentBackendService
import com.agentjar.services.AgentContext
import com.agentjar.services.ChatMessage
import com.agentjar.services.MessageRole
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import java.awt.BorderLayout
import javax.swing.*

/**
 * Factory for creating the AgentJar tool window.
 * This provides the main UI for interacting with AI agents.
 */
class AgentJarToolWindowFactory : ToolWindowFactory {
    
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val agentJarWindow = AgentJarToolWindow(project)
        val contentFactory = ContentFactory.getInstance()
        val content = contentFactory.createContent(agentJarWindow.getContent(), "", false)
        toolWindow.contentManager.addContent(content)
    }
    
    override fun shouldBeAvailable(project: Project): Boolean = true
}

/**
 * Main tool window component with chat interface.
 */
class AgentJarToolWindow(private val project: Project) {
    
    private val mainPanel = JPanel(BorderLayout())
    private val chatArea = JTextArea()
    private val inputField = JTextField()
    private val sendButton = JButton("Send")
    private val backendService = AgentBackendService.getInstance(project)
    private val conversationHistory = mutableListOf<ChatMessage>()
    private val scope = CoroutineScope(Dispatchers.Swing)
    
    init {
        setupUI()
    }
    
    private fun setupUI() {
        // Chat display area
        chatArea.isEditable = false
        chatArea.lineWrap = true
        chatArea.wrapStyleWord = true
        
        val backend = backendService.getActiveBackend()
        chatArea.text = """
            Welcome to AgentJar! 🤖
            
            Your AI-powered assistant for Spring Boot development.
            Active Backend: ${backend?.name ?: "None"}
            
            Features:
            • Ask questions about your code
            • Generate Spring Boot components
            • Create conventional commits
            • Build Docker containers
            
            Type a message below to get started!
        """.trimIndent()
        
        val scrollPane = JScrollPane(chatArea)
        mainPanel.add(scrollPane, BorderLayout.CENTER)
        
        // Input panel
        val inputPanel = JPanel(BorderLayout(5, 0))
        inputPanel.border = BorderFactory.createEmptyBorder(5, 5, 5, 5)
        
        inputField.toolTipText = "Type your message or question here..."
        inputPanel.add(inputField, BorderLayout.CENTER)
        inputPanel.add(sendButton, BorderLayout.EAST)
        
        mainPanel.add(inputPanel, BorderLayout.SOUTH)
        
        // Event handlers
        sendButton.addActionListener { sendMessage() }
        inputField.addActionListener { sendMessage() }
    }
    
    private fun sendMessage() {
        val message = inputField.text.trim()
        if (message.isEmpty()) return
        
        // Add user message to chat
        appendToChat("\n\n[You]: $message")
        inputField.text = ""
        sendButton.isEnabled = false
        
        // Store in history
        conversationHistory.add(ChatMessage(MessageRole.USER, message))
        
        // Send to backend asynchronously
        scope.launch {
            try {
                val context = AgentContext(
                    projectName = project.name,
                    currentFile = null,  // TODO: Get current file from editor
                    selectedText = null,
                    language = "java"
                )
                
                val response = backendService.chat(message, context, conversationHistory)
                
                // Store response in history
                conversationHistory.add(ChatMessage(MessageRole.ASSISTANT, response.message))
                
                // Display response
                ApplicationManager.getApplication().invokeLater {
                    appendToChat("\n\n[Agent]: ${response.message}")
                    
                    // Show suggestions if any
                    if (response.suggestions.isNotEmpty()) {
                        appendToChat("\n\nSuggestions:")
                        response.suggestions.forEach { suggestion ->
                            appendToChat("\n  • $suggestion")
                        }
                    }
                    
                    sendButton.isEnabled = true
                }
            } catch (e: Exception) {
                ApplicationManager.getApplication().invokeLater {
                    appendToChat("\n\n[Error]: Failed to get response: ${e.message}")
                    sendButton.isEnabled = true
                }
            }
        }
    }
    
    private fun appendToChat(text: String) {
        chatArea.append(text)
        chatArea.caretPosition = chatArea.document.length
    }
    
    fun getContent(): JComponent = mainPanel
}
