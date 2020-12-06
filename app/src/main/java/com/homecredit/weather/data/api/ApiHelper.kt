package com.homecredit.weather.data.api

import io.reactivex.rxjava3.core.Single

interface ApiHelper {

    // for consuming of endpoints that doesn't need authentication
    fun <T> public(apiClass: Class<T>): Single<T>
}