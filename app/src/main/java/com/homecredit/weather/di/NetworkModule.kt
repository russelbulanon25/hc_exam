package com.homecredit.weather.di.module

import com.homecredit.weather.BuildConfig
import com.homecredit.weather.data.api.ApiHelper
import com.homecredit.weather.data.api.ApiHelperImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit.MINUTES


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
    OkHttpClient.Builder()
        .connectTimeout(1, MINUTES)
        .readTimeout(1, MINUTES)
        .writeTimeout(1, MINUTES)
        .addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
        .addInterceptor { chain ->
            val original = chain.request()

            val updatedHttpUrl = original.url.newBuilder()
                .addQueryParameter("appid", "393eadff6164bf9a0df31a74b20efaee")
                .build()

            val updatedRequest = original.newBuilder().url(updatedHttpUrl).build()
            chain.proceed(updatedRequest)
        }
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
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .client(publicOkhttpClient)
        .build()

