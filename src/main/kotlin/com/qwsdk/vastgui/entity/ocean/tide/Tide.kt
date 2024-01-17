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

package com.qwsdk.vastgui.entity.ocean.tide

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [潮汐](https://dev.qweather.com/docs/api/ocean/tide/)
 *
 * @property code 请参考[状态码](https://dev.qweather.com/docs/resource/status-code/)。
 * @property updateTime 当前[API的最近更新时间](https://dev.qweather.com/docs/resource/glossary/#update-time)。
 * @property fxLink 当前数据的响应式页面，便于嵌入网站或应用。
 * @property tideHourly 参考 [TideHourly] 。
 * @property tideTable 参考 [TideTable] 。
 * @property refer 参考 [Refer] 。
 */
@Serializable
data class Tide(
    override val code: String,
    val fxLink: String? = null,
    val refer: Refer = Refer(),
    val tideHourly: List<TideHourly> = emptyList(),
    val tideTable: List<TideTable> = emptyList(),
    val updateTime: String? = null
) : QWSdkResponse {
    /**
     * [潮汐](https://dev.qweather.com/docs/api/ocean/tide/)
     *
     * @property fxTime 逐小时预报时间。
     * @property height 海水高度，单位：米。对于一些地点，此数据可能为空。
     */
    @Serializable
    data class TideHourly(
        val fxTime: String,
        val height: String
    )

    /**
     * [潮汐](https://dev.qweather.com/docs/api/ocean/tide/)
     *
     * @property fxTime 满潮或干潮时间。
     * @property height 海水高度，单位：米。
     * @property type 满潮（H）或干潮（L）。
     */
    @Serializable
    data class TideTable(
        val fxTime: String,
        val height: String,
        val type: String
    )
}