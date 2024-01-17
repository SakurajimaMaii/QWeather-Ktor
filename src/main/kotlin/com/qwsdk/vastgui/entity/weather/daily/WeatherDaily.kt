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

package com.qwsdk.vastgui.entity.weather.daily

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [每日天气预报](https://dev.qweather.com/docs/api/weather/weather-daily-forecast/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property daily 参考 [Daily] 。
 * @property fxLink 当前数据的响应式页面，便于嵌入网站或应用。
 * @property refer 参考 [Refer] 。
 * @property updateTime 当前 [API的最近更新时间](https://dev.qweather.com/docs/resource/glossary/#update-time) 。
 */
@Serializable
data class WeatherDaily(
    override val code: String,
    val daily: List<Daily> = emptyList(),
    val fxLink: String? = null,
    val refer: Refer = Refer(),
    val updateTime: String? = null
) : QWSdkResponse {
    /**
     * [每日天气预报](https://dev.qweather.com/docs/api/weather/weather-daily-forecast/)
     *
     * @property cloud 云量，百分比数值。可能为空。
     * @property fxDate 预报日期。
     * @property humidity 相对湿度，百分比数值。
     * @property iconDay 预报白天天气状况的 [图标代码](https://dev.qweather.com/docs/resource/icons/) ，
     * 另请参考 [天气图标项目](https://icons.qweather.com/) 。
     * @property iconNight 预报夜间天气状况的 [图标代码](https://dev.qweather.com/docs/resource/icons/) ，
     * 另请参考 [天气图标项目](https://icons.qweather.com/) 。
     * @property moonPhase [月相名称](https://dev.qweather.com/docs/resource/sun-moon-info/#moon-phase) 。
     * @property moonPhaseIcon 月相 [图标代码](https://dev.qweather.com/docs/resource/icons/) ，另请参考
     * [天气图标项目](https://icons.qweather.com/) 。
     * @property moonrise 当天 [月升时间](https://dev.qweather.com/docs/resource/sun-moon-info/#moonrise-and-moonset) ，
     * 可能为空。
     * @property moonset 当天 [月落时间](https://dev.qweather.com/docs/resource/sun-moon-info/#moonrise-and-moonset) ，
     * 可能为空。
     * @property pressure 大气压强，默认单位：百帕。
     * @property sunrise [日出时间](https://dev.qweather.com/docs/resource/sun-moon-info/#sunrise-and-sunset) ，
     * 在高纬度地区可能为空。
     * @property sunset [日落时间](https://dev.qweather.com/docs/resource/sun-moon-info/#sunrise-and-sunset) ，
     * 在高纬度地区可能为空。
     * @property tempMax 预报当天最高温度。
     * @property tempMin 预报当天最低温度。
     * @property textDay 预报白天天气状况文字描述，包括阴晴雨雪等天气状态的描述。
     * @property textNight 预报晚间天气状况文字描述，包括阴晴雨雪等天气状态的描述
     * @property uvIndex 紫外线强度指数。
     * @property vis 能见度，默认单位：公里。
     * @property wind360Day 预报白天 [风向](https://dev.qweather.com/docs/resource/wind-info/#wind-direction) 360角度。
     * @property wind360Night 预报夜间 [风向](https://dev.qweather.com/docs/resource/wind-info/#wind-direction) 360角度。
     * @property windDirDay 预报白天 [风向](https://dev.qweather.com/docs/resource/wind-info/#wind-direction) 。
     * @property windDirNight 预报夜间当天 [风向](https://dev.qweather.com/docs/resource/wind-info/#wind-direction) 。
     * @property windScaleDay 预报白天 [风力等级](https://dev.qweather.com/docs/resource/wind-info/#wind-scale) 。
     * @property windScaleNight 预报夜间 [风力等级](https://dev.qweather.com/docs/resource/wind-info/#wind-scale) 。
     * @property windSpeedDay 预报白天 [风速](https://dev.qweather.com/docs/resource/wind-info/#wind-speed) ，公里/小时。
     * @property windSpeedNight 预报夜间 [风速](https://dev.qweather.com/docs/resource/wind-info/#wind-speed) ，公里/小时。
     */
    @Serializable
    data class Daily(
        val cloud: String? = null,
        val fxDate: String,
        val humidity: String,
        val iconDay: String,
        val iconNight: String,
        val moonPhase: String,
        val moonPhaseIcon: String,
        val moonrise: String? = null,
        val moonset: String? = null,
        val precip: String,
        val pressure: String,
        val sunrise: String? = null,
        val sunset: String? = null,
        val tempMax: String,
        val tempMin: String,
        val textDay: String,
        val textNight: String,
        val uvIndex: String,
        val vis: String,
        val wind360Day: String,
        val wind360Night: String,
        val windDirDay: String,
        val windDirNight: String,
        val windScaleDay: String,
        val windScaleNight: String,
        val windSpeedDay: String,
        val windSpeedNight: String
    )
}