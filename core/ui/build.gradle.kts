plugins {
    alias(libs.plugins.weather.android.library)
    alias(libs.plugins.weather.android.compose)
}

android {
    namespace = "com.bikcodeh.weatherapp.core.ui"
    resourcePrefix = "core_ui_"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.androidx.compose.runtime)
}
