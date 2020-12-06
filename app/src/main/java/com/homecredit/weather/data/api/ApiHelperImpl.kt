package com.homecredit.weather.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit

class ApiHelperImpl(
    private val publicRetrofit: Retrofit
) : ApiHelper {

    override fun <T> public(apiClass: Class<T>): Single<T> {
        return Single.fromCallable { publicRetrofit.create(apiClass) }
    }
}