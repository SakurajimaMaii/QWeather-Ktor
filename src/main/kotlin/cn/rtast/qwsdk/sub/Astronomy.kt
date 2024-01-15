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

package cn.rtast.qwsdk.sub

import cn.rtast.qwsdk.QWeather
import cn.rtast.qwsdk.entity.astronomy.MoonBean
import cn.rtast.qwsdk.entity.astronomy.SolarElevationAngleBean
import cn.rtast.qwsdk.entity.astronomy.SunBean
import cn.rtast.qwsdk.utils.Coordinate
import cn.rtast.qwsdk.utils.DateUtil
import cn.rtast.qwsdk.utils.get
import cn.rtast.qwsdk.utils.makeParam
import com.google.gson.Gson
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class Astronomy {
    /** [日出日落](https://dev.qweather.com/docs/api/astronomy/sunrise-sunset/) */
    suspend fun sun(
        location: String, date: String, lang: QWeather.Lang = QWeather.Lang.ZH
    ): Result<SunBean> = runCatching {
        DateUtil(date).verifyYMD()
        QWeather.client.get("astronomy/sun") {
            url {
                parameter("location", location)
                parameter("date", date)
                parameter("lang", lang.toString())
            }
        }.body()
    }

    /** [日出日落](https://dev.qweather.com/docs/api/astronomy/sunrise-sunset/) */
    suspend fun sun(
        location: Coordinate, date: String, lang: QWeather.Lang = QWeather.Lang.ZH
    ): Result<SunBean> = sun(location(), date, lang)

    /**
     * [月升月落和月相](https://dev.qweather.com/docs/api/astronomy/moon-and-moon-phase/)
     */
    suspend fun moon(
        location: String, date: String, lang: QWeather.Lang = QWeather.Lang.ZH
    ): Result<MoonBean> = runCatching {
        DateUtil(date).verifyYMD()
        QWeather.client.get("astronomy/moon") {
            url {
                parameter("location", location)
                parameter("date", date)
                parameter("lang", lang.toString())
            }
        }.body()
    }

    /**
     * [月升月落和月相](https://dev.qweather.com/docs/api/astronomy/moon-and-moon-phase/)
     */
    suspend fun moon(
        location: Coordinate, date: String, lang: QWeather.Lang = QWeather.Lang.ZH
    ): Result<MoonBean> = moon(location(), date, lang)

    /**
     * [太阳高度角](https://dev.qweather.com/docs/api/astronomy/solar-elevation-angle/)
     */
    suspend fun solarElevationAngle(
        location: Coordinate, date: String, time: String, tz: String, alt: Int
    ): Result<SolarElevationAngleBean> = runCatching {
        DateUtil(date).verifyYMD()
        DateUtil(time).verifyHM()
        QWeather.client.get("astronomy/solar-elevation-angle") {
            url {
                parameter("location", location())
                parameter("date", date)
                parameter("time", time)
                parameter("tz", tz)
                parameter("alt", alt)
            }
        }.body()
    }
}