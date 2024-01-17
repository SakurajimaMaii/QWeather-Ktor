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

import com.qwsdk.vastgui.QWSdk
import com.qwsdk.vastgui.tests.qw
import com.qwsdk.vastgui.tests.utils.randomID
import com.qwsdk.vastgui.utils.LocationID
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestIndices {

    private val locationID = randomID()

    @Test
    fun indices1dTest() = runTest {
        qw.indices()
            .indices1d(LocationID(locationID), types = arrayOf(QWSdk.IndicesType.SPF, QWSdk.IndicesType.SPORT))
            .onSuccess {
                it.daily.forEach { daily ->
                    println(daily)
                }
                assertEquals(it.code.toInt(), 200)
            }.onFailure {
                println(it)
            }
    }

    @Test
    fun indices3d() = runTest {
        qw.indices()
            .indices3d(LocationID(locationID), types = arrayOf(QWSdk.IndicesType.SPF, QWSdk.IndicesType.SPORT))
            .onSuccess {
                it.daily.forEach { daily ->
                    println(daily)
                }
                assertEquals(it.code.toInt(), 200)
            }.onFailure {
                println(it)
            }
    }
}