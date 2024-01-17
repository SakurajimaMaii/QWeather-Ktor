/*
 * Copyright 2023 RTAkland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qwsdk.vastgui.utils

import com.qwsdk.vastgui.QWSdk

fun parseIndices(indices: QWSdk.IndicesType): Int {
    val number = when (indices) {
        QWSdk.IndicesType.ALL -> 0
        QWSdk.IndicesType.SPORT -> 1
        QWSdk.IndicesType.WASH_CAR -> 2
        QWSdk.IndicesType.CLOTHING -> 3
        QWSdk.IndicesType.FISHING -> 4
        QWSdk.IndicesType.UV_RAY -> 5
        QWSdk.IndicesType.TRAVEL -> 6
        QWSdk.IndicesType.POLLEN_ALLERGY -> 7
        QWSdk.IndicesType.COMFORT -> 8
        QWSdk.IndicesType.COLD -> 9
        QWSdk.IndicesType.AIR_POLLUTION_DIFFUSION_CONDITION -> 10
        QWSdk.IndicesType.AIR_CONDITIONER -> 11
        QWSdk.IndicesType.SUNGLASSES -> 12
        QWSdk.IndicesType.MAKEUP -> 13
        QWSdk.IndicesType.DRYING -> 14
        QWSdk.IndicesType.TRAFFIC -> 15
        QWSdk.IndicesType.SPF -> 16
    }
    return number
}
