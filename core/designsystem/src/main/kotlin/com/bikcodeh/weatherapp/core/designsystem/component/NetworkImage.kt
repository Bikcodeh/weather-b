package com.bikcodeh.weatherapp.core.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

/**
 * Image loaded from [url]. Wraps the image loading library so features do not depend on it.
 *
 * @param contentDescription Accessibility text; `null` for decorative images.
 */
@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}
