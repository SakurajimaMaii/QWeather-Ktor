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

package com.qwsdk.vastgui.entity.geo.lookup

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.GeoLocationID
import com.qwsdk.vastgui.utils.LocationID
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [城市搜索](https://dev.qweather.com/docs/api/geoapi/city-lookup/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property location 参考 [Location] 。
 * @property refer 参考 [Refer] 。
 */
@Serializable
data class GeoLookup(
    override val code: String,
    val location: List<Location> = emptyList(),
    val refer: Refer = Refer()
) : QWSdkResponse {
    /**
     * [城市搜索](https://dev.qweather.com/docs/api/geoapi/city-lookup/)
     *
     * @property adm1 地区/城市所属一级行政区域。
     * @property adm2 地区/城市的上级行政区划名称。
     * @property country 地区/城市所属国家名称。
     * @property fxLink 该地区的天气预报网页链接，便于嵌入你的网站或应用。
     * @property id 地区/城市ID。
     * @property isDst 地区/城市是否当前处于 [夏令时](https://dev.qweather.com/docs/resource/glossary/#daylight-saving-time)。
     * 1 表示当前处于夏令时， 0 表示当前不是夏令时。
     * @property lat 地区/城市纬度。
     * @property lon 地区/城市经度。
     * @property name 地区/城市名称。
     * @property rank [地区评分](https://dev.qweather.com/docs/resource/glossary/#rank) 。
     * @property type 地区/城市的属性。
     * @property tz 地区/城市所在 [时区](https://dev.qweather.com/docs/resource/glossary/#timezone) 。
     * @property utcOffset 地区/城市目前与UTC时间偏移的小时数。
     */
    @Serializable
    data class Location(
        val adm1: String,
        val adm2: String,
        val country: String,
        val fxLink: String,
        val id: String,
        val isDst: String,
        val lat: String,
        val lon: String,
        val name: String,
        val rank: String,
        val type: String,
        val tz: String,
        val utcOffset: String
    ) : GeoLocationID {
        override fun getLocationID(): LocationID = LocationID(id)
    }
}