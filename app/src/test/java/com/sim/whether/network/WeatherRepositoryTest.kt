package com.sim.whether.network

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class WeatherRepositoryTest {

    private val weatherMapService = mock(OpenWeatherMapService::class.java)
    private val weatherRepository = WeatherRepository(weatherMapService)

    @Test
    fun `getWeather emits success when service returns valid response`() = runTest {
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
        `when`(weatherMapService.forecast()).thenReturn(retrofit2.Response.success(mockResponse))

        val result = weatherRepository.getWeather(0.0, 0.0).first()

        assertTrue(result is Response.Success)
        assertTrue((result as Response.Success).data == mockResponse)
    }

    @Test
    fun `getWeather emits error when service returns error`() = runTest {
        val errorBody = "Error".toResponseBody("text/plain".toMediaType())
        `when`(weatherMapService.forecast()).thenReturn(retrofit2.Response.error(400, errorBody))

        val result = weatherRepository.getWeather(0.0, 0.0).first()

        assertTrue(result is Response.Error)
        assertTrue((result as Response.Error).message == "Response.error()")
    }
}