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

package com.qwsdk.vastgui.entity.geo.poi

import com.qwsdk.vastgui.utils.GeoLocationID
import com.qwsdk.vastgui.utils.LocationID
import kotlinx.serialization.Serializable

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/1/16

/**
 * [POI搜索](https://dev.qweather.com/docs/api/geoapi/poi-lookup/)
 *
 * @property adm1 POI（兴趣点）所属一级行政区域。
 * @property adm2 POI（兴趣点）的上级行政区划名称。
 * @property country POI（兴趣点）所属国家名称。
 * @property fxLink 该地区的天气预报网页链接，便于嵌入你的网站或应用。
 * @property id POI（兴趣点）ID。
 * @property isDst POI（兴趣点）是否当前处于 [夏令时](https://dev.qweather.com/docs/resource/glossary/#daylight-saving-time)。
 * 1 表示当前处于夏令时， 0 表示当前不是夏令时。
 * @property lat POI（兴趣点）纬度。
 * @property lon POI（兴趣点）经度。
 * @property name POI（兴趣点）名称。
 * @property rank [地区评分](https://dev.qweather.com/docs/resource/glossary/#rank) 。
 * @property type POI（兴趣点）的属性。
 * @property tz POI（兴趣点）所在 [时区](https://dev.qweather.com/docs/resource/glossary/#timezone) 。
 * @property utcOffset 地区/城市目前与 UTC 时间偏移的小时数。
 */
@Serializable
data class Poi(
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