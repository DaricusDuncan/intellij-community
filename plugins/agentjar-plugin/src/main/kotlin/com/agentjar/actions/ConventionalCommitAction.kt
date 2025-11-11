package com.agentjar.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.ValidationInfo
import git4idea.commands.Git
import git4idea.commands.GitCommand
import git4idea.commands.GitLineHandler
import git4idea.repo.GitRepositoryManager
import java.awt.BorderLayout
import javax.swing.*

/**
 * Action to create a conventional commit.
 * Follows the Conventional Commits specification: https://www.conventionalcommits.org/
 */
class ConventionalCommitAction : AnAction() {
    
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        
        // Check if Git is available
        val repositoryManager = GitRepositoryManager.getInstance(project)
        val repositories = repositoryManager.repositories
        
        if (repositories.isEmpty()) {
            Messages.showErrorDialog(
                project,
                "No Git repository found in this project.",
                "Git Not Available"
            )
            return
        }
        
        val dialog = ConventionalCommitDialog(project)
        if (dialog.showAndGet()) {
            val commitMessage = dialog.getCommitMessage()
            performCommit(project, commitMessage)
        }
    }
    
    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = e.project != null
    }
    
    private fun performCommit(project: Project, message: String) {
        try {
            val repositoryManager = GitRepositoryManager.getInstance(project)
            val repository = repositoryManager.repositories.firstOrNull() ?: return
            
            val handler = GitLineHandler(project, repository.root, GitCommand.COMMIT)
            handler.addParameters("-m", message)
            
            val result = Git.getInstance().runCommand(handler)
            
            if (result.success()) {
                Messages.showInfoMessage(
                    project,
                    "Commit created successfully!\n\nMessage:\n$message",
                    "Commit Success"
                )
            } else {
                Messages.showErrorDialog(
                    project,
                    "Failed to create commit:\n${result.errorOutputAsJoinedString}",
                    "Commit Error"
                )
            }
        } catch (e: Exception) {
            Messages.showErrorDialog(
                project,
                "Failed to create commit: ${e.message}",
                "Commit Error"
            )
        }
    }
}

/**
 * Dialog for collecting conventional commit information.
 */
class ConventionalCommitDialog(project: Project) : DialogWrapper(project) {
    
    private val typeComboBox = JComboBox(arrayOf(
        "feat", "fix", "docs", "style", "refactor", 
        "perf", "test", "build", "ci", "chore"
    ))
    
    private val scopeField = JTextField(20)
    private val descriptionField = JTextField(40)
    private val bodyArea = JTextArea(5, 40)
    private val breakingChangeCheckbox = JCheckBox("Breaking Change")
    
    init {
        title = "Create Conventional Commit"
        init()
    }
    
    override fun createCenterPanel(): JComponent {
        val panel = JPanel(BorderLayout(10, 10))
        panel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        
        val formPanel = JPanel()
        formPanel.layout = BoxLayout(formPanel, BoxLayout.Y_AXIS)
        
        // Type
        formPanel.add(createFieldRow("Type*:", typeComboBox))
        formPanel.add(Box.createVerticalStrut(10))
        
        // Scope
        scopeField.toolTipText = "Optional scope (e.g., api, ui, auth)"
        formPanel.add(createFieldRow("Scope:", scopeField))
        formPanel.add(Box.createVerticalStrut(10))
        
        // Description
        descriptionField.toolTipText = "Short description (imperative mood, lowercase)"
        formPanel.add(createFieldRow("Description*:", descriptionField))
        formPanel.add(Box.createVerticalStrut(10))
        
        // Breaking change
        formPanel.add(breakingChangeCheckbox)
        formPanel.add(Box.createVerticalStrut(10))
        
        // Body
        val bodyLabel = JLabel("Body (optional):")
        formPanel.add(bodyLabel)
        formPanel.add(Box.createVerticalStrut(5))
        
        bodyArea.lineWrap = true
        bodyArea.wrapStyleWord = true
        bodyArea.toolTipText = "Longer description explaining the motivation for the change"
        val scrollPane = JScrollPane(bodyArea)
        formPanel.add(scrollPane)
        
        panel.add(formPanel, BorderLayout.CENTER)
        
        // Add help text
        val helpText = JLabel("<html><i>Format: type(scope): description</i></html>")
        panel.add(helpText, BorderLayout.SOUTH)
        
        return panel
    }
    
    private fun createFieldRow(label: String, component: JComponent): JPanel {
        val panel = JPanel(BorderLayout(5, 0))
        panel.add(JLabel(label), BorderLayout.WEST)
        panel.add(component, BorderLayout.CENTER)
        return panel
    }
    
    override fun doValidate(): ValidationInfo? {
        if (descriptionField.text.trim().isEmpty()) {
            return ValidationInfo("Description is required", descriptionField)
        }
        
        val description = descriptionField.text.trim()
        if (description.length > 72) {
            return ValidationInfo("Description should be 72 characters or less", descriptionField)
        }
        
        if (description.first().isUpperCase()) {
            return ValidationInfo("Description should start with lowercase", descriptionField)
        }
        
        return null
    }
    
    fun getCommitMessage(): String {
        val type = typeComboBox.selectedItem as String
        val scope = scopeField.text.trim()
        val description = descriptionField.text.trim()
        val body = bodyArea.text.trim()
        val breaking = breakingChangeCheckbox.isSelected
        
        val builder = StringBuilder()
        
        // Format: type(scope): description
        builder.append(type)
        if (scope.isNotEmpty()) {
            builder.append("($scope)")
        }
        if (breaking) {
            builder.append("!")
        }
        builder.append(": ")
        builder.append(description)
        
        // Add body if present
        if (body.isNotEmpty()) {
            builder.append("\n\n")
            builder.append(body)
        }
        
        // Add breaking change footer if checked
        if (breaking) {
            builder.append("\n\nBREAKING CHANGE: ")
            if (body.isEmpty()) {
                builder.append(description)
            }
        }
        
        return builder.toString()
    }
}
