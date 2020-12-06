package com.homecredit.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.fluttercode.datafactory.impl.DataFactory
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class BaseMockDataFactory {

    companion object {
        private val FACTORY = DataFactory()

        fun randomString(): String {
            return FACTORY.getRandomChars(32)
        }

        fun randomInt(): Int {
            return FACTORY.getNumberBetween(1, 100)
        }

        fun randomBoolean(): Boolean {
            return FACTORY.chance(50)
        }

        fun randomIntBoolean(): Int {
            return if (randomBoolean()) 1 else 0
        }

        @Throws(InterruptedException::class)
        fun <T> getValue(liveData: LiveData<T>): T {
            val data = arrayOfNulls<Any>(1)
            val latch = CountDownLatch(1)
            val observer = object : Observer<T> {
                override fun onChanged(t: T?) {
                    data[0] = t
                    latch.countDown()
                    liveData.removeObserver(this)
                }

            }
            liveData.observeForever(observer)
            latch.await(2, TimeUnit.SECONDS)

            return data[0] as T
        }
    }
}