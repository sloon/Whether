package com.sim.whether.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object WeatherModule {

    private val json = Json {
        // Customize the Json configuration as needed
        ignoreUnknownKeys = true // Ignore JSON fields not present in your data class
        coerceInputValues = true // Use default values for missing fields
    }

    @Provides
    fun provideWeatherService(
        // Potential dependencies of this type
    ): OpenWeatherMapService {
        return Retrofit.Builder()
            .baseUrl(Constants.OPEN_WEATHER_MAP_BASE_URL)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=utf-8".toMediaType()
                )
            )
            .build()
            .create(OpenWeatherMapService::class.java)
    }
}