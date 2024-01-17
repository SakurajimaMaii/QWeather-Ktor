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
import com.qwsdk.vastgui.utils.Coordinate
import com.qwsdk.vastgui.utils.Day
import com.qwsdk.vastgui.utils.Hour
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/1/16

class TestGrid {
    private val coordinate = Coordinate(116.41, 39.92)

    @Test
    fun nowTest() = runTest {
        qw.grid().now(coordinate).onSuccess {
            println(it.now)
            assertEquals(it.code.toInt(), 200)
        }.onFailure {
            println(it)
        }
    }

    @Test
    fun dailyTest() = runTest {
        qw.grid().daily(Day.Day3, Coordinate(116.41, 39.92)).onSuccess {
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
        qw.grid().hourly(Hour.Hour24, Coordinate(116.41, 39.92)).onSuccess {
            it.hourly.forEach { hourly ->
                println(hourly)
            }
            assertEquals(it.code.toInt(), 200)
        }.onFailure {
            println(it)
        }
    }
}