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

package com.qwsdk.vastgui.entity.air.now

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [实时空气质量](https://dev.qweather.com/docs/api/air/air-now/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property fxLink 当前数据的响应式页面，便于嵌入网站或应用。
 * @property now 参考 [Now] 。
 * @property refer 参考 [Refer] 。
 * @property station 参考 [Station] 。
 * @property updateTime 当前 [API的最近更新时间](https://dev.qweather.com/docs/resource/glossary/#update-time) 。
 */
@Serializable
data class AirNow(
    override val code: String,
    val fxLink: String? = null,
    val now: Now? = null,
    val refer: Refer = Refer(),
    val station: List<Station> = emptyList(),
    val updateTime: String? = null
) : QWSdkResponse {
    /**
     * [实时空气质量](https://dev.qweather.com/docs/api/air/air-now/)
     *
     * @property aqi 空气质量指数。
     * @property category 空气质量指数级别。
     * @property co 一氧化碳。
     * @property level 空气质量指数等级。
     * @property no2 二氧化氮。
     * @property o3 臭氧。
     * @property pm10 PM10 。
     * @property pm2p5 PM2.5 。
     * @property primary 空气质量的主要污染物，空气质量为优时，返回值为 NA 。
     * @property pubTime 空气质量数据发布时间。
     * @property so2 二氧化硫。
     */
    @Serializable
    data class Now(
        val aqi: String,
        val category: String,
        val co: String,
        val level: String,
        val no2: String,
        val o3: String,
        val pm10: String,
        val pm2p5: String,
        val primary: String,
        val pubTime: String,
        val so2: String
    )

    /**
     * [实时空气质量](https://dev.qweather.com/docs/api/air/air-now/)
     *
     * @property aqi 空气质量指数。
     * @property category 空气质量指数级别。
     * @property co 一氧化碳。
     * @property id 监测站 ID 。
     * @property level 空气质量指数等级。
     * @property name 监测站名称。
     * @property no2 二氧化氮。
     * @property o3 臭氧。
     * @property pm10 PM10 。
     * @property pm2p5 PM2.5 。
     * @property primary 空气质量的主要污染物，空气质量为优时，返回值为 NA 。
     * @property pubTime 空气质量数据发布时间。
     * @property so2 二氧化硫。
     */
    @Serializable
    data class Station(
        val aqi: String,
        val category: String,
        val co: String,
        val id: String,
        val level: String,
        val name: String,
        val no2: String,
        val o3: String,
        val pm10: String,
        val pm2p5: String,
        val primary: String,
        val pubTime: String,
        val so2: String
    )
}