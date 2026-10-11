import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.HostTestBuilder
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.bikcodeh.weatherapp.buildlogic.bundle
import com.bikcodeh.weatherapp.buildlogic.configureAndroidCommon
import com.bikcodeh.weatherapp.buildlogic.libs
import com.bikcodeh.weatherapp.buildlogic.versionInt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/** Base setup for every Android library module (`:core:*`, `:feature:*`). */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            extensions.configure<LibraryExtension> {
                configureAndroidCommon(this)
                testOptions.targetSdk = libs.versionInt("targetSdk")
            }

            // Unit tests run once, against the debug variant: `./gradlew test` stays fast.
            extensions.configure<LibraryAndroidComponentsExtension> {
                beforeVariants(selector().withBuildType("release")) { variant ->
                    variant.hostTests[HostTestBuilder.UNIT_TEST_TYPE]?.enable = false
                }
            }

            dependencies {
                "testImplementation"(libs.bundle("unit-test"))
            }
        }
    }
}
