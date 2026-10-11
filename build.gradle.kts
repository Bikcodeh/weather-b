plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.spotless)
}

spotless {
    val ktlintVersion = libs.versions.ktlint.get()
    // One parameter per line in constructors and in functions with 3+ parameters (composables).
    val signatureRuleOption = "_rule_force_multiline_when_parameter_count_greater_or_equal_than"
    val ktlintOverrides = mapOf(
        "ktlint_class_signature$signatureRuleOption" to 1,
        "ktlint_function_signature$signatureRuleOption" to 3,
    )
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**")
        ktlint(ktlintVersion)
            .editorConfigOverride(ktlintOverrides)
            .customRuleSets(
                listOf("io.nlopez.compose.rules:ktlint:${libs.versions.composeRules.get()}"),
            )
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude("**/build/**")
        ktlint(ktlintVersion).editorConfigOverride(ktlintOverrides)
    }
}
