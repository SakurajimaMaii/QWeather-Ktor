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

package com.qwsdk.vastgui.api

import com.qwsdk.vastgui.QWSdk
import com.qwsdk.vastgui.entity.astronomy.Moon
import com.qwsdk.vastgui.entity.astronomy.SolarElevationAngle
import com.qwsdk.vastgui.entity.astronomy.Sun
import com.qwsdk.vastgui.utils.*
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * [天文](https://dev.qweather.com/docs/api/astronomy/)
 *
 * 天文API提供了全球任意地点未来60天的日出日落、太阳高度角、月升月落和月相数据。
 */
class Astronomy internal constructor(private val qwSdk: QWSdk) {
    /**
     * [日出日落](https://dev.qweather.com/docs/api/astronomy/sunrise-sunset/)
     *
     * 获取未来 60 天全球任意地点日出日落时间。
     *
     * 在 [逐天预报API][Weather.daily] 中，也会返回对应天数的日出日落、月升月落和月相数据。
     *
     * @param location 需要查询地区的 [LocationID] 或以英文逗号分隔的经度,纬度坐标 [Coordinate] ，
     * LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param date 选择日期，最多可选择未来60天（包含今天）的数据。日期格式为 yyyyMMdd，例如
     * date=20200531 。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    suspend fun sun(
        location: Location, date: String, lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<Sun> = apiCatching {
        check(location is LocationID || location is Coordinate) { "无效类型，当前仅支持Coordinate或LocationID" }
        DateUtil(date).verifyYMD()
        qwSdk.client.get("astronomy/sun") {
            url {
                parameter("location", location.location)
                parameter("date", date)
                parameter("lang", lang)
            }
        }.body()
    }

    /**
     * [月升月落和月相](https://dev.qweather.com/docs/api/astronomy/moon-and-moon-phase/)
     *
     * 获取未来60天全球城市月升月落和逐小时的月相数据。
     *
     * 月相已考虑南北半球的差异，不需要再进行转换。
     *
     * @param location 需要查询地区的 [LocationID] 或以英文逗号分隔的经度,纬度坐标 [Coordinate] ，
     * LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param date 选择日期，最多可选择未来60天（包含今天）的数据。日期格式为 yyyyMMdd，例如
     * date=20200531 。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    suspend fun moon(
        location: Location, date: String, lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<Moon> = apiCatching {
        check(location is LocationID || location is Coordinate) { "无效类型，当前仅支持Coordinate或LocationID" }
        DateUtil(date).verifyYMD()
        qwSdk.client.get("astronomy/moon") {
            url {
                parameter("location", location.location)
                parameter("date", date)
                parameter("lang", lang)
            }
        }.body()
    }

    /**
     * [太阳高度角](https://dev.qweather.com/docs/api/astronomy/solar-elevation-angle/)
     *
     * 任意时间点的全球太阳高度及方位角。
     *
     * @param location 需要查询地区的以英文逗号分隔的经度,纬度坐标 [Coordinate] 。
     * @param date 查询日期，格式为 yyyyMMdd ，例如 date=20170809 。
     * @param time 查询时间，格式为 HHmm ， 24 时制，例如 time=1230 。
     * @param tz 查询地区所在时区，例如 tz=0800 或 tz=-0530 。
     * @param alt 海拔高度，单位为米，例如 alt=43 。
     */
    suspend fun solarElevationAngle(
        location: Coordinate, date: String, time: String, tz: String, alt: Int
    ): Result<SolarElevationAngle> = apiCatching {
        DateUtil(date).verifyYMD()
        DateUtil(time).verifyHM()
        qwSdk.client.get("astronomy/solar-elevation-angle") {
            url {
                parameter("location", location.location)
                parameter("date", date)
                parameter("time", time)
                parameter("tz", tz)
                parameter("alt", alt)
            }
        }.body()
    }
}