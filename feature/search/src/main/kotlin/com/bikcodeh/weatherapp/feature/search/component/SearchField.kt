package com.bikcodeh.weatherapp.feature.search.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.bikcodeh.weatherapp.core.designsystem.icon.WeatherIcons
import com.bikcodeh.weatherapp.feature.search.R

/** Single-line search input with a clear button, styled for the gradient background. */
@Composable
internal fun SearchField(state: TextFieldState, modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val contentColor = MaterialTheme.colorScheme.onBackground
    val secondaryContentColor = contentColor.copy(alpha = 0.7f)

    OutlinedTextField(
        state = state,
        modifier = modifier,
        placeholder = { Text(text = stringResource(R.string.feature_search_field_placeholder)) },
        leadingIcon = {
            Icon(painter = painterResource(WeatherIcons.Search), contentDescription = null)
        },
        trailingIcon = {
            if (state.text.isNotEmpty()) {
                IconButton(onClick = state::clearText) {
                    Icon(
                        painter = painterResource(WeatherIcons.Close),
                        contentDescription = stringResource(R.string.feature_search_clear),
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Search,
        ),
        onKeyboardAction = { keyboardController?.hide() },
        lineLimits = TextFieldLineLimits.SingleLine,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = contentColor,
            unfocusedTextColor = contentColor,
            cursorColor = contentColor,
            focusedBorderColor = contentColor,
            unfocusedBorderColor = contentColor.copy(alpha = 0.5f),
            focusedLeadingIconColor = secondaryContentColor,
            unfocusedLeadingIconColor = secondaryContentColor,
            focusedTrailingIconColor = secondaryContentColor,
            unfocusedTrailingIconColor = secondaryContentColor,
            focusedPlaceholderColor = secondaryContentColor,
            unfocusedPlaceholderColor = secondaryContentColor,
        ),
    )
}
