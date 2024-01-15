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
import cn.rtast.qwsdk.entity.air.daily.AirDailyBean
import cn.rtast.qwsdk.entity.air.now.AirBean
import cn.rtast.qwsdk.utils.Coordinate
import cn.rtast.qwsdk.utils.get
import cn.rtast.qwsdk.utils.makeParam
import com.google.gson.Gson
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class Air {
    /** [实时空气质量](https://dev.qweather.com/docs/api/air/air-now/) */
    suspend fun now(
        location: String,
        lang: QWeather.Lang = QWeather.Lang.ZH
    ): Result<AirBean> = runCatching {
        QWeather.client.get("air/now") {
            url {
                parameter("location", location)
                parameter("lang", lang.toString())
            }
        }.body()
    }

    /** [实时空气质量](https://dev.qweather.com/docs/api/air/air-now/) */
    suspend fun now(
        location: Coordinate,
        lang: QWeather.Lang = QWeather.Lang.ZH
    ): Result<AirBean> = now(location(), lang)

    /** [空气质量每日预报](https://dev.qweather.com/docs/api/air/air-daily-forecast/) */
    suspend fun daily(
        location: String,
        lang: QWeather.Lang = QWeather.Lang.ZH
    ): Result<AirDailyBean> = runCatching {
        QWeather.client.get("air/5d") {
            url {
                parameter("location", location)
                parameter("lang", lang.toString())
            }
        }.body()
    }

    /** [空气质量每日预报](https://dev.qweather.com/docs/api/air/air-daily-forecast/) */
    suspend fun daily(
        location: Coordinate,
        lang: QWeather.Lang = QWeather.Lang.ZH
    ): Result<AirDailyBean> = daily(location(), lang)
}