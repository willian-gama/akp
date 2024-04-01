package com.android.dev.engineer.kotlin.compose.plugin

import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainService
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask

class CodeLintingPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.subprojects {
            setUpKtLint()
            setUpDetekt()
            setUpUnitTest()
        }
    }

    private fun Project.setUpUnitTest() {
        pluginManager.apply("jacoco")

        configure<JacocoPluginExtension> {
            toolVersion = "0.8.10"
        }

        tasks.withType<Test>().configureEach {
            configure<JacocoTaskExtension> {
                isIncludeNoLocationClasses = true // Robolectric support
                excludes = listOf(
                    "jdk.internal.*",
                    "coil.compose.*"
                )
            }

            testLogging {
                exceptionFormat = TestExceptionFormat.FULL // Display the full log to identify Paparazzi test failures
                showStackTraces = false
            }

            // Paparazzi screenshots Java compatibility: https://docs.gradle.org/current/userguide/toolchains.html#sec:plugins_toolchains
            javaLauncher.set(
                // https://blog.jetbrains.com/kotlin/2021/11/gradle-jvm-toolchain-support-in-the-kotlin-plugin
                extensions.getByType<JavaToolchainService>().launcherFor {
                    languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.toString()))
                }
            )
        }
    }

    private fun Project.setUpDetekt() {
        pluginManager.apply("io.gitlab.arturbosch.detekt")

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
        pluginManager.apply("org.jlleitschuh.gradle.ktlint")

        // https://github.com/JLLeitschuh/ktlint-gradle?tab=readme-ov-file#configuration
        configure<KtlintExtension> {
            version.set("1.2.1")
            android.set(true)
            verbose.set(true)

            reporters {
                reporter(ReporterType.HTML)
                reporter(ReporterType.JSON) // it's required for Sonar
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