package com.homecredit.weather.util.connection

import io.reactivex.rxjava3.core.Single

interface ConnectionUtil {

    fun isConnected(): Single<Boolean>
}