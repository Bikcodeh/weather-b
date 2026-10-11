package com.bikcodeh.weatherapp.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikcodeh.weatherapp.core.designsystem.R
import com.bikcodeh.weatherapp.core.designsystem.theme.WeatherTheme

/** Error placeholder with an illustration, a [message] and a retry button. */
@Composable
fun ErrorState(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
    ) {
        LoopingLottieAnimation(
            animationRes = R.raw.core_designsystem_error,
            modifier = Modifier.size(180.dp),
        )
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        Button(onClick = onRetryClick) {
            Text(text = stringResource(R.string.core_designsystem_retry))
        }
    }
}

@Preview
@Composable
private fun ErrorStatePreview() {
    WeatherTheme {
        GradientBackground {
            ErrorState(message = "Oops! No internet connection.", onRetryClick = {})
        }
    }
}
