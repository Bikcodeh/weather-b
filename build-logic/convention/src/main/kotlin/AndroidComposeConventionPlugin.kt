import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.bikcodeh.weatherapp.buildlogic.library
import com.bikcodeh.weatherapp.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

/**
 * Enables Jetpack Compose on top of `weather.android.application` or `weather.android.library`.
 *
 * Compose compiler metrics/reports can be generated with `-PcomposeCompilerReports=true`
 * (output in `build/compose-compiler`), useful to audit stability and skippability.
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            pluginManager.withPlugin("com.android.application") {
                extensions.configure<ApplicationExtension> { buildFeatures.compose = true }
            }
            pluginManager.withPlugin("com.android.library") {
                extensions.configure<LibraryExtension> { buildFeatures.compose = true }
            }

            val reportsProperty = providers.gradleProperty("composeCompilerReports")
            if (reportsProperty.orNull.toBoolean()) {
                extensions.configure<ComposeCompilerGradlePluginExtension> {
                    val outputDir = layout.buildDirectory.dir("compose-compiler")
                    reportsDestination.set(outputDir)
                    metricsDestination.set(outputDir)
                }
            }

            dependencies {
                val bom = platform(libs.library("androidx-compose-bom"))
                "implementation"(bom)
                "testImplementation"(bom)
                "implementation"(libs.library("androidx-compose-ui-tooling-preview"))
                "debugImplementation"(libs.library("androidx-compose-ui-tooling"))
            }
        }
    }
}
