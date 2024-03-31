import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginManager

class MyConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        applyPlugins(pluginManager = project.pluginManager)
        println("Using custom plugin")
        println("KTLINT" + project.pluginManager.hasPlugin("org.jlleitschuh.gradle.ktlint"))
//
//        project.extensions.getByType(KtlintExtension::class.java).apply {
//            ktlint {
//
//            }
//        }
//
//        project.subprojects {
//            ktlint {
//
//            }
//        }

//        project.tasks.withType(GenerateReportsTask).configureEach {
//            reportsOutputDirectory.set(project.layout.buildDirectory.dir("reports/ktlint/$name"))
//        }
    }

    private fun applyPlugins(pluginManager: PluginManager) {
        pluginManager.apply {
            apply("org.jlleitschuh.gradle.ktlint")
//            apply("android-library")
//            apply("kotlin-android")
        }
    }
}