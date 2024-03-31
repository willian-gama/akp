import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask

class CodeLintingPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.subprojects {
            setUpKtLint()
        }
    }

    private fun Project.setUpKtLint() {
        // https://github.com/JLLeitschuh/ktlint-gradle?tab=readme-ov-file#applying-to-subprojects
        pluginManager.apply {
            apply("org.jlleitschuh.gradle.ktlint")
        }

        // https://github.com/JLLeitschuh/ktlint-gradle?tab=readme-ov-file#configuration
        configure<KtlintExtension> {
            version.set("1.2.0")
            android.set(true)
            verbose.set(true)

            reporters {
                reporter(ReporterType.HTML)
                reporter(ReporterType.JSON)
            }

            filter {
                include("**/*.kt")
                exclude("**/build/**")
            }
        }

        // https://github.com/JLLeitschuh/ktlint-gradle#setting-reports-output-directory
        tasks.withType<GenerateReportsTask> {
            reportsOutputDirectory.set(project.layout.buildDirectory.dir("reports/ktlint/$name"))
        }
    }
}