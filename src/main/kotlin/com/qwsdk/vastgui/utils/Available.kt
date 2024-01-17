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

package com.qwsdk.vastgui.utils

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/1/16

/**
 * 针对不同订阅计划可用的时间范围。
 */
sealed interface Available {
    val range: String
}

sealed class Day(override val range: String) : Available {
    data object Day3 : Day("3d")
    data object Day7 : Day("7d")
    data object Day10 : Day("10d")
    data object Day15 : Day("15d")
    data object Day30 : Day("30d")

    override fun toString(): String = range
}

sealed class Hour(override val range: String) : Available {
    data object Hour24 : Hour("24h")
    data object Hour72 : Hour("72h")
    data object Hour168 : Hour("168h")

    override fun toString(): String = range
}