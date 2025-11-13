import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.DynamicFeatureExtension
import com.example.convention.ExtensionType
import com.example.convention.addUiLayerDependencies
import com.example.convention.configureAndroidCompose
import com.example.convention.configureBuildTypes
import com.example.convention.configureKotlinAndroid
import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

/**
 * Android application convention plugin that applies standard Android and Kotlin plugins
 * and configures common application options using the project's version catalog.
 *
 * This plugin sets `applicationId`, `targetSdk`, `versionCode`, and `versionName` from
 * the `libs` version catalog and delegates further Android/Kotlin configuration to
 * `configureKotlinAndroid`.
 */
class AndroidDynamicFeatureConventionPlugin : Plugin<Project> {
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
                apply("com.android.dynamic-feature")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.android")
            }

            // Configure the Android ApplicationExtension using the version catalog values.
            extensions.configure<DynamicFeatureExtension> {

                // Configure Kotlin and Android compilation/options (delegated function).
                configureKotlinAndroid(this)
                configureAndroidCompose(this)
                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.DYNAMIC_FEATURE
                )
            }

            dependencies {
                addUiLayerDependencies(target)
                "testImplementation"(kotlin("test"))
            }
        }
    }
}