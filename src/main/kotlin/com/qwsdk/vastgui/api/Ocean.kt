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
import com.qwsdk.vastgui.entity.ocean.currents.Currents
import com.qwsdk.vastgui.entity.ocean.tide.Tide
import com.qwsdk.vastgui.utils.DateUtil
import com.qwsdk.vastgui.utils.LocationID
import com.qwsdk.vastgui.utils.apiCatching
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * [海洋数据](https://dev.qweather.com/docs/api/ocean/)
 *
 * 海洋数据API提供全球主要港口和城市的潮汐和潮流数据。
 */
class Ocean internal constructor(private val qwSdk: QWSdk) {
    /**
     * [潮汐](https://dev.qweather.com/docs/api/ocean/tide/)
     *
     * 未来10天全球潮汐数据，包括满潮、干潮高度和时间，逐小时潮汐数据。
     *
     * @param location 需要查询的潮汐站点，请填写潮汐站点的 [LocationID] ，
     * LocationID 可通过 [POI][Geo.poiLookup] 搜索服务获取。例如 location=P2951 。
     * @param date 选择日期，最多可选择未来 10 天（包含今天）的数据。日期格式为 yyyyMMdd ，
     * 例如 date=20200531 。
     */
    @Throws(IllegalStateException::class)
    suspend fun tide(
        location: LocationID,
        date: String
    ): Result<Tide> = apiCatching {
        check(qwSdk.apiPlan.isStandard()) { "无效权限，请参考：https://dev.qweather.com/docs/finance/subscription/#comparison" }
        DateUtil(date).verifyYMD()
        qwSdk.client.get("ocean/tide") {
            url {
                parameter("location", location.location)
                parameter("date", date)
            }
        }.body()
    }

    /**
     * [潮流](https://dev.qweather.com/docs/api/ocean/currents/)
     *
     * 未来10天全球潮流数据，包括潮流流速和流向。
     *
     * @param location 需要查询的潮流站点，请填写潮流站点的
     * [LocationID](https://dev.qweather.com/docs/resource/glossary/#locationid) ，
     * LocationID 可通过 [POI][Geo.poiLookup] 搜索服务获取。例如 location=P66981 。
     * @param date 选择日期，最多可选择未来 10 天（包含今天）的数据。日期格式为 yyyyMMdd ，
     * 例如 date=20200531 。
     */
    @Throws(IllegalStateException::class)
    suspend fun currents(
        location: LocationID,
        date: String
    ): Result<Currents> = apiCatching {
        check(qwSdk.apiPlan.isStandard()) { "无效权限，请参考：https://dev.qweather.com/docs/finance/subscription/#comparison" }
        DateUtil(date).verifyYMD()
        qwSdk.client.get("ocean/currents") {
            url {
                parameter("location", location.location)
                parameter("date", date)
            }
        }.body()
    }
}