package com.sim.whether.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherMapService: OpenWeatherMapService
) {
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