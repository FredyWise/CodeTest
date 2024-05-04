import org.jetbrains.kotlin.builtins.StandardNames.FqNames.target

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
    id("com.diffplug.spotless") version "6.19.0" apply true
}

subprojects {
    repositories {
        google()
        mavenCentral()
    }
//
//    apply plugin:"com.diffplug.spotless"
//    spotless {
//        kotlin {
//            target ("**/*.kt")
//            targetExclude("$buildDir/**/*.kt")
//            targetExclude("bin/**/*.kt")
//
//            ktlint("0.45.2").userData([android: "true"])
//            licenseHeaderFile rootProject.file("spotless/copyright.kt")
//        }
//    }
}