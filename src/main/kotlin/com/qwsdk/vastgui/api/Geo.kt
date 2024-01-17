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
import com.qwsdk.vastgui.entity.geo.lookup.GeoLookup
import com.qwsdk.vastgui.entity.geo.poi.GeoPoi
import com.qwsdk.vastgui.entity.geo.poi.range.GeoPoiRange
import com.qwsdk.vastgui.entity.geo.top.GeoTop
import com.qwsdk.vastgui.utils.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

/**
 * [GeoAPI](https://dev.qweather.com/docs/api/geoapi/)
 *
 * 和风天气 GeoAPI 提供全球地理位位置、全球城市搜索服务，支持经纬度坐标反查、多语言、模糊搜索等功能。
 *
 * 天气数据是基于地理位置的数据，因此获取天气之前需要先知道具体的位置信息。和风天气提供一个功能强大
 * 的位置信息搜索 API 服务： GeoAPI 。通过 GeoAPI ，你可获取到需要查询城市或 POI 的基本信息，包
 * 括查询地区的 Location ID（你需要这个 ID 去查询天气），多语言名称、经纬度、时区、海拔、Rank 值、
 * 归属上级行政区域、所在行政区域等。
 *
 * 除此之外，GeoAPI还可以帮助你：
 * - 避免重名城市的困扰
 * - 支持名称模糊搜索
 * - 在你的应用或网站中根据用户输入的名称返回多个城市结果，便于用户选择准确的城市并返回该城市天气
 * - 在你的应用或网站中展示热门城市
 * - 不需要维护城市列表，城市信息更新实时获取
 */
class Geo internal constructor(private val qwSdk: QWSdk) {
    /**
     * [城市搜索](https://dev.qweather.com/docs/api/geoapi/city-lookup/)
     *
     * 城市搜索API提供全球地理位位置、全球城市搜索服务，支持经纬度坐标反查、多语言、模糊搜索等功能。
     *
     * 天气数据是基于地理位置的数据，因此获取天气之前需要先知道具体的位置信息。使用城市搜索，可获取
     * 到该城市的基本信息，包括城市的Location ID（你需要这个ID去查询天气），多语言名称、经纬度、
     * 时区、海拔、Rank值、归属上级行政区域、所在行政区域等。
     *
     * 另外，城市搜索也可以帮助你在你的APP中实现模糊搜索，用户只需要输入1-2个字即可获得结果。
     *
     * @param location 需要查询地区的名称，支持文字 [Name] 、以英文逗号分隔的经度，纬度坐标
     * [Coordinate] 、[LocationID] 或 [Adcode]（仅限中国城市) 。
     * @param adm 城市的上级行政区划，可设定只在某个行政区划范围内进行搜索，用于排除重名城市
     * 或对结果进行过滤。例如 adm=beijing
     * @param range 搜索范围，可设定只在某个国家或地区范围内进行搜索，国家和地区名称需使用
     * [ISO 3166 所定义的国家代码][QWSdk.CountryCode] 。如果不设置此参数，搜索范围将在
     * 所有城市。例如 range=cn 。
     * @param number 返回结果的数量，取值范围1-20，默认返回10个结果。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     * @throws IllegalStateException 当 [number] 未在 0-20 范围内会抛出该异常。
     */
    @Throws(IllegalStateException::class)
    suspend fun cityLookup(
        location: Location,
        adm: String? = null,
        range: QWSdk.CountryCode = QWSdk.CountryCode.CN,
        number: Int = 10,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<GeoLookup> = apiCatching {
        check(number in 1..20) { "Invalid Range: $number, please choose from 1-20!" }
        qwSdk.client.get {
            url {
                url("https://geoapi.qweather.com/v2/city/lookup")
                parameter("key", qwSdk.apiKey)
                parameter("location", location.location)
                parameter("adm", adm)
                parameter("range", range)
                parameter("number", number)
                parameter("lang", lang)
            }
        }.body()
    }

    /**
     * [热门城市查询](https://dev.qweather.com/docs/api/geoapi/top-city/)
     *
     * 获取全球各国热门城市列表。
     *
     * @param range 搜索范围，可设定只在某个国家或地区范围内进行搜索，国家和地区名称需使用
     * [ISO 3166 所定义的国家代码][QWSdk.CountryCode] 。如果不设置此参数，搜索范围将在
     * 所有城市。例如 range=cn 。
     * @param number 返回结果的数量，取值范围1-20，默认返回10个结果。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     * @throws IllegalStateException 当 [number] 未在 0-20 范围内会抛出该异常。
     */
    @Throws(IllegalStateException::class)
    suspend fun topCity(
        range: QWSdk.CountryCode = QWSdk.CountryCode.CN,
        number: Int = 10,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<GeoTop> = apiCatching {
        check(number in 1..20) { "Invalid Range: $number, please choose from 1-20!" }
        qwSdk.client.get {
            url {
                url("https://geoapi.qweather.com/v2/city/top")
                parameter("key", qwSdk.apiKey)
                parameter("range", range)
                parameter("number", number)
                parameter("lang", lang)
            }
        }.body()
    }

    /**
     * [POI搜索](https://dev.qweather.com/docs/api/geoapi/poi-lookup/)
     *
     * 使用关键字和坐标查询 POI 信息（景点、火车站、飞机场、港口等）。
     *
     * @param location 需要查询地区的名称 [Name] ，支持文字、以英文逗号分隔的经度，纬度坐标
     * [Coordinate] 、[LocationID] 或 [Adcode]（仅限中国城市) 。
     * @param type POI类型，可选择搜索某一类型的 POI 。
     * @param city 选择 POI 所在城市，可设定只搜索在特定城市内的 POI 信息。城市名称可以是
     * [Name] 或城市的 [LocationID] 。默认不限制特定城市。
     * @param number 返回结果的数量，取值范围1-20，默认返回10个结果。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     * @throws IllegalStateException 当 [number] 未在 0-20 范围内会抛出该异常。
     */
    @Throws(IllegalStateException::class)
    suspend fun poiLookup(
        location: Location,
        type: QWSdk.POIType,
        city: String? = null,
        number: Int = 10,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<GeoPoi> = apiCatching {
        check(number in 1..20) { "Invalid Range: $number, please choose from 1-20!" }
        qwSdk.client.get {
            url {
                url("https://geoapi.qweather.com/v2/poi/lookup")
                parameter("key", qwSdk.apiKey)
                parameter("location", location.location)
                parameter("type", type)
                parameter("city", city)
                parameter("number", number)
                parameter("lang", lang)
            }
        }.body()
    }

    /**
     * [POI范围搜索](https://dev.qweather.com/docs/api/geoapi/poi-range/)
     *
     * 提供指定区域范围内查询所有POI信息。
     *
     * @param location 需要查询地区的以英文逗号分隔的经度,纬度坐标 [Coordinate] 。
     * @param type POI类型，可选择搜索某一类型的 POI 。
     * @param radius 搜索范围，可设置搜索半径，取值范围 1-50 ，单位：公里。默认 5 公里。
     * @param number 返回结果的数量，取值范围 1-20 ，默认返回 10 个结果。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     * @throws IllegalStateException 当 [number] 或 [radius] 未在规范范围内会抛出该异常。
     */
    @Throws(IllegalStateException::class)
    suspend fun poiRange(
        location: Coordinate,
        type: QWSdk.POIType,
        radius: Int = 5,
        number: Int = 10,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<GeoPoiRange> = apiCatching {
        check(number in 1..20) { "Invalid Range: $number, please choose from 1-20!" }
        check(radius in 1..50) { "Invalid Radius: $radius, please choose from 1-50!" }
        qwSdk.client.get {
            url("https://geoapi.qweather.com/v2/poi/range")
            parameter("key", qwSdk.apiKey)
            parameter("location", location.location)
            parameter("type", type)
            parameter("radius", radius)
            parameter("number", number)
            parameter("lang", lang)
        }.body()
    }
}
