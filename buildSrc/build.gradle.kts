plugins {
//    `java-gradle-plugin`
    `kotlin-dsl`
    //    alias libs.plugins.android.application apply false
//    alias libs.plugins.android.library apply false
}

repositories {
    google()
    mavenCentral()
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:8.3.1")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.1.0")
}

gradlePlugin {
    plugins {
        create("my-convention-plugin") {
            id = "com.gradle.plugin.my-convention-plugin"
            implementationClass = "CodeLintingPlugin"
        }
    }
}