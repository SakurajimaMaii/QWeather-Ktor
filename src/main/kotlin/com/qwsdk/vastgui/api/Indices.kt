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
import com.qwsdk.vastgui.entity.indices.Indices
import com.qwsdk.vastgui.utils.*
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * [天气指数](https://dev.qweather.com/docs/api/indices/)
 *
 * 天气生活指数包括洗车指数、穿衣指数、感冒指数、过敏指数、紫外线指数、
 * 钓鱼指数等数据。天气指数支持中国3000+个市县区和海外 15 万个城市天气预报。
 */
class Indices internal constructor(private val qwSdk: QWSdk) {
    private suspend fun indices(
        days: String,
        location: Location,
        lang: QWSdk.Lang = QWSdk.Lang.ZH,
        vararg types: QWSdk.IndicesType = arrayOf(QWSdk.IndicesType.ALL),
    ): Result<Indices> = apiCatching {
        check(location is LocationID || location is Coordinate) { "无效类型，当前仅支持Coordinate或LocationID" }
        val typeArray = if (types.contains(QWSdk.IndicesType.ALL)) {
            arrayOf(QWSdk.IndicesType.ALL)
        } else types
        val typeString = typeArray.map { parseIndices(it) }.joinToString(",")
        qwSdk.client.get("indices/$days") {
            url {
                parameter("location", location.location)
                parameter("type", typeString)
                parameter("lang", lang)
            }
        }.body()
    }

    /**
     * [当天生活指数](https://dev.qweather.com/docs/api/indices/indices-forecast/)
     *
     * @param location 需要查询地区的 [LocationID] 或以英文逗号分隔的经度,纬度坐标 [Coordinate] ，
     * LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     * @param types 生活指数的类型ID，包括洗车指数、穿衣指数、钓鱼指数等。可以一次性获取多个类型的生活指数。
     */
    suspend fun indices1d(
        location: Location,
        lang: QWSdk.Lang = QWSdk.Lang.ZH,
        vararg types: QWSdk.IndicesType = arrayOf(QWSdk.IndicesType.ALL)
    ): Result<Indices> = indices("1d", location, lang, *types)

    /**
     * [未来3天生活指数](https://dev.qweather.com/docs/api/indices/indices-forecast/)
     *
     * @param location 需要查询地区的 [LocationID] 或以英文逗号分隔的经度,纬度坐标 [Coordinate] ，
     * LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     * @param types 生活指数的类型ID，包括洗车指数、穿衣指数、钓鱼指数等。可以一次性获取多个类型的生活指数。
     */
    suspend fun indices3d(
        location: Location,
        lang: QWSdk.Lang = QWSdk.Lang.ZH,
        vararg types: QWSdk.IndicesType = arrayOf(QWSdk.IndicesType.ALL)
    ): Result<Indices> = indices("3d", location, lang, *types)
}