package com.homecredit.weather.util.connection

import com.homecredit.weather.Constants.Companion.WARNING_NO_INTERNET_CONNECTION

class NoInternetThrowable : Throwable(WARNING_NO_INTERNET_CONNECTION)