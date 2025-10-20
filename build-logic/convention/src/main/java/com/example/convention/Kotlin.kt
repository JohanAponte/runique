package com.example.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    /**
     * Configure Kotlin and Android options for a Gradle project.
     *
     * @receiver Project The current Gradle project.
     * @param commonExtension A CommonExtension instance used to configure Android common options
     * such as compileSdk, defaultConfig, and compileOptions.
     */
    commonExtension.apply {
        // Set compile SDK version from the version catalog property "projectCompileSdkVersion".
        compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()

        // Set minimum SDK version from the version catalog property "projectMinSdkVersion".
        defaultConfig.minSdk = libs.findVersion("projectMinSdkVersion").get().toString().toInt()

        // Enable core library desugaring and set Java source/target compatibility to Java 11.
        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    // Configure Kotlin-specific compiler options (see configureKotlin()).
    configureKotlin()

    // Add the desugaring library dependency required when core library desugaring is enabled.
    dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("desugar.jdk.libs").get())
    }
}

/**
 * Configure Kotlin compiler options for the project.
 *
 * @receiver Project The current Gradle project.
 *
 * This ensures all Kotlin compilation tasks target JVM 11 using the Kotlin Gradle DSL's
 * compilerOptions API.
 */
private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        /*kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }*/
        // Set Kotlin JVM target to Java 11 using the Kotlin Gradle Domain Specific Language API.
        compilerOptions.jvmTarget.set(JvmTarget.JVM_11)
    }
}