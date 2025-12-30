package com.sim.whether.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


sealed class Response {
    data class Success(val data: WeatherResponse) : Response()
    data class Error(val message: String) : Response()
}


@Serializable
data class WeatherResponse(
    val current: Current,
)

@Serializable
data class Current(
    @SerialName("last_updated") val lastUpdated: String,
    @SerialName("last_updated_epoch") val lastUpdatedEpoch: Int,
    @SerialName("temp_c") val tempC: Double,
    @SerialName("temp_f") val tempF: Double,
    @SerialName("feelslike_c") val feelsLikeC: Double,
    @SerialName("feelslike_f") val feelsLikeF: Double,
    @SerialName("windchill_c") val windChillC: Double,
    @SerialName("windchill_f") val windChillF: Double,
    @SerialName("heatindex_c") val heatIndexC: Double,
    @SerialName("heatindex_f") val heatIndexF: Double,
    @SerialName("dewpoint_c") val dewPointC: Double,
    @SerialName("dewpoint_f") val dewPointF: Double,
    @SerialName("wind_mph") val windMph: Double,
    @SerialName("wind_kph") val windKph: Double,
    @SerialName("wind_degree") val windDegree: Int,
    @SerialName("wind_dir") val windDir: String,
)