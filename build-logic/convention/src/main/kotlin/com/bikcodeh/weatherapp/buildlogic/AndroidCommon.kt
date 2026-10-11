package com.bikcodeh.weatherapp.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

/**
 * Configuration shared by every Android module (application and libraries).
 *
 * Kotlin is compiled by AGP's built-in Kotlin support, whose `jvmTarget` follows
 * `compileOptions.targetCompatibility`, so no extra Kotlin configuration is needed here.
 */
internal fun Project.configureAndroidCommon(extension: CommonExtension) {
    extension.apply {
        compileSdk = libs.versionInt("compileSdk")
        defaultConfig.minSdk = libs.versionInt("minSdk")

        compileOptions.sourceCompatibility = JavaVersion.VERSION_17
        compileOptions.targetCompatibility = JavaVersion.VERSION_17

        // Lets Robolectric-based tests (e.g. Compose UI tests) resolve Android resources.
        testOptions.unitTests.isIncludeAndroidResources = true

        lint.warningsAsErrors = true
        lint.disable += VersionFreshnessLintChecks
    }
}

/**
 * Dependency freshness is tracked by Dependabot. Keeping these checks would make the lint result
 * of the same commit change whenever a new version of a library or tool is published.
 */
private val VersionFreshnessLintChecks = setOf(
    "AndroidGradlePluginVersion",
    "GradleDependency",
    "NewerVersionAvailable",
)
