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

package com.qwsdk.vastgui.entity.tropical.forecast

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [台风预报](https://dev.qweather.com/docs/api/tropical-cyclone/storm-forecast/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property forecast 参考 [Forecast] 。
 * @property fxLink 当前数据的响应式页面，便于嵌入网站或应用。
 * @property refer 参考 [Refer] 。
 * @property updateTime 当前 [API的最近更新时间](https://dev.qweather.com/docs/resource/glossary/#update-time) 。
 */
@Serializable
data class StormForecast(
    override val code: String,
    val forecast: List<Forecast> = emptyList(),
    val fxLink: String? = null,
    val refer: Refer = Refer(),
    val updateTime: String? = null
) : QWSdkResponse {
    /**
     * [台风预报](https://dev.qweather.com/docs/api/tropical-cyclone/storm-forecast/)
     *
     * @property fxTime 台风预报时间
     * @property lat 台风所处纬度。
     * @property lon 台风所处经度。
     * @property move360 台风移动方位360度方向。
     * @property moveDir 台风移动方位。
     * @property moveSpeed 台风移动速度。
     * @property pressure 台风中心气压。
     * @property type 台风类型。
     * @property windSpeed 台风附近最大风速。
     */
    @Serializable
    data class Forecast(
        val fxTime: String,
        val lat: String,
        val lon: String,
        val move360: String,
        val moveDir: String,
        val moveSpeed: String,
        val pressure: String,
        val type: String,
        val windSpeed: String
    )
}