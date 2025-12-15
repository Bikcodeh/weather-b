package com.bikcodeh.weatherapp.ui.search


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikcodeh.weatherapp.R
import com.bikcodeh.weatherapp.ui.components.LottieAnimation
import com.bikcodeh.weatherapp.ui.components.Loading
import com.bikcodeh.weatherapp.ui.search.components.LocationCard
import com.bikcodeh.weatherapp.ui.search.components.NotFound
import com.bikcodeh.weatherapp.ui.search.viewmodel.SearchState

@Composable
fun SearchContent(
    state: SearchState,
    onQueryChange: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            OutlinedTextField(
                value = state.query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = {
                    Text(
                        stringResource(R.string.search_city),
                        color = Color.White.copy(alpha = 0.6f)
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                },
                trailingIcon = {
                    if (state.query.isNotEmpty()) {
                        IconButton(
                            onClick = { onQueryChange("") }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.clear_text),
                                tint = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.White.copy(alpha = 0.9f),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
            if (state.query.isEmpty() || state.query.isNotEmpty() && state.isError) {
                LottieAnimation(lottieFile = R.raw.plane)
            }
        }
        when {
            state.isLoading -> {
                Loading()
            }


            state.locations.isEmpty() && state.query.isNotEmpty() && !state.isError -> {
                Column(
                    modifier = Modifier.fillMaxSize(),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.city_not_found),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    NotFound(modifier = Modifier.size(250.dp))
                }
            }

            else -> {

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.locations,
                        key = { it.id }
                    ) { location ->
                        LocationCard(
                            city = location.name,
                            country = location.country,
                            onClick = { onItemClick(location.name) }
                        )
                    }
                }
            }
        }

    }
}


@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SearchContentPreview() {
    SearchContent(
        state = SearchState(
            query = "hola",
            locations = emptyList()
        ),
        onQueryChange = {},
        onItemClick = {}
    )
}