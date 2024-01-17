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

package com.qwsdk.vastgui.entity.indices

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [天气指数预报](https://dev.qweather.com/docs/api/indices/indices-forecast/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property daily 参考 [Daily] 。
 * @property fxLink 当前数据的响应式页面，便于嵌入网站或应用。
 * @property refer 参考 [Refer] 。
 * @property updateTime 当前 [API的最近更新时间](https://dev.qweather.com/docs/resource/glossary/#update-time) 。
 */
@Serializable
data class Indices(
    override val code: String,
    val daily: List<Daily> = emptyList(),
    val fxLink: String? = null,
    val refer: Refer = Refer(),
    val updateTime: String? = null
) : QWSdkResponse {
    /**
     * [天气指数预报](https://dev.qweather.com/docs/api/indices/indices-forecast/)
     *
     * @property category 生活指数预报级别名称。
     * @property date 预报日期。
     * @property level 生活指数预报等级。
     * @property name 生活指数类型的名称。
     * @property text 生活指数预报的详细描述，可能为空
     * @property type 生活指数类型ID。
     */
    @Serializable
    data class Daily(
        val category: String,
        val date: String,
        val level: String,
        val name: String,
        val text: String? = null,
        val type: String
    )
}