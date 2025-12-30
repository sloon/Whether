package com.sim.whether.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapService {
    @GET("current.json")
    suspend fun forecast(
        @Query("key") key: String = Constants.OPEN_WEATHER_MAP_API_KEY,
        @Query("q") latLng: String = "33.44,-94.04",
    ): Response<WeatherResponse>?
}
