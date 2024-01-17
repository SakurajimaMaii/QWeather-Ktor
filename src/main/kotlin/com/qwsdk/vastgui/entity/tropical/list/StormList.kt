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

package com.qwsdk.vastgui.entity.tropical.list

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.QWSdkResponse
import com.qwsdk.vastgui.utils.StormId
import kotlinx.serialization.Serializable

/**
 * [台风列表](https://dev.qweather.com/docs/api/tropical-cyclone/storm-list/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property fxLink 当前数据的响应式页面，便于嵌入网站或应用。
 * @property refer 参考 [Refer] 。
 * @property storm 参考 [Storm] 。
 * @property updateTime 当前 [API的最近更新时间](https://dev.qweather.com/docs/resource/glossary/#update-time) 。
 */
@Serializable
data class StormList(
    override val code: String,
    val fxLink: String? = null,
    val refer: Refer = Refer(),
    val storm: List<Storm> = emptyList(),
    val updateTime: String? = null
) : QWSdkResponse {
    /**
     * [台风列表](https://dev.qweather.com/docs/api/tropical-cyclone/storm-list/)
     *
     * @property isActive 是否为活跃台风。1 活跃台风，0 停编。
     * @property basin 台风所处流域。
     * @property id 台风 ID 。
     * @property name 台风名称。
     * @property year 台风所处年份。
     */
    @Serializable
    data class Storm(
        val isActive: String,
        val basin: String,
        val id: String,
        val name: String,
        val year: String
    ) {
        /**
         * 获取需要查询的台风 ID 。
         */
        fun getStormId() = StormId(id)
    }
}