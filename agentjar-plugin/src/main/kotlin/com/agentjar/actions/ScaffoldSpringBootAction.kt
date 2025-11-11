package com.agentjar.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import java.io.File

class ScaffoldSpringBootAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        scaffoldSpringBoot(project)
    }
    
    private fun scaffoldSpringBoot(project: Project) {
        val basePath = project.basePath ?: return
        
        WriteAction.runAndWait<RuntimeException> {
            // Create application.properties
            val resourcesDir = File(basePath, "src/main/resources")
            resourcesDir.mkdirs()
            val propsFile = File(resourcesDir, "application.properties")
            propsFile.writeText("server.port=8080\n")
            
            // Create DemoApplication.java
            val javaDir = File(basePath, "src/main/java/com/agentjar/demo")
            javaDir.mkdirs()
            val appFile = File(javaDir, "DemoApplication.java")
            appFile.writeText("""
                package com.agentjar.demo;
                
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
            VfsUtil.markDirtyAndRefresh(false, true, true, 
                project.baseDir ?: return@runAndWait)
        }
    }
}
