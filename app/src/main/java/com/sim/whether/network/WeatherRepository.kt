package com.sim.whether.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


class WeatherRepository() {

    private val json = Json {
        // Customize the Json configuration as needed
        ignoreUnknownKeys = true // Ignore JSON fields not present in your data class
        coerceInputValues = true // Use default values for missing fields
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.OPEN_WEATHER_MAP_BASE_URL)
        .addConverterFactory(
            json.asConverterFactory(
                "application/json; charset=utf-8".toMediaType()
            )
        )
        .build()


    private val weatherMapService: OpenWeatherMapService =
        retrofit.create(OpenWeatherMapService::class.java)

    suspend fun getWeather(lat: Double, lon: Double): Flow<Response> {
        val response = weatherMapService.forecast()
        response?.body()?.let {
            return flow {
                emit(Response.Success(it))
            }
        }
        response?.message()?.let {
            return flow {
                emit(Response.Error(it))
            }
        }
        return flow {
            emit(Response.Error("Something went wrong"))
        }
    }

}