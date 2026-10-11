import com.android.build.api.dsl.ApplicationExtension
import com.bikcodeh.weatherapp.buildlogic.configureAndroidCommon
import com.bikcodeh.weatherapp.buildlogic.libs
import com.bikcodeh.weatherapp.buildlogic.versionInt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/** Base setup for the `:app` module. */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")

            extensions.configure<ApplicationExtension> {
                configureAndroidCommon(this)
                defaultConfig.targetSdk = libs.versionInt("targetSdk")
                testOptions.animationsDisabled = true
            }
        }
    }
}
