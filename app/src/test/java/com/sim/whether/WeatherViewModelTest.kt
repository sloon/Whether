package com.sim.whether

import com.sim.whether.network.Current
import com.sim.whether.network.Response
import com.sim.whether.network.WeatherRepository
import com.sim.whether.network.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val weatherRepository = mock(WeatherRepository::class.java)
    private lateinit var viewModel: WeatherViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState is initially loading`() = runTest {
        // Mock the repository to return an empty flow or a flow that doesn't emit immediately
        // so that the initial state is preserved for the assertion.
        // However, the ViewModel's init block runs immediately upon construction.
        // If getWeather returns null (mock default), it will crash in init.
        `when`(weatherRepository.getWeather(33.44, -94.04)).thenReturn(emptyFlow())

        viewModel = WeatherViewModel(weatherRepository)
        assertTrue(viewModel.uiState.value is Response.Error)
        assertTrue((viewModel.uiState.value as Response.Error).message == "Loading...")
    }

    @Test
    fun `uiState updates to success when repository returns success`() = runTest {
        val current = Current(
            lastUpdated = "2023-10-27 10:00",
            lastUpdatedEpoch = 1698393600,
            tempC = 20.0,
            tempF = 68.0,
            feelsLikeC = 19.0,
            feelsLikeF = 66.2,
            windChillC = 18.0,
            windChillF = 64.4,
            heatIndexC = 21.0,
            heatIndexF = 69.8,
            dewPointC = 12.0,
            dewPointF = 53.6,
            windMph = 10.0,
            windKph = 16.1,
            windDegree = 180,
            windDir = "S"
        )
        val mockResponse = WeatherResponse(current)
        `when`(weatherRepository.getWeather(33.44, -94.04)).thenReturn(flowOf(Response.Success(mockResponse)))
        
        viewModel = WeatherViewModel(weatherRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is Response.Success)
        assertTrue((viewModel.uiState.value as Response.Success).data == mockResponse)
    }

    @Test
    fun `uiState updates to error when repository returns error`() = runTest {
        `when`(weatherRepository.getWeather(33.44, -94.04)).thenReturn(flowOf(Response.Error("Error")))
        
        viewModel = WeatherViewModel(weatherRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is Response.Error)
        assertTrue((viewModel.uiState.value as Response.Error).message == "Error")
    }
}