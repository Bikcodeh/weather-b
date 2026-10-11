import java.io.StringReader
import java.util.Properties

plugins {
    alias(libs.plugins.weather.android.application)
    alias(libs.plugins.weather.android.compose)
    alias(libs.plugins.weather.hilt)
}

/** Reads a `.properties` file of the root project, tracked as a configuration cache input. */
fun loadProperties(fileName: String): Properties? {
    val file = rootProject.layout.projectDirectory.file(fileName)
    val content = providers.fileContents(file).asText.orNull ?: return null
    return Properties().apply { load(StringReader(content)) }
}

val localProperties = loadProperties("local.properties")
val keystoreProperties = loadProperties("keystore.properties")

/** WeatherAPI key from `local.properties` or, on CI, from the `WEATHER_API_KEY` environment variable. */
val weatherApiKey: String = (
    localProperties?.getProperty("WEATHER_API_KEY")
        ?: providers.environmentVariable("WEATHER_API_KEY").orNull
        ?: ""
    ).trim().removeSurrounding("\"")

android {
    namespace = "com.bikcodeh.weatherapp"

    defaultConfig {
        applicationId = "com.bikcodeh.weatherapp"
        versionCode = 1
        versionName = "1.0.0"

        buildConfigField("String", "WEATHER_API_KEY", "\"$weatherApiKey\"")
    }

    signingConfigs {
        if (keystoreProperties != null) {
            create("release") {
                storeFile = file(keystoreProperties.getProperty("storeFile"))
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.findByName("release")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.feature.detail)
    implementation(projects.feature.search)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.timber)
}
