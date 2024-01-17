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
import com.qwsdk.vastgui.tests.utils.randomID
import com.qwsdk.vastgui.utils.Day
import com.qwsdk.vastgui.utils.Hour
import com.qwsdk.vastgui.utils.LocationID
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestWeather {
    private val locationID = randomID()

    @Test
    fun nowTest() = runTest {
        qw.weather().now(LocationID(locationID)).onSuccess {
            println(it.now)
            assertEquals(it.code.toInt(), 200)
        }.onFailure {
            println(it)
        }
    }

    @Test
    fun dailyTest() = runTest {
        qw.weather().daily(Day.Day30, LocationID(locationID)).onSuccess {
            it.daily.forEach { daily ->
                println(daily)
            }
            assertEquals(it.code.toInt(), 200)
        }.onFailure {
            println(it)
        }
    }

    @Test
    fun hourlyTest() = runTest {
        qw.weather().hourly(Hour.Hour72, LocationID(locationID)).onSuccess {
            it.hourly.forEach { hourly ->
                println(hourly)
            }
            assertEquals(it.code.toInt(), 200)
        }.onFailure {
            println(it)
        }
    }
}