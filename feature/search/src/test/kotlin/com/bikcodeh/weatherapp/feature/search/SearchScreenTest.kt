package com.bikcodeh.weatherapp.feature.search

import android.content.Context
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bikcodeh.weatherapp.core.designsystem.R as DesignSystemR
import com.bikcodeh.weatherapp.core.designsystem.theme.WeatherTheme
import com.bikcodeh.weatherapp.core.ui.R as CoreUiR
import com.bikcodeh.weatherapp.feature.search.model.LocationUiModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/** UI tests of the stateless search screen, running on the JVM with Robolectric. */
@RunWith(AndroidJUnit4::class)
@Config(qualifiers = "en-rUS")
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun results_areDisplayed_andTheClickedLocationIsReported() {
        var clickedLocationId: Long? = null
        setContent(
            uiState = SearchUiState.Success(
                listOf(
                    LocationUiModel(id = 7, name = "Bogota", subtitle = "Cundinamarca, Colombia"),
                ),
            ),
            onLocationClick = { clickedLocationId = it },
        )

        composeTestRule.onNodeWithText("Cundinamarca, Colombia").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bogota").performClick()

        assertThat(clickedLocationId).isEqualTo(7)
    }

    @Test
    fun emptyResults_showTheNoResultsMessage() {
        setContent(uiState = SearchUiState.Empty)

        val noResults = context.getString(R.string.feature_search_no_results)
        composeTestRule.onNodeWithText(noResults).assertIsDisplayed()
    }

    @Test
    fun error_showsTheMessage_andRetries() {
        var retried = false
        setContent(
            uiState = SearchUiState.Error(CoreUiR.string.core_ui_error_network),
            onRetryClick = { retried = true },
        )

        val message = context.getString(CoreUiR.string.core_ui_error_network)
        val retry = context.getString(DesignSystemR.string.core_designsystem_retry)
        composeTestRule.onNodeWithText(message).assertIsDisplayed()
        composeTestRule.onNodeWithText(retry).performClick()

        assertThat(retried).isTrue()
    }

    @Test
    fun clearButton_clearsTheQuery() {
        val queryState = TextFieldState("Bogota")
        setContent(uiState = SearchUiState.Loading, queryState = queryState)

        val clear = context.getString(R.string.feature_search_clear)
        composeTestRule.onNodeWithContentDescription(clear).performClick()

        assertThat(queryState.text.toString()).isEmpty()
    }

    private fun setContent(
        uiState: SearchUiState,
        queryState: TextFieldState = TextFieldState("Bog"),
        onLocationClick: (Long) -> Unit = {},
        onRetryClick: () -> Unit = {},
    ) {
        composeTestRule.setContent {
            WeatherTheme {
                SearchContent(
                    queryState = queryState,
                    uiState = uiState,
                    onLocationClick = onLocationClick,
                    onRetryClick = onRetryClick,
                )
            }
        }
    }
}
