package com.homecredit.weather.di.module

import com.homecredit.weather.BuildConfig
import com.homecredit.weather.data.api.ApiHelper
import com.homecredit.weather.data.api.ApiHelperImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single<ApiHelper> {
        return@single ApiHelperImpl(
            get(named("publicRetrofit"))
        )
    }

    single(named("publicOkhttpClient")) { providePublicOkHttpClient() }

    single(named("publicRetrofit")) {
        providePublicRetrofit(
            get(named("publicOkhttpClient")),
            "http://api.openweathermap.org"
        )
    }
}

// for endpoints that doesn't need authentication
private fun providePublicOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient
    .Builder()
    .build()

private fun providePublicRetrofit(
    publicOkhttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(publicOkhttpClient)
        .build()

