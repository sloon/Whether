package com.sim.whether

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sim.whether.network.Response

@Composable
fun WeatherSubScreen(modifier: Modifier = Modifier, viewModel: WeatherViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Text(
        text = when(uiState) {
            is Response.Success -> (uiState as Response.Success).data.toString()
            is Response.Error -> (uiState as Response.Error).toString()
        },
        modifier = modifier
    )
}