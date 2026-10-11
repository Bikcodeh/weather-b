plugins {
    alias(libs.plugins.weather.android.library)
    alias(libs.plugins.weather.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.bikcodeh.weatherapp.core.data"
}

dependencies {
    implementation(projects.core.domain)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.timber)

    testImplementation(projects.core.testing)
    testImplementation(libs.okhttp.mockwebserver)
}
