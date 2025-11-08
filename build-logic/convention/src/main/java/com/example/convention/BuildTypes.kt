package com.example.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Configures the build types (debug and release) for a Gradle project based on the provided extension type.
 *
 * @receiver Project The current Gradle project.
 * @param commonExtension A `CommonExtension` instance used to configure Android build options.
 * @param extensionType The type of extension (APPLICATION or LIBRARY) to configure.
 *
 * This function retrieves the `API_URL` and ``BASE_URL from the local Gradle properties and configures the
 * `debug` and `release` build types accordingly. It applies different configurations based
 * on whether the project is an application or a library.
 */
internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }
        // Retrieve the API key from the local Gradle properties.
        val apiKey = gradleLocalProperties(rootDir, providers).getProperty("API_KEY")
        val baseUrl = gradleLocalProperties(rootDir, providers).getProperty("BASE_URL")
        val mapsApiKey = gradleLocalProperties(rootDir, providers).getProperty("MAPS_API_KEY")
        when (extensionType) {
            ExtensionType.APPLICATION -> {
                // Configure build types for an Android application.
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            // Configure the debug build type with the API key.
                            configureDebugBuildType(apiKey, baseUrl, mapsApiKey)
                        }

                        release {
                            // Configure the release build type with the API key.
                            configureReleaseBuildType(commonExtension, apiKey, baseUrl, mapsApiKey)
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                // Configure build types for an Android library.
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            // Configure the debug build type with the API key.
                            configureDebugBuildType(apiKey, baseUrl, mapsApiKey)
                        }

                        release {
                            // Configure the release build type with the API key.
                            configureReleaseBuildType(commonExtension, apiKey, baseUrl, mapsApiKey)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Configures the debug build type with specific build configuration fields.
 *
 * @receiver BuildType The debug build type to configure.
 * @param apiKey The API key to be used in the debug build.
 *
 * This function sets the `API_URL` and `BASE_URL` fields for the debug build type.
 */
private fun BuildType.configureDebugBuildType(apiKey: String, baseUrl: String, mapsApiKey: String) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
    manifestPlaceholders["MAPS_API_KEY"] = "\"${mapsApiKey}\""
}

/**
 * Configures the release build type with specific build configuration fields and ProGuard settings.
 *
 * @receiver BuildType The release build type to configure.
 * @param commonExtension A `CommonExtension` instance used to retrieve default ProGuard files.
 * @param apiKey The API key to be used in the release build.
 *
 * This function sets the `API_URL` and `BASE_URL` fields for the release build type, disables
 * code minification, and applies ProGuard configuration files.
 */
private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String,
    baseUrl: String,
    mapsApiKey: String
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
    manifestPlaceholders["MAPS_API_KEY"] = "\"${mapsApiKey}\""

    isMinifyEnabled = false
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}