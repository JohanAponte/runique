import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.configureKotlinAndroid
import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
     * Android application convention plugin that applies standard Android and Kotlin plugins
     * and configures common application options using the project's version catalog.
     *
     * This plugin sets `applicationId`, `targetSdk`, `versionCode`, and `versionName` from
     * the `libs` version catalog and delegates further Android/Kotlin configuration to
     * `configureKotlinAndroid`.
     */
    class AndroidApplicationConventionPlugin: Plugin<Project> {
        /**
         * Applies this plugin to the given Gradle project.
         *
         * @param target The Gradle project to configure.
         *
         * Behavior:
         * - Applies the `com.android.application` and `org.jetbrains.kotlin.android` plugins.
         * - Configures the [ApplicationExtension] defaultConfig properties using values
         *   retrieved from the version catalog (`libs`).
         * - Delegates additional Kotlin/Android configuration to [configureKotlinAndroid].
         */
        override fun apply(target: Project) {
            target.run {
                // Apply Android application and Kotlin Android plugins to the project.
                pluginManager.run {
                    apply("com.android.application")
                    apply("org.jetbrains.kotlin.android")
                }

                // Configure the Android ApplicationExtension using the version catalog values.
                extensions.configure<ApplicationExtension> {
                    defaultConfig{
                        // Set the application id from the version catalog entry `projectApplicationId`.
                        applicationId = libs.findVersion("projectApplicationId").get().toString()

                        // Set the target SDK version from the version catalog entry `projectTargetSdkVersion`.
                        targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()

                        // Set the version code and version name from the version catalog.
                        versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                        versionName = libs.findVersion("projectVersionName").get().toString()
                    }

                    // Configure Kotlin and Android compilation/options (delegated function).
                    configureKotlinAndroid(this)
                }
            }
        }
    }