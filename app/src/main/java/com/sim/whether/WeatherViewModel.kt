package com.sim.whether

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sim.whether.network.Response
import com.sim.whether.network.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState: MutableStateFlow<Response> =
        MutableStateFlow(Response.Error("Loading..."))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<Response> = _uiState

    init {
        viewModelScope.launch {
            weatherRepository.getWeather(33.44, -94.04)
                // Update View with the latest favorite news
                // Writes to the value property of MutableStateFlow,
                // adding a new element to the flow and updating all
                // of its collectors
                .collect { favoriteNews ->
                    _uiState.value = favoriteNews
                }
        }
    }

}