package com.homecredit.weather.ui.weather.repository.mapper

import com.homecredit.weather.WeatherForecastMockDataFactory
import com.homecredit.weather.data.api.weather.WeatherForecastDto
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast.Companion.HEX_COLOR_COLD
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast.Companion.HEX_COLOR_FREEZING
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast.Companion.HEX_COLOR_HOT
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast.Companion.HEX_COLOR_WARM
import com.homecredit.weather.ui.weatherforecast.repository.mapper.DtoToWeatherForecastMapper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DtoToWeatherForecastMapperTest {

    @Test
    fun `background hex color should be equal to cold if current temperature ranges from 1 to 15`() {

        val currentTemperature = (1..15).random().toDouble()

        val expected = WeatherForecastMockDataFactory.weatherForecastDto(currentTemperature)

        val testObserver = TestObserver<WeatherForecast>()

        Observable.fromCallable { expected }
            .compose(DtoToWeatherForecastMapper())
            .subscribe(testObserver)

        testObserver
            .assertComplete()
            .assertNoErrors()

        val actual = testObserver.values()[0]

        assertEqual(actual, expected)

        assertThat(actual.backgroundHexColor).isEqualTo(HEX_COLOR_COLD)
    }

    @Test
    fun `background hex color should be equal to freezing if current temperature is negative or zero`() {

        val currentTemperature = -1.0

        val expected = WeatherForecastMockDataFactory.weatherForecastDto(currentTemperature)

        val testObserver = TestObserver<WeatherForecast>()

        Observable.fromCallable { expected }
            .compose(DtoToWeatherForecastMapper())
            .subscribe(testObserver)

        testObserver
            .assertComplete()
            .assertNoErrors()

        val actual = testObserver.values()[0]

        assertEqual(actual, expected)

        assertThat(actual.backgroundHexColor).isEqualTo(HEX_COLOR_FREEZING)
    }

    @Test
    fun `background hex color should be equal to warm if current temperature ranges from 16 and 30`() {

        val currentTemperature = (16..30).random().toDouble()

        val expected = WeatherForecastMockDataFactory.weatherForecastDto(currentTemperature)

        val testObserver = TestObserver<WeatherForecast>()

        Observable.fromCallable { expected }
            .compose(DtoToWeatherForecastMapper())
            .subscribe(testObserver)

        testObserver
            .assertComplete()
            .assertNoErrors()

        val actual = testObserver.values()[0]

        assertEqual(actual, expected)

        assertThat(actual.backgroundHexColor).isEqualTo(HEX_COLOR_WARM)
    }

    @Test
    fun `background hex color should be equal to hot if current temperature is over 31`() {

        val currentTemperature = (31..Int.MAX_VALUE).random().toDouble()

        val expected = WeatherForecastMockDataFactory.weatherForecastDto(currentTemperature)

        val testObserver = TestObserver<WeatherForecast>()

        Observable.fromCallable { expected }
            .compose(DtoToWeatherForecastMapper())
            .subscribe(testObserver)

        testObserver
            .assertComplete()
            .assertNoErrors()

        val actual = testObserver.values()[0]

        assertEqual(actual, expected)

        assertThat(actual.backgroundHexColor).isEqualTo(HEX_COLOR_HOT)
    }

    @Test
    fun `weather status should be an empty string if WeatherForecastStatusDto List is null`() {
        val expected =
            WeatherForecastMockDataFactory.weatherForecastDto(weatherForecastStatusDto = null)

        val testObserver = TestObserver<WeatherForecast>()

        Observable.fromCallable { expected }
            .compose(DtoToWeatherForecastMapper())
            .subscribe(testObserver)

        testObserver
            .assertComplete()
            .assertNoErrors()
    }

    private fun assertEqual(
        actual: WeatherForecast,
        expected: WeatherForecastDto
    ) {
        assertThat(actual.currentTemperature).isEqualTo(expected.weatherForecastMainDto.currentTemperature)

        assertThat(actual.favorite).isFalse

        assertThat(actual.locationId).isEqualTo(expected.locationId)

        assertThat(actual.locationName).isEqualTo(expected.locationName)

        assertThat(actual.maxTemperature).isEqualTo(expected.weatherForecastMainDto.maxTemperature)

        assertThat(actual.minTemperature).isEqualTo(expected.weatherForecastMainDto.minTemperature)

        assertThat(actual.weatherStatus).isEqualTo(expected.weatherForecastStatusDto!![0].status)
    }
}
