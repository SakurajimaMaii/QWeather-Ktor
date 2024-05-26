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
import com.qwsdk.vastgui.entity.grid.daily.GridDaily
import com.qwsdk.vastgui.entity.grid.hourly.GridHourly
import com.qwsdk.vastgui.entity.grid.now.GridNow
import com.qwsdk.vastgui.utils.Coordinate
import com.qwsdk.vastgui.utils.Day
import com.qwsdk.vastgui.utils.Hour
import com.qwsdk.vastgui.utils.apiCatching
import io.ktor.client.call.*
import io.ktor.client.request.*

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/1/16

/**
 * [格点天气](https://dev.qweather.com/docs/api/grid-weather/)
 *
 * 以经纬度为基准的全球高精度、公里级、格点化天气预报产品，包括任意经纬度的实时天气和天气预报。
 * 1. 提示：格点天气的空间分辨率为 3-5 公里。
 * 2. 注意：格点天气是一种数值预报，由卫星、气象雷达等数据通过模型算法运算而来，它可能受到许多
 *    因素的影响，如山脉、建筑物、人类活动、大气变化等。格点天气与观测站数据会有所不同，不可直接对比。
 */
class Grid internal constructor(private val qwSdk: QWSdk) {
    /**
     * [格点实时天气](https://dev.qweather.com/docs/api/grid-weather/grid-weather-now/)
     *
     * 基于全球任意坐标的高精度实时天气，精确到3-5公里范围，包括：温度、湿度、大气压、天气状况、风力、风向等。
     *
     * @param location 需要查询地区的以英文逗号分隔的经度,纬度坐标 [Coordinate] 。
     * @param unit 数据单位设置，可选值包括unit=m（公制单位，默认）和unit=i（英制单位）。更多选项和说明参考
     * [度量衡单位](https://dev.qweather.com/docs/resource/unit) 。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    suspend fun now(
        location: Coordinate,
        unit: QWSdk.Units = QWSdk.Units.M,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<GridNow> = apiCatching {
        qwSdk.client.get("grid-weather/now") {
            url {
                parameter("location", location.location)
                parameter("lang", lang)
                parameter("unit", unit)
            }
        }.body()
    }

    /**
     * [格点每日天气预报](https://dev.qweather.com/docs/api/grid-weather/grid-weather-daily-forecast/)
     *
     * 基于全球任意坐标的高精度每日天气预报，精确到 3-5 公里范围，包括温度、湿度、大气压、天气状况、风力、风向等。
     *
     * @param location 需要查询地区的以英文逗号分隔的经度,纬度坐标 [Coordinate] 。
     * @param unit 数据单位设置，可选值包括unit=m（公制单位，默认）和unit=i（英制单位）。更多选项和说明参考
     * [度量衡单位](https://dev.qweather.com/docs/resource/unit) 。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    suspend fun daily(
        days: Day,
        location: Coordinate,
        unit: QWSdk.Units = QWSdk.Units.M,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<GridDaily> = apiCatching {
        check(Day.Day3 == days || Day.Day7 == days) { "无效的时间范围，请参考：https://dev.qweather.com/docs/finance/subscription/#comparison" }
        qwSdk.client.get("grid-weather/${days.range}") {
            parameter("location", location.location)
            parameter("lang", lang)
            parameter("unit", unit)
        }.body()
    }

    /**
     * [格点逐小时天气预报](https://dev.qweather.com/docs/api/grid-weather/grid-weather-hourly-forecast/)
     *
     * 基于全球任意坐标的高精度逐小时天气预报，精确到 3-5 公里范围，包括温度、湿度、大气压、天气状况、风力、风向等。
     *
     * @param location 需要查询地区的以英文逗号分隔的经度,纬度坐标 [Coordinate] 。
     * @param unit 数据单位设置，可选值包括unit=m（公制单位，默认）和unit=i（英制单位）。更多选项和说明参考
     * [度量衡单位](https://dev.qweather.com/docs/resource/unit) 。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    suspend fun hourly(
        hours: Hour,
        location: Coordinate,
        unit: QWSdk.Units = QWSdk.Units.M,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<GridHourly> = apiCatching {
        val standardRange = qwSdk.apiPlan.isStandard() && (Hour.Hour24 == hours || Hour.Hour72 == hours)
        val freeRange = qwSdk.apiPlan.isFree() && Hour.Hour24 == hours
        check(standardRange || freeRange) { "无效的时间范围，请参考：https://dev.qweather.com/docs/finance/subscription/#comparison" }
        qwSdk.client.get("grid-weather/${hours.range}") {
            parameter("location", location.location)
            parameter("lang", lang)
            parameter("unit", unit)
        }.body()
    }
}