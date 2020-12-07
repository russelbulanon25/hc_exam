package com.homecredit.weather.ui.weather.repository.remote

import com.homecredit.weather.BaseMockDataFactory.Companion.randomInt
import com.homecredit.weather.MockWebServiceRule
import com.homecredit.weather.WeatherMockDataFactory
import com.homecredit.weather.data.api.ApiHelper
import com.homecredit.weather.data.api.weather.GroupedWeatherForecastDto
import com.homecredit.weather.data.api.weather.WeatherApi
import com.homecredit.weather.data.api.weather.WeatherForecastDto
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.HttpURLConnection.HTTP_OK

class RemoteWeatherForecastDataSourceImplTest {

    @get:Rule
    var rule = MockWebServiceRule()

    @MockK
    lateinit var apiHelper: ApiHelper

    private lateinit var classUnderTest: RemoteWeatherDataSourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        classUnderTest = RemoteWeatherDataSourceImpl(apiHelper)
    }

    @After
    fun tearDown() {
        rule.mockWebServer().shutdown()
    }

    @Test
    fun loadingOfWeatherFromCitiesWillSucceed() {
        provideMockApiHelper()

        val expectedResponse = WeatherMockDataFactory.groupedWeatherDto()

        rule.mockWebServer().enqueue(
            MockResponse()
                .setResponseCode(HTTP_OK)
                .setBody(rule.jsonAdapter(GroupedWeatherForecastDto::class.java).toJson(expectedResponse))
        )

        val expectedCityIds = listOf(randomInt(), randomInt(), randomInt())

        classUnderTest
            .getWeatherForecastFromCities(expectedCityIds)
            .test()
            .await()
            .assertComplete()
            .assertNoErrors()

        val recordedRequest = rule.mockWebServer().takeRequest()

        assertThat(recordedRequest.method).isEqualTo("GET")

        assertThat(recordedRequest.path).contains(
            "data/2.5/group?id=" + expectedCityIds.joinToString(
                ","
            )
        )
    }

    @Test
    fun loadingOfWeatherFromSpecificCityWillSucceed() {
        provideMockApiHelper()

        val expectedResponse = WeatherMockDataFactory.weatherDto()

        rule.mockWebServer().enqueue(
            MockResponse()
                .setResponseCode(HTTP_OK)
                .setBody(rule.jsonAdapter(WeatherForecastDto::class.java).toJson(expectedResponse))
        )

        val expectedCityId = randomInt()

        classUnderTest
            .getWeatherForecastFromCity(expectedCityId)
            .test()
            .await()
            .assertComplete()
            .assertNoErrors()

        val recordedRequest = rule.mockWebServer().takeRequest()

        assertThat(recordedRequest.method).isEqualTo("GET")

        assertThat(recordedRequest.path).contains(
            "data/2.5/weather?id=$expectedCityId"
        )
    }

    private fun provideMockApiHelper() {
        every { apiHelper.public(WeatherApi::class.java) } returns Single.fromCallable {
            rule.create(WeatherApi::class.java)
        }
    }
}