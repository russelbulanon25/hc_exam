package com.homecredit.weather.util

import com.homecredit.weather.util.connection.ConnectionUtil
import com.homecredit.weather.util.connection.ConnectionUtilImpl
import org.koin.dsl.module


val utilModule = module {
    single<ConnectionUtil> {
        return@single ConnectionUtilImpl(
            get()
        )
    }
}