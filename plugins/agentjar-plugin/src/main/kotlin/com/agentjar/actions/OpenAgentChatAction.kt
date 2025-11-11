package com.agentjar.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager

/**
 * Action to open and activate the AgentJar tool window.
 */
class OpenAgentChatAction : AnAction() {
    
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        
        val toolWindowManager = ToolWindowManager.getInstance(project)
        val toolWindow = toolWindowManager.getToolWindow("AgentJar")
        
        toolWindow?.let {
            it.show()
            it.activate(null)
        }
    }
    
    override fun update(e: AnActionEvent) {
        // Enable action only when a project is open
        e.presentation.isEnabled = e.project != null
    }
}
