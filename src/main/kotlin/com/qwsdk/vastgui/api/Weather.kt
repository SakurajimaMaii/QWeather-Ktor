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
import com.qwsdk.vastgui.entity.weather.hourly.WeatherHourly
import com.qwsdk.vastgui.entity.weather.daily.WeatherDaily
import com.qwsdk.vastgui.entity.weather.now.WeatherNow
import com.qwsdk.vastgui.utils.*
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * [城市天气](https://dev.qweather.com/docs/api/weather/)
 *
 * 城市天气预报提供包括中国 3000+ 市县区在内的全球 20万+ 城市的天气预报，
 * 支持实时天气、最多 30 天预报及最多 7 天逐小时天气预报。
 */
class Weather internal constructor(private val qwSdk: QWSdk) {
    /**
     * [实时天气](https://dev.qweather.com/docs/api/weather/weather-now/)
     *
     * 获取中国 3000+ 市县区和海外 20 万个城市实时天气数据，包括实时温度、体感温度、风力风向、相对湿度、大气压强、降水量、
     * 能见度、露点温度、云量等。
     *
     * **实况数据均为近实时数据，相比真实的物理世界有 5-20 分钟的延迟，请根据实况数据中的 obsTime 确定数据对应的准确时间。**
     *
     * @param location 需要查询地区的 [LocationID] 或以英文逗号分隔的经度,纬度坐标 [Coordinate] ，
     * LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param unit 数据单位设置，可选值包括unit=m（公制单位，默认）和unit=i（英制单位）。更多选项和说明参考
     * [度量衡单位](https://dev.qweather.com/docs/resource/unit) 。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    @Throws(IllegalStateException::class)
    suspend fun now(
        location: Location,
        unit: QWSdk.Units = QWSdk.Units.M,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<WeatherNow> = apiCatching {
        check(location is LocationID || location is Coordinate) { "无效类型，当前仅支持Coordinate或LocationID" }
        qwSdk.client.get("weather/now") {
            url {
                parameter("location", location.location)
                parameter("lang", lang)
                parameter("unit", unit)
            }
        }.body()
    }

    /**
     * [每日天气预报](https://dev.qweather.com/docs/api/weather/weather-daily-forecast/)
     *
     * 每日天气预报API，提供全球城市未来 3-30 天天气预报，包括：日出日落、月升月落、最高最低温度、天气白天和夜间状况、
     * 风力、风速、风向、相对湿度、大气压强、降水量、露点温度、紫外线强度、能见度等。
     *
     * @param location 需要查询地区的 [LocationID] 或以英文逗号分隔的经度,纬度坐标 [Coordinate] ，
     * LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param unit 数据单位设置，可选值包括unit=m（公制单位，默认）和unit=i（英制单位）。更多选项和说明参考
     * [度量衡单位](https://dev.qweather.com/docs/resource/unit) 。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    @Throws(IllegalStateException::class)
    suspend fun daily(
        days: Day,
        location: Location,
        unit: QWSdk.Units = QWSdk.Units.M,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<WeatherDaily> = apiCatching {
        check(location is LocationID || location is Coordinate) { "无效类型，当前仅支持Coordinate或LocationID" }
        val standardRange = qwSdk.apiPlan.isStandard()
        val freeRange = qwSdk.apiPlan.isFree() && (Day.Day3 == days || Day.Day7 == days)
        check(standardRange || freeRange) { "无效的时间范围，请参考：https://dev.qweather.com/docs/finance/subscription/#comparison" }
        qwSdk.client.get("weather/${days.range}") {
            parameter("location", location.location)
            parameter("lang", lang)
            parameter("unit", unit)
        }.body()
    }

    /**
     * [逐小时天气预报](https://dev.qweather.com/docs/api/weather/weather-hourly-forecast/)
     *
     * 逐小时天气预报 API，提供全球城市未来 24-168 小时逐小时天气预报，包括：温度、天气状况、风力、风速、风向、相对湿度、
     * 大气压强、降水概率、露点温度、云量。
     *
     * @param location 需要查询地区的 [LocationID] 或以英文逗号分隔的经度,纬度坐标 [Coordinate] ，
     * LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param unit 数据单位设置，可选值包括unit=m（公制单位，默认）和unit=i（英制单位）。更多选项和说明参考
     * [度量衡单位](https://dev.qweather.com/docs/resource/unit) 。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    suspend fun hourly(
        hours: Hour,
        location: Location,
        unit: QWSdk.Units = QWSdk.Units.M,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<WeatherHourly> = apiCatching {
        check(location is LocationID || location is Coordinate) { "无效类型，当前仅支持Coordinate或LocationID" }
        val standardRange = qwSdk.apiPlan.isStandard()
        val freeRange = qwSdk.apiPlan.isFree() && Hour.Hour24 == hours
        check(standardRange || freeRange) { "无效的时间范围，请参考：https://dev.qweather.com/docs/finance/subscription/#comparison" }
        qwSdk.client.get("weather/${hours.range}") {
            url {
                parameter("location", location.location)
                parameter("lang", lang)
                parameter("unit", unit)
            }
        }.body()
    }
}