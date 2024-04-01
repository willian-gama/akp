plugins {
    `kotlin-dsl`
    id("jacoco") // Jacoco plugin: https://docs.gradle.org/current/userguide/jacoco_plugin.html#sec:jacoco_getting_started
}

dependencies {
    implementation("com.android.tools.build:gradle:8.3.1")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.1.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.6")
    //implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:4.4.1.3373")
}

gradlePlugin {
    plugins {
        create("code_linting_plugin") {
            id = "com.gradle.plugin.code_linting_plugin"
            implementationClass = "com.android.dev.engineer.kotlin.compose.plugin.CodeLintingPlugin"
        }
    }
}