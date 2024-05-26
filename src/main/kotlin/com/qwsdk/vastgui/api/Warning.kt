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
import com.qwsdk.vastgui.entity.warning.Warning
import com.qwsdk.vastgui.entity.warning.list.WarningCityList
import com.qwsdk.vastgui.utils.Coordinate
import com.qwsdk.vastgui.utils.Location
import com.qwsdk.vastgui.utils.LocationID
import com.qwsdk.vastgui.utils.apiCatching
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * [预警](https://dev.qweather.com/docs/api/warning/)
 *
 * 和风天气灾害预警API提供了全球极端天气预警服务，覆盖中国及全球数十个国家或地区。
 *
 * 预警数据包括数百种灾害预警类型，例如：台风、暴雨、暴雪、寒潮、大风、沙尘暴、高温、
 * 干旱、雷电、冰雹、霜冻、大雾、霾、道路结冰、寒冷、灰霾、雷雨大风、森林火险、降温、
 * 道路冰雪、干热风、低温、冰冻、空气重污染、海上大雾、雷暴大风、持续低温、浓浮尘、
 * 龙卷风、低温冻害、海上大风、低温雨雪冰冻、强对流、臭氧、大雪、强降雨、强降温、
 * 雪灾、森林（草原）火险、雷暴、严寒、沙尘等等。
 *
 * 了解更多关于我们 [支持的国家或地区](https://dev.qweather.com/docs/resource/warning-info/#supported-regions) 、
 * 支持的 [预警严重程度](https://dev.qweather.com/docs/resource/warning-info/#warning-severity) 和
 * [预警类型](https://dev.qweather.com/docs/resource/warning-info/#warning-type) 。
 */
class Warning internal constructor(private val qwSdk: QWSdk) {
    /**
     * [天气灾害预警](https://dev.qweather.com/docs/api/warning/weather-warning/)
     *
     * 天气灾害预警API可以获取中国及全球多个国家或地区官方发布的实时天气灾害预警数据。
     *
     * 提示：天气预警有较多规则和需要注意的事项，在开始使用天气预警之前，你应该先阅读
     * [实用资料-预警信息](https://dev.qweather.com/docs/resource/warning-info/) 。
     *
     * @param location 需要查询地区的 [LocationID] 或以英文逗号分隔的经度,纬度坐标 [Coordinate] ，
     * LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    suspend fun now(
        location: Location,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<Warning> = apiCatching {
        check(location is LocationID || location is Coordinate) { "无效类型，当前仅支持Coordinate或LocationID" }
        qwSdk.client.get("warning/now") {
            url {
                parameter("location", location.location)
                parameter("lang", lang)
            }
        }.body()
    }

    /**
     * [天气预警城市列表](https://dev.qweather.com/docs/api/warning/weather-warning-city-list/)
     *
     * 获取指定国家或地区当前正在发生天气灾害预警的城市列表，根据这些城市列表再查询对应城市的天气灾害预警。
     * - 目前天气预警城市列表仅适用于获取中国（包括港澳台）城市列表。其他国家和地区，请使用 [天气灾害预警][now] 。
     * - 关于更多天气预警数据的说明，请参考 [实用资料-预警信息](https://dev.qweather.com/docs/resource/warning-info/) 。
     *
     * @param range 选择指定的国家或地区，使用 ISO 3166 格式。例如 range=cn 或 range=hk 。目前该功能仅支持中国
     * （包括港澳台）地区的城市列表，其他国家和地区请使用请使用 [天气灾害预警][now] 单独获取。
     */
    suspend fun list(
        range: QWSdk.CountryCode = QWSdk.CountryCode.CN
    ): Result<WarningCityList> = apiCatching {
        qwSdk.client.get("warning/list") {
            url {
                parameter("range", range)
            }
        }.body()
    }
}