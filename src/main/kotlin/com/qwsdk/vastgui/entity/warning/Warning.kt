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

package com.qwsdk.vastgui.entity.warning

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [天气灾害预警](https://dev.qweather.com/docs/api/warning/weather-warning/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property fxLink 当前数据的响应式页面，便于嵌入网站或应用。
 * @property refer 参考 [Refer] 。
 * @property updateTime 当前 [API的最近更新时间](https://dev.qweather.com/docs/resource/glossary/#update-time) 。
 * @property warning 参考 [Warning] ，如果查询的地区当前没有灾害预警信息，返回的warning字段为空。
 */
@Serializable
data class Warning(
    override val code: String,
    val fxLink: String? = null,
    val refer: Refer = Refer(),
    val updateTime: String? = null,
    val warning: List<Warning> = emptyList()
) : QWSdkResponse {
    /**
     * [天气灾害预警](https://dev.qweather.com/docs/api/warning/weather-warning/)
     *
     * @property endTime 灾害[预警结束时间](https://dev.qweather.com/docs/resource/warning-info/#expiry-time)，可能为空。
     * @property id 本条预警的唯一标识，可判断本条预警是否已经存在。
     * @property level 预警等级（已弃用），不要再使用这个字段，该字段已弃用，
     * 目前返回为空或未更新的值。请使用 [severity] 和 [severityColor] 代替。
     * @property pubTime 预警发布时间。
     * @property related 与本条预警相关联的预警ID，当预警状态为 cancel 或
     * update 时返回。可能为空
     * @property sender 预警发布单位，可能为空。
     * @property startTime 预警开始时间，可能为空。
     * @property status 预警信息的发布 [状态](https://dev.qweather.com/docs/resource/warning-info/#status) 。
     * @property text 预警详细文字描述。
     * @property title 预警信息标题。
     * @property type 预警 [类型ID](https://dev.qweather.com/docs/resource/warning-info/#warning-type) 。
     * @property typeName 预警 [类型名称](https://dev.qweather.com/docs/resource/warning-info/#warning-type) 。
     * @property severity 预警 [严重等级](https://dev.qweather.com/docs/resource/warning-info/#severity) 。
     * @property severityColor 预警 [严重等级颜色](https://dev.qweather.com/docs/resource/warning-info/#severity-color)，可能为空。
     * @property urgency 预警 [信息的紧迫程度](https://dev.qweather.com/docs/resource/warning-info/#urgency) ，可能为空。
     * @property certainty 预警 [信息的确定性](https://dev.qweather.com/docs/resource/warning-info/#certainty) ，可能为空。
     */
    @Serializable
    data class Warning(
        val id: String,
        val sender: String? = null,
        val pubTime: String,
        val title: String,
        val startTime: String? = null,
        val endTime: String? = null,
        val status: String,
        val level: String? = null,
        val severity: String,
        val severityColor: String? = null,
        val type: String,
        val typeName: String,
        val urgency: String? = null,
        val certainty: String? = null,
        val text: String,
        val related: String? = null,
    )
}