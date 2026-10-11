package com.bikcodeh.weatherapp.feature.detail

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bikcodeh.weatherapp.core.designsystem.R as DesignSystemR
import com.bikcodeh.weatherapp.core.designsystem.theme.WeatherTheme
import com.bikcodeh.weatherapp.core.testing.data.TestData
import com.bikcodeh.weatherapp.core.ui.R as CoreUiR
import com.bikcodeh.weatherapp.feature.detail.model.toUiModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/** UI tests of the stateless detail screen, running on the JVM with Robolectric. */
@RunWith(AndroidJUnit4::class)
@Config(qualifiers = "en-rUS")
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun success_showsTheFormattedWeather() {
        setContent(DetailUiState.Success(TestData.bogotaForecast.toUiModel()))

        composeTestRule.onNodeWithText("Bogota").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tuesday, December 16").assertIsDisplayed()
        composeTestRule.onNodeWithText("19°").assertIsDisplayed()
        composeTestRule.onNodeWithText("Feels like 17°").assertIsDisplayed()
        composeTestRule.onNodeWithText("0.4 mm").assertIsDisplayed()
        composeTestRule.onNodeWithText("63%").assertIsDisplayed()
        composeTestRule.onNodeWithText("11 km/h").assertIsDisplayed()
        composeTestRule.onNodeWithText("WED").assertExists()
        composeTestRule.onNodeWithContentDescription("High 22°, low 10°").assertExists()
    }

    @Test
    fun error_showsTheMessage_andRetries() {
        var retried = false
        val uiState = DetailUiState.Error(CoreUiR.string.core_ui_error_server)
        setContent(uiState, onRetryClick = { retried = true })

        val message = context.getString(CoreUiR.string.core_ui_error_server)
        val retry = context.getString(DesignSystemR.string.core_designsystem_retry)
        composeTestRule.onNodeWithText(message).assertIsDisplayed()
        composeTestRule.onNodeWithText(retry).performClick()

        assertThat(retried).isTrue()
    }

    @Test
    fun backButton_navigatesBack() {
        var navigatedBack = false
        setContent(DetailUiState.Loading, onBackClick = { navigatedBack = true })

        val back = context.getString(DesignSystemR.string.core_designsystem_back)
        composeTestRule.onNodeWithContentDescription(back).performClick()

        assertThat(navigatedBack).isTrue()
    }

    private fun setContent(
        uiState: DetailUiState,
        onBackClick: () -> Unit = {},
        onRetryClick: () -> Unit = {},
    ) {
        composeTestRule.setContent {
            WeatherTheme {
                DetailContent(
                    uiState = uiState,
                    onBackClick = onBackClick,
                    onRetryClick = onRetryClick,
                )
            }
        }
    }
}
