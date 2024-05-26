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
import com.qwsdk.vastgui.entity.historical.air.HistoricalAir
import com.qwsdk.vastgui.entity.historical.weather.HistoricalWeather
import com.qwsdk.vastgui.utils.DateUtil
import com.qwsdk.vastgui.utils.LocationID
import com.qwsdk.vastgui.utils.apiCatching
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * [时光机](https://dev.qweather.com/docs/api/time-machine/)
 *
 * 时光机可以获取最近10天的历史天气和空气质量数据。
 */
class TimeMachine internal constructor(private val qwSdk: QWSdk) {
    /**
     * [天气时光机](https://dev.qweather.com/docs/api/time-machine/time-machine-weather/)
     *
     * 获取最近 10 天的天气历史再分析数据。例如今天是 12 月 30 日，最多可获取 12 月 20 日
     * 至 12 月 29 日的天气历史数据。
     *
     * @param location 需要查询的地区，仅支持 [LocationID] ，LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param date 选择日期，最多可选择最近 10 天（不包含今天）的数据。日期格式为 yyyyMMdd ，
     * 例如 date=20200531 。
     * @param unit 数据单位设置，可选值包括unit=m（公制单位，默认）和unit=i（英制单位）。更多
     * 选项和说明参考 [度量衡单位](https://dev.qweather.com/docs/resource/unit) 。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    suspend fun weatherHistory(
        location: LocationID,
        date: String,
        unit: QWSdk.Units = QWSdk.Units.M,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<HistoricalWeather> = apiCatching {
        check(qwSdk.apiPlan.isStandard()) { "无效权限，请参考：https://dev.qweather.com/docs/finance/subscription/#comparison" }
        DateUtil(date).verifyYMD()
        qwSdk.client.get("historical/weather") {
            url {
                parameter("location", location.location)
                parameter("lang", lang)
                parameter("unit", unit)
                parameter("date", date)
            }
        }.body()
    }

    /**
     * [空气质量时光机](https://dev.qweather.com/docs/api/time-machine/time-machine-air/)
     *
     * 获取最近 10 天的中国空气质量历史再分析数据。例如今天是 12 月 30 日，最多可获取 12 月
     * 20 日至 12 月 29 日的空气质量历史数据。
     *
     * @param location 需要查询的地区，仅支持 [LocationID] ，LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param date 选择日期，最多可选择最近 10 天（不包含今天）的数据。日期格式为 yyyyMMdd ，
     * 例如 date=20200531 。
     * @param unit 数据单位设置，可选值包括unit=m（公制单位，默认）和unit=i（英制单位）。更多
     * 选项和说明参考 [度量衡单位](https://dev.qweather.com/docs/resource/unit) 。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    suspend fun airHistory(
        location: LocationID,
        date: String,
        unit: QWSdk.Units = QWSdk.Units.M,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<HistoricalAir> = apiCatching {
        check(qwSdk.apiPlan.isStandard()) { "无效权限，请参考：https://dev.qweather.com/docs/finance/subscription/#comparison" }
        DateUtil(date).verifyYMD()
        qwSdk.client.get("historical/air") {
            url {
                parameter("location", location.location)
                parameter("lang", lang)
                parameter("unit", unit)
                parameter("date", date)
            }
        }.body()
    }
}