package com.agentjar

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import javax.swing.JPanel
import javax.swing.JTextArea
import java.awt.BorderLayout

class AgentJarToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentPanel = JPanel(BorderLayout())
        val textArea = JTextArea("AgentJar Agent is ready.\nAsk me to help with your Spring Boot project!")
        textArea.isEditable = false
        contentPanel.add(textArea, BorderLayout.CENTER)
        
        val contentFactory = ContentFactory.getInstance()
        val content = contentFactory.createContent(contentPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}
