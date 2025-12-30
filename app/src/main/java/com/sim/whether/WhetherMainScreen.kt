package com.sim.whether

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sim.whether.network.Response

@Composable
fun WeatherMainScreen(
    onNavigateToSubScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Text(
        text = when(uiState) {
            is Response.Success -> (uiState as Response.Success).data.toString()
            is Response.Error -> (uiState as Response.Error).toString()
        },
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .clickable { onNavigateToSubScreen() }
    )
}