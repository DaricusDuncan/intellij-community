package com.agentjar.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFileManager
import java.io.File

class ScaffoldSpringBootAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val baseDir = project.basePath ?: return
        
        ApplicationManager.getApplication().invokeLater {
            try {
                // Create directory structure
                val srcMainJava = File(baseDir, "src/main/java/com/example/demo")
                val srcMainResources = File(baseDir, "src/main/resources")
                srcMainJava.mkdirs()
                srcMainResources.mkdirs()
                
                // Create application.properties
                val propsFile = File(srcMainResources, "application.properties")
                propsFile.writeText("spring.application.name=demo\nserver.port=8080\n")
                
                // Create DemoApplication.java
                val appFile = File(srcMainJava, "DemoApplication.java")
                appFile.writeText("""
                    package com.example.demo;
                    
                    import org.springframework.boot.SpringApplication;
                    import org.springframework.boot.autoconfigure.SpringBootApplication;
                    
                    @SpringBootApplication
                    public class DemoApplication {
                        public static void main(String[] args) {
                            SpringApplication.run(DemoApplication.class, args);
                        }
                    }
                """.trimIndent())
                
                // Refresh VFS
                VirtualFileManager.getInstance().refreshWithoutFileWatcher(false)
                
                Messages.showInfoMessage(
                    project,
                    "Spring Boot project scaffolded successfully!",
                    "AgentJar"
                )
            } catch (ex: Exception) {
                Messages.showErrorDialog(
                    project,
                    "Failed to scaffold project: ${ex.message}",
                    "AgentJar Error"
                )
            }
        }
    }
}
