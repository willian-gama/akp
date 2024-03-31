plugins {
//    id("org.jlleitschuh.gradle.ktlint") version "12.1.0" apply true
    `kotlin-dsl`
//    id("com.android.application") version "8.3.1" apply false
//    id("com.android.library") version "8.3.1" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.3.1")
        //classpath(kotlin("gradle-plugin", version = "1.4.32"))
    }
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("my-convention-plugin") {
            id = "com.gradle.plugin.my-convention-plugin"
            implementationClass = "MyConventionPlugin"

//            println(pluginManager.hasPlugin("org.jlleitschuh.gradle.ktlint"))
//            plugins {
//
//                id("org.jlleitschuh.gradle.ktlint") version "12.1.0" apply true
//            }
        }
    }
}