package com.bikcodeh.weatherapp.core.designsystem.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bikcodeh.weatherapp.core.designsystem.R
import com.bikcodeh.weatherapp.core.designsystem.icon.WeatherIcons
import com.bikcodeh.weatherapp.core.designsystem.theme.WeatherTheme

/**
 * Transparent, center aligned top app bar meant to be placed over [GradientBackground].
 *
 * @param onBackClick When not null, a back navigation icon is shown and invokes it.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(WeatherIcons.ArrowBack),
                        contentDescription = stringResource(R.string.core_designsystem_back),
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
        ),
    )
}

@Preview
@Composable
private fun WeatherTopAppBarPreview() {
    WeatherTheme {
        GradientBackground {
            WeatherTopAppBar(title = "Detail", onBackClick = {})
        }
    }
}
