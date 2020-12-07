package com.homecredit.weather.ui.weatherforecast.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherForecast(

    val currentTemperature: Double = 0.0,

    val backgroundHexColor: String = HEX_COLOR_COLD,

    var favorite: Boolean = false,

    val locationId: Int = 0,

    val locationName: String = "",

    val maxTemperature: Double = 0.0,

    val minTemperature: Double = 0.0,

    val weatherStatus: String = ""
): Parcelable {

    companion object {
        const val HEX_COLOR_COLD = "#26C6DA"

        const val HEX_COLOR_FREEZING = "#1976D2"

        const val HEX_COLOR_HOT = "#FF7043"

        const val HEX_COLOR_WARM = "#66BB6A"
    }
}