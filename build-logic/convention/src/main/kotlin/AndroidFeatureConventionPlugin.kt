import com.bikcodeh.weatherapp.buildlogic.library
import com.bikcodeh.weatherapp.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

/**
 * Presentation-layer feature module: Compose + Hilt + type-safe navigation.
 *
 * Features depend only on `:core:domain` (business contracts) and shared UI modules, never on
 * `:core:data` or on other features. The `:app` module wires the navigation between features.
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("weather.android.library")
            pluginManager.apply("weather.android.compose")
            pluginManager.apply("weather.hilt")
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                "implementation"(project(":core:designsystem"))
                "implementation"(project(":core:domain"))
                "implementation"(project(":core:ui"))

                "implementation"(libs.library("androidx-hilt-lifecycle-viewmodel-compose"))
                "implementation"(libs.library("androidx-lifecycle-runtime-compose"))
                "implementation"(libs.library("androidx-lifecycle-viewmodel-compose"))
                "implementation"(libs.library("androidx-navigation-compose"))

                "testImplementation"(project(":core:testing"))
                "testImplementation"(libs.library("androidx-compose-ui-test-junit4"))
                "testImplementation"(libs.library("androidx-test-ext-junit"))
                "testImplementation"(libs.library("robolectric"))
                "debugImplementation"(libs.library("androidx-compose-ui-test-manifest"))
            }
        }
    }
}
