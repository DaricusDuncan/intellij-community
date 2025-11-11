package com.agentjar.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.openapi.vcs.changes.ChangeListManager
import java.io.File

class ConventionalCommitAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        
        val type = Messages.showInputDialog(
            project,
            "Commit type (feat, fix, docs, style, refactor, test, chore):",
            "Conventional Commit",
            Messages.getQuestionIcon()
        ) ?: return
        
        val scope = Messages.showInputDialog(
            project,
            "Scope (optional):",
            "Conventional Commit",
            Messages.getQuestionIcon()
        ) ?: ""
        
        val description = Messages.showInputDialog(
            project,
            "Description:",
            "Conventional Commit",
            Messages.getQuestionIcon()
        ) ?: return
        
        val scopePart = if (scope.isNotBlank()) "($scope)" else ""
        val message = "$type$scopePart: $description"
        
        try {
            val basePath = project.basePath ?: return
            val process = ProcessBuilder("git", "commit", "-am", message)
                .directory(File(basePath))
                .redirectErrorStream(true)
                .start()
            
            process.waitFor()
            
            // Mark VCS dirty to refresh
            ChangeListManager.getInstance(project).scheduleUpdate()
            
            Messages.showInfoMessage(project, "Committed: $message", "Success")
        } catch (ex: Exception) {
            Messages.showErrorDialog(project, "Failed to commit: ${ex.message}", "Error")
        }
    }
}
