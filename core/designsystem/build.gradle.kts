plugins {
    alias(libs.plugins.weather.android.library)
    alias(libs.plugins.weather.android.compose)
}

android {
    namespace = "com.bikcodeh.weatherapp.core.designsystem"
    resourcePrefix = "core_designsystem_"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.lottie.compose)
}
