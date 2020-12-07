package com.homecredit.weather.ui.weatherforecast.repository

import com.homecredit.weather.BaseMockDataFactory.Companion.randomInt
import com.homecredit.weather.WeatherForecastMockDataFactory
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast
import com.homecredit.weather.ui.weatherforecast.repository.remote.RemoteWeatherForecastDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class WeatherForecastRepositoryImplTest {

    @MockK
    lateinit var remoteWeatherForecastDataSource: RemoteWeatherForecastDataSource

    lateinit var classUnderTest: WeatherForecastRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        classUnderTest = WeatherForecastRepositoryImpl(remoteWeatherForecastDataSource)
    }

    @Test
    fun `loading of weather forecast from all cities will succeed`() {
        val expected = WeatherForecastMockDataFactory.groupedWeatherDto()

        every {
            remoteWeatherForecastDataSource.getWeatherForecastFromCities(any())
        } returns (Single.fromCallable { expected })

        val expectedCityIds = listOf(randomInt(), randomInt(), randomInt())

        val testObserver = TestObserver<WeatherForecast>()

        classUnderTest
            .getWeatherForecastFromCities(expectedCityIds)
            .subscribe(testObserver)

        testObserver
            .assertComplete()
            .assertNoErrors()

        val actual = testObserver.values()

        assertThat(actual.size).isEqualTo(expected.weatherForecastDtoList.size)

        actual.forEachIndexed { index, weatherForecast ->

            assertThat(weatherForecast.currentTemperature).isEqualTo(
                expected.weatherForecastDtoList[index].weatherForecastMainDto.currentTemperature
            )

            assertThat(weatherForecast.favorite).isFalse

            assertThat(weatherForecast.locationId).isEqualTo(
                expected.weatherForecastDtoList[index].locationId
            )

            assertThat(weatherForecast.locationName).isEqualTo(
                expected.weatherForecastDtoList[index].locationName
            )

            assertThat(weatherForecast.maxTemperature).isEqualTo(
                expected.weatherForecastDtoList[index].weatherForecastMainDto.maxTemperature
            )

            assertThat(weatherForecast.minTemperature).isEqualTo(
                expected.weatherForecastDtoList[index].weatherForecastMainDto.minTemperature
            )

            assertThat(weatherForecast.weatherStatus).isEqualTo(
                expected.weatherForecastDtoList[index].weatherForecastStatusDto!![0].status
            )
        }
    }
}