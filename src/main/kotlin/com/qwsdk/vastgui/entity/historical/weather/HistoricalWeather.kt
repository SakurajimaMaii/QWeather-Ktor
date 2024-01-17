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

package com.qwsdk.vastgui.entity.historical.weather

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [天气时光机](https://dev.qweather.com/docs/api/time-machine/time-machine-weather/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property fxLink 当前数据的响应式页面，便于嵌入网站或应用。
 * @property refer 参考 [Refer] 。
 * @property weatherDaily 参考 [weatherDaily] 。
 * @property weatherHourly 参考 [WeatherHourly] 。
 */
@Serializable
data class HistoricalWeather(
    override val code: String,
    val fxLink: String? = null,
    val refer: Refer = Refer(),
    val weatherDaily: WeatherDaily? = null,
    val weatherHourly: List<WeatherHourly> = emptyList()
) : QWSdkResponse {
    /**
     * [天气时光机](https://dev.qweather.com/docs/api/time-machine/time-machine-weather/)
     *
     * @property date 当天日期。
     * @property humidity 当天每小时相对湿度，百分比数值。
     * @property moonPhase 当天 [月相名称](https://dev.qweather.com/docs/resource/sun-moon-info/#moon-phase) 。
     * @property moonrise 当天 [月升时间](https://dev.qweather.com/docs/resource/sun-moon-info/#moonrise-and-moonset) ，可能为空。
     * @property moonset 当天 [月落时间](https://dev.qweather.com/docs/resource/sun-moon-info/#moonrise-and-moonset) ，可能为空
     * @property precip 当天总降水量，默认单位：毫米。
     * @property pressure 大气压强，默认单位：百帕。
     * @property sunrise 当天 [日出时间](https://dev.qweather.com/docs/api/time-machine/time-machine-weather/) ，在高纬度地区可能为空。
     * @property sunset 当天 [日落时间](https://dev.qweather.com/docs/api/time-machine/time-machine-weather/) ，在高纬度地区可能为空。
     * @property tempMax 当天最高温度。
     * @property tempMin 当天最低温度。
     */
    @Serializable
    data class WeatherDaily(
        val date: String,
        val humidity: String,
        val moonPhase: String,
        val moonrise: String,
        val moonset: String,
        val precip: String,
        val pressure: String,
        val sunrise: String,
        val sunset: String,
        val tempMax: String,
        val tempMin: String
    )

    /**
     * [天气时光机](https://dev.qweather.com/docs/api/time-machine/time-machine-weather/)
     *
     * @property humidity 当天每小时相对湿度，百分比数值。
     * @property icon 当天每小时天气状况的 [图标代码](https://dev.qweather.com/docs/resource/icons/) ，
     * 另请参考 [天气图标项目](https://icons.qweather.com/) 。
     * @property precip 当天总降水量，默认单位：毫米。
     * @property pressure 大气压强，默认单位：百帕。
     * @property temp 当天每小时温度，默认单位：摄氏度。
     * @property text 当天每小时天气状况的文字描述，包括阴晴雨雪等天气状态的描述。
     * @property time 当天时间。
     * @property wind360 当天每小时 [风向](https://dev.qweather.com/docs/resource/wind-info/#wind-direction) 360 角度。
     * @property windDir 当天每小时 [风向](https://dev.qweather.com/docs/resource/wind-info/#wind-direction) 。
     * @property windScale 当天每小时 [风力等级](https://dev.qweather.com/docs/api/time-machine/time-machine-weather/) 。
     * @property windSpeed 当天每小时 [风速](https://dev.qweather.com/docs/resource/wind-info/#wind-speed) ，公里/小时。
     */
    @Serializable
    data class WeatherHourly(
        val humidity: String,
        val icon: String,
        val precip: String,
        val pressure: String,
        val temp: String,
        val text: String,
        val time: String,
        val wind360: String,
        val windDir: String,
        val windScale: String,
        val windSpeed: String
    )
}