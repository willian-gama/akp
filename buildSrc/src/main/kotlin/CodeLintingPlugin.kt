import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.JavaVersion
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
            setUpDetekt()
        }
    }

    private fun Project.setUpDetekt() {
        pluginManager.apply {
            apply("io.gitlab.arturbosch.detekt")
        }

        tasks.withType<Detekt>().configureEach {
            include("**/*.kt")
            exclude("**/build/**")
            jvmTarget = JavaVersion.VERSION_17.toString()

            parallel = true
            allRules = true
            autoCorrect = true
            buildUponDefaultConfig = true
            setSource(files(projectDir))
            config.setFrom(file("${rootProject.rootDir}/config/detekt/detekt.yml"))

            reports {
                txt.required.set(false)
                sarif.required.set(false)
                md.required.set(false)
                html.required.set(true)
                html.outputLocation.set(file("${project.layout.buildDirectory.get()}/reports/detekt/detekt.html"))
                xml.required.set(true) // It's required for Sonar
                xml.outputLocation.set(file("${project.layout.buildDirectory.get()}/reports/detekt/detekt.xml"))
            }
        }
    }

    private fun Project.setUpKtLint() {
        // https://github.com/JLLeitschuh/ktlint-gradle?tab=readme-ov-file#applying-to-subprojects
        pluginManager.apply {
            apply("org.jlleitschuh.gradle.ktlint")
        }

        // https://github.com/JLLeitschuh/ktlint-gradle?tab=readme-ov-file#configuration
        configure<KtlintExtension> {
            version.set("1.2.1")
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