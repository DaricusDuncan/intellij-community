package com.agentjar.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.ui.Messages
import git4idea.repo.GitRepositoryManager
import java.io.File

class ConventionalCommitAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        
        // Prompt for commit details
        val type = Messages.showInputDialog(
            project,
            "Enter commit type (feat, fix, docs, style, refactor, test, chore):",
            "Conventional Commit - Type",
            Messages.getQuestionIcon()
        ) ?: return
        
        val scope = Messages.showInputDialog(
            project,
            "Enter scope (optional, press Cancel to skip):",
            "Conventional Commit - Scope",
            Messages.getQuestionIcon()
        )
        
        val description = Messages.showInputDialog(
            project,
            "Enter description:",
            "Conventional Commit - Description",
            Messages.getQuestionIcon()
        ) ?: return
        
        // Build commit message
        val commitMessage = if (scope.isNullOrBlank()) {
            "$type: $description"
        } else {
            "$type($scope): $description"
        }
        
        // Execute git commit
        ProgressManager.getInstance().run(object : Task.Backgroundable(project, "Creating Conventional Commit", false) {
            override fun run(indicator: ProgressIndicator) {
                try {
                    val repoManager = GitRepositoryManager.getInstance(project)
                    val repos = repoManager.repositories
                    if (repos.isEmpty()) {
                        ApplicationManager.getApplication().invokeLater {
                            Messages.showErrorDialog(project, "No Git repository found", "Git Error")
                        }
                        return
                    }
                    
                    val repoRoot = repos[0].root.path
                    val process = ProcessBuilder("git", "commit", "-am", commitMessage)
                        .directory(File(repoRoot))
                        .redirectErrorStream(true)
                        .start()
                    
                    val exitCode = process.waitFor()
                    val output = process.inputStream.bufferedReader().readText()
                    
                    ApplicationManager.getApplication().invokeLater {
                        if (exitCode == 0) {
                            Messages.showInfoMessage(project, "Commit created successfully!", "AgentJar")
                        } else {
                            Messages.showErrorDialog(project, "Git commit failed:\n$output", "Git Error")
                        }
                    }
                } catch (ex: Exception) {
                    ApplicationManager.getApplication().invokeLater {
                        Messages.showErrorDialog(project, "Error: ${ex.message}", "AgentJar Error")
                    }
                }
            }
        })
    }
}
