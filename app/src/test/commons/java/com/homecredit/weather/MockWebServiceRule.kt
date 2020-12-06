package com.homecredit.weather

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MockWebServiceRule : ExternalResource() {

    private var mockWebServer: MockWebServer = MockWebServer()

    private var retrofit: Retrofit

    init {

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer!!.url(""))
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    fun <T> create(clazz: Class<T>?): T {
        return retrofit!!.create(clazz)
    }

    fun mockWebServer(): MockWebServer {
        return mockWebServer
    }

    fun <T> jsonAdapter(clazz: Class<T>): JsonAdapter<T> {
        return Moshi.Builder().build().adapter(clazz)
    }
}