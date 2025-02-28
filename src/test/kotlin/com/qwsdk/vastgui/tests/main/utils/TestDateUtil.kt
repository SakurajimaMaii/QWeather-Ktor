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

package com.qwsdk.vastgui.tests.main.utils

import com.qwsdk.vastgui.utils.exceptions.InvalidDateException
import com.qwsdk.vastgui.utils.DateUtil
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class TestDateUtil {

    @Test
    fun verifyYMDTest() {
        assertThrows<InvalidDateException> {
            DateUtil("202201021").verifyYMD()  // wrong length
            DateUtil("20210229").verifyYMD()  // not leap year
            DateUtil("20221320").verifyYMD()  // Month 13 (
        }

        assertDoesNotThrow {
            DateUtil("20230110").verifyYMD()  // correct
        }
    }

    @Test
    fun verifyHMTest() {
        assertThrows<InvalidDateException> {
            DateUtil("3010").verifyHM()
            DateUtil("22100").verifyHM()
            DateUtil("1961").verifyHM()
        }

        assertDoesNotThrow {
            DateUtil("0110").verifyHM()
        }
    }
}