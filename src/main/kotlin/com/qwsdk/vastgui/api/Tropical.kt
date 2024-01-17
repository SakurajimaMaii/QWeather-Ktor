/*
 * Copyright 2023 RTAkland
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.qwsdk.vastgui.api

import com.qwsdk.vastgui.QWSdk
import com.qwsdk.vastgui.entity.tropical.forecast.StormForecast
import com.qwsdk.vastgui.entity.tropical.list.StormList
import com.qwsdk.vastgui.entity.tropical.track.StormTrack
import com.qwsdk.vastgui.utils.StormId
import com.qwsdk.vastgui.utils.apiCatching
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.time.Year

/**
 * [热带气旋（台风）](https://dev.qweather.com/docs/api/tropical-cyclone/)
 *
 * 热带气旋（台风）API提供全球主要海洋流域的台风信息，包括台风实时位置、等级、气压、
 * 风速，还可查询台风路径和台风预报信息。
 */
class Tropical internal constructor(private val qwSdk: QWSdk) {
    /**
     * [台风预报](https://dev.qweather.com/docs/api/tropical-cyclone/storm-forecast/)
     *
     * 台风预报 API 提供全球主要海洋流域的台风预测位置、等级、气压、风速等。 如果查询的台风已经结束，则返回的数据为空，建议先通过
     * [台风列表接口][list] 获取台风的状态
     *
     * @param stormID 需要查询的台风 ID ，StormID 可通过 [台风查询 API][list] 获取。例如
     * stormid=NP2018 。
     */
    @Throws(IllegalStateException::class)
    suspend fun forecast(stormID: StormId): Result<StormForecast> = apiCatching {
        check(qwSdk.apiPlan.isFree()) {
            "Invalid permission: please refer to https://dev.qweather.com/docs/finance/subscription/#comparison"
        }
        qwSdk.client.get("tropical/storm-forecast") {
            url {
                parameter("stormid", stormID.id)
            }
        }.body()
    }

    /**
     * [台风实况和路径](https://dev.qweather.com/docs/api/tropical-cyclone/storm-track/)
     *
     * 台风实况和路径API提供全球主要海洋流域的台风实时位置、等级、气压、风速以及活跃台风的轨迹路径。
     *
     * @param stormID 需要查询的台风 ID ，StormID 可通过 [台风查询 API][list] 获取。例如
     * stormid=NP2018 。
     */
    @Throws(IllegalStateException::class)
    suspend fun track(stormID: StormId): Result<StormTrack> = apiCatching {
        check(qwSdk.apiPlan.isFree()) {
            "Invalid permission: please refer to https://dev.qweather.com/docs/finance/subscription/#comparison"
        }
        qwSdk.client.get("tropical/storm-track") {
            url {
                parameter("stormid", stormID.id)
            }
        }.body()
    }

    /**
     * [台风列表](https://dev.qweather.com/docs/api/tropical-cyclone/storm-list/)
     *
     * 台风列表API提供全球主要海洋流域最近2年的台风列表。目前仅支持中国沿海地区，即 [basin]=[QWSdk.BasinType.NP] 。
     *
     * @param year 支持查询本年度和上一年度的台风，例如：year=2020, year=2019 。
     * @param basin 需要查询的台风所在的流域，例如中国处于西北太平洋，即 basin=NP 。当前仅支持 NP 。
     */
    @Throws(IllegalStateException::class)
    suspend fun list(
        year: String,
        basin: QWSdk.BasinType = QWSdk.BasinType.NP
    ): Result<StormList> = apiCatching {
        check(qwSdk.apiPlan.isFree()) {
            "Invalid permission: please refer to https://dev.qweather.com/docs/finance/subscription/#comparison"
        }
        check(basin == QWSdk.BasinType.NP) {
            "Current not support this area: ${basin.name.lowercase()}!"
        }
        val currentYear = Year.now().toString().toInt()
        val lastYear = currentYear - 1
        check(year == currentYear.toString() || year == lastYear.toString()) {
            "You can't list the year before last year and future storms!"
        }
        qwSdk.client.get("tropical/storm-list") {
            url {
                parameter("basin", basin)
                parameter("year", year)
            }
        }.body()
    }
}