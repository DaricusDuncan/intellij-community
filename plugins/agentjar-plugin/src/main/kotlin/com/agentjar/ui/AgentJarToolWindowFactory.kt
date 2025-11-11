package com.agentjar.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
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
    
    init {
        setupUI()
    }
    
    private fun setupUI() {
        // Chat display area
        chatArea.isEditable = false
        chatArea.lineWrap = true
        chatArea.wrapStyleWord = true
        chatArea.text = """
            Welcome to AgentJar! 🤖
            
            Your AI-powered assistant for Spring Boot development.
            
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
        chatArea.append("\n\n[You]: $message")
        inputField.text = ""
        
        // TODO: Send to agent backend and get response
        // For now, just acknowledge
        chatArea.append("\n\n[Agent]: Message received! Agent backend integration coming soon...")
        
        // Auto-scroll to bottom
        chatArea.caretPosition = chatArea.document.length
    }
    
    fun getContent(): JComponent = mainPanel
}
