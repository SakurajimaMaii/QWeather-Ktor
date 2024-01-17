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

package com.qwsdk.vastgui.entity.air.daily

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [空气质量每日预报](https://dev.qweather.com/docs/api/air/air-daily-forecast/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property daily 参考 [Daily] 。
 * @property fxLink 当前数据的响应式页面，便于嵌入网站或应用。
 * @property refer 参考 [Refer] 。
 * @property updateTime 当前 [API的最近更新时间](https://dev.qweather.com/docs/resource/glossary/#update-time) 。
 */
@Serializable
data class AirDaily(
    override val code: String,
    val daily: List<Daily> = emptyList(),
    val fxLink: String? = null,
    val refer: Refer = Refer(),
    val updateTime: String? = null
) : QWSdkResponse {
    /**
     * [空气质量每日预报](https://dev.qweather.com/docs/api/air/air-daily-forecast/)
     *
     * @property aqi 空气质量指数。
     * @property category 空气质量指数级别。
     * @property fxDate 预报日期。
     * @property level 空气质量指数等级。
     * @property primary 空气质量的主要污染物，空气质量为优时，返回值为 NA 。
     */
    @Serializable
    data class Daily(
        val aqi: String,
        val category: String,
        val fxDate: String,
        val level: String,
        val primary: String
    )
}