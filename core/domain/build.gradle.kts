plugins {
    alias(libs.plugins.weather.jvm.library)
}

dependencies {
    // Only JSR-330 annotations: the domain stays free of Android, Hilt and networking libraries.
    implementation(libs.javax.inject)

    testImplementation(projects.core.testing)
}
