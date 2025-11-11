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
        val panel = JPanel(BorderLayout())
        val textArea = JTextArea("AgentJar Tool Window\n\nWelcome to AgentJar IDE!")
        textArea.isEditable = false
        panel.add(textArea, BorderLayout.CENTER)
        
        val contentFactory = ContentFactory.getInstance()
        val content = contentFactory.createContent(panel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}
