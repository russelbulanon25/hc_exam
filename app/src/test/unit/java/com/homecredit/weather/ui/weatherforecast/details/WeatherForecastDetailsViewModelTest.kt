package com.homecredit.weather.ui.weatherforecast.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.homecredit.weather.BaseMockDataFactory.Companion.randomString
import com.homecredit.weather.Constants.Companion.WARNING_NO_INTERNET_CONNECTION
import com.homecredit.weather.RxImmediateSchedulerRule
import com.homecredit.weather.WeatherForecastMockDataFactory
import com.homecredit.weather.ui.weatherforecast.repository.WeatherForecastRepository
import com.homecredit.weather.ui.weatherforecast.repository.mapper.DtoToWeatherForecastMapper
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast
import com.homecredit.weather.util.connection.NoInternetThrowable
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.*

class WeatherForecastDetailsViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var navigator: WeatherForecastDetailsFragmentNavigator

    @MockK
    lateinit var weatherForecastRepository: WeatherForecastRepository

    lateinit var classUnderTest: WeatherForecastDetailsViewModel

    lateinit var weatherForecast: WeatherForecast

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        weatherForecast =
            Observable.fromCallable { WeatherForecastMockDataFactory.weatherForecastDto() }
                .compose(DtoToWeatherForecastMapper())
                .blockingFirst()

        classUnderTest = WeatherForecastDetailsViewModel(weatherForecastRepository)

        classUnderTest.navigator = navigator

        classUnderTest.setWeatherForecastFromArgs(weatherForecast)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `refreshing of weather forecast by city will fail if no internet connection`() {
        every {
            weatherForecastRepository.getWeatherForecastFromCity(any())
        } returns Single.error { NoInternetThrowable() }

        classUnderTest.refreshData()

        val actualErrorMessageSlot = slot<String>()

        verify { navigator.onShowLoading() }
        verify { navigator.onDismissLoading() }

        verify(atLeast = 1) {
            navigator.onShowErrorMessage(errorMessage = capture(actualErrorMessageSlot))
        }

        verify { weatherForecastRepository.getWeatherForecastFromCity(any()) wasNot Called }

        assertThat(actualErrorMessageSlot.captured).isEqualTo(WARNING_NO_INTERNET_CONNECTION)
    }

    @Test
    fun `refreshing of weather forecast by city will fail`() {
        val expectedErrorMessage = randomString()

        every {
            weatherForecastRepository.getWeatherForecastFromCity(any())
        } returns Single.error { Throwable(expectedErrorMessage) }

        classUnderTest.refreshData()

        val actualErrorMessageSlot = slot<String>()

        verify { navigator.onShowLoading() }
        verify { navigator.onDismissLoading() }

        verify(atLeast = 1) {
            navigator.onShowErrorMessage(errorMessage = capture(actualErrorMessageSlot))
        }

        assertThat(actualErrorMessageSlot.captured).isEqualTo(expectedErrorMessage)

        verify { navigator.onShowLoading() }

        verify { navigator.onDismissLoading() }

        val slot = slot<Int>()

        verify(atLeast = 1) {
            weatherForecastRepository.getWeatherForecastFromCity(
                cityId = capture(
                    slot
                )
            )
        }

        assertThat(slot.captured)
            .isEqualTo(classUnderTest.weatherForecastLiveData.value!!.locationId)
    }

    @Test
    fun `refreshing of weather forecast by city will succeed`() {
        val expectedLocationId = weatherForecast.locationId

        // verify that we still have the old data before we do the refresh
        assertThat(classUnderTest.weatherForecastLiveData.value!!.currentTemperature).isEqualTo(
            weatherForecast.currentTemperature
        )

        assertThat(classUnderTest.weatherForecastLiveData.value!!.locationId).isEqualTo(
            weatherForecast.locationId
        )

        assertThat(classUnderTest.weatherForecastLiveData.value!!.locationName).isEqualTo(
            weatherForecast.locationName
        )

        assertThat(classUnderTest.weatherForecastLiveData.value!!.maxTemperature).isEqualTo(
            weatherForecast.maxTemperature
        )

        assertThat(classUnderTest.weatherForecastLiveData.value!!.minTemperature).isEqualTo(
            weatherForecast.minTemperature
        )

        assertThat(classUnderTest.weatherForecastLiveData.value!!.weatherStatus).isEqualTo(
            weatherForecast.weatherStatus
        )

        val updatedWeatherForecast =
            Observable.fromCallable { WeatherForecastMockDataFactory.weatherForecastDto() }
                .compose(DtoToWeatherForecastMapper())
                .blockingFirst()

        every {
            weatherForecastRepository.getWeatherForecastFromCity(any())
        } returns Single.fromCallable { updatedWeatherForecast }

        classUnderTest.refreshData()

        // verify that we have now the updated weather forecast data
        assertThat(classUnderTest.weatherForecastLiveData.value!!.backgroundHexColor).isEqualTo(
            updatedWeatherForecast.backgroundHexColor
        )

        assertThat(classUnderTest.weatherForecastLiveData.value!!.currentTemperature).isEqualTo(
            updatedWeatherForecast.currentTemperature
        )

        assertThat(classUnderTest.weatherForecastLiveData.value!!.maxTemperature).isEqualTo(
            updatedWeatherForecast.maxTemperature
        )

        assertThat(classUnderTest.weatherForecastLiveData.value!!.minTemperature).isEqualTo(
            updatedWeatherForecast.minTemperature
        )

        assertThat(classUnderTest.weatherForecastLiveData.value!!.weatherStatus).isEqualTo(
            updatedWeatherForecast.weatherStatus
        )

        verify { navigator.onShowLoading() }

        verify { navigator.onDismissLoading() }

        val slot = slot<Int>()

        verify(atLeast = 1) {
            weatherForecastRepository.getWeatherForecastFromCity(
                cityId = capture(
                    slot
                )
            )
        }

        assertThat(slot.captured).isEqualTo(expectedLocationId)
    }

}