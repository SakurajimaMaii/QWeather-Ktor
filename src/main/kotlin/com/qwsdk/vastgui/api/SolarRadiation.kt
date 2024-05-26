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

package com.qwsdk.vastgui.api

import com.qwsdk.vastgui.QWSdk
import com.qwsdk.vastgui.entity.radiation.SolarRadiation
import com.qwsdk.vastgui.utils.Coordinate
import com.qwsdk.vastgui.utils.Day
import com.qwsdk.vastgui.utils.Hour
import io.ktor.client.call.*
import io.ktor.client.request.*

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/1/17

/**
 * [太阳辐射](https://dev.qweather.com/docs/api/solar-radiation/)
 *
 * 太阳辐射API支持获取全球任意坐标的辐射数据，包括净太阳辐射，太阳散射辐射和太阳直接辐射。
 */
class SolarRadiation internal constructor(private val qwSdk: QWSdk) {
    /**
     * [太阳辐射逐小时预报](https://dev.qweather.com/docs/api/solar-radiation/solar-radiation-hourly-forecast/)
     *
     * 太阳辐射API支持获取全球任意坐标的辐射数据，包括净太阳辐射，太阳散射辐射和太阳直接辐射。
     *
     * **注意：在一些特殊情况下，未来72小时预报有可能会缺少最后若干小时的数据。**
     *
     * @param location 需要查询地区的以英文逗号分隔的经度,纬度坐标 [Coordinate] 。
     */
    suspend fun radiation(
        hour: Hour = Hour.Hour24,
        location: Coordinate
    ): Result<SolarRadiation> = runCatching {
        check(qwSdk.apiPlan.isStandard()) { "无效权限，请参考：https://dev.qweather.com/docs/finance/subscription/#comparison" }
        check(hour is Hour.Hour24 || hour is Hour.Hour72) { "无效时间范围：仅支持 Hour24 或 Hour72。" }
        qwSdk.client.get("solar-radiation/${hour.range}") {
            parameter("location", location.location)
        }.body()
    }
}