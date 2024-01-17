/*
 * Copyright 2024 VastGui
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qwsdk.vastgui.tests.main

import com.qwsdk.vastgui.tests.qw
import com.qwsdk.vastgui.tests.utils.getCurrentYear
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestTropical {
    @Test
    fun forecastTest() = runTest {
        qw.tropical().list(getCurrentYear()).getOrNull()?.storm?.get(0)?.getStormId()?.run {
            qw.tropical().forecast(this).onSuccess {
                it.forecast.forEach { forecast ->
                    println(forecast)
                }
                assertEquals(it.code.toInt(), 200)
            }.onFailure {
                println(it)
            }
        }
    }

    @Test
    fun trackTest() = runTest {
        qw.tropical().list(getCurrentYear()).getOrNull()?.storm?.get(0)?.getStormId()?.run {
            qw.tropical().track(this).onSuccess {
                it.track.forEach { track ->
                    println(track)
                }
                assertEquals(it.code.toInt(), 200)
            }.onFailure {
                println(it)
            }
        }
    }

    @Test
    fun listTest() = runTest {
        qw.tropical().list(getCurrentYear()).onSuccess {
            it.storm.forEach { track ->
                println(track)
            }
            assertEquals(it.code.toInt(), 200)
        }.onFailure {
            println(it)
        }
    }
}