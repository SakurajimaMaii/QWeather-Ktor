/*
 * Copyright 2023 RTAkland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qwsdk.vastgui.utils

import com.qwsdk.vastgui.api.Geo

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/1/16

sealed interface Location {
    val location: String
}

internal interface GeoLocationID {
    /**
     * 获取 Geo 的 [LocationID] 。
     *
     * @see Geo
     */
    fun getLocationID(): LocationID
}

/**
 * LocationID 或 locid ，是城市、地区或 POI 点的 ID ，一般由数字或字母
 * +数字组成，是一个地点的唯一标识。 LocationID 可以通过 [定位搜索服务获取][Geo]，
 * 中国地区、热门海外城市、 一些 POI 点的 LocationID 还可以通过
 * [城市列表](https://dev.qweather.com/docs/resource/location-list/) 下载。
 *
 * @property location
 */
@JvmInline
value class LocationID internal constructor(override val location: String) : Location

/**
 * Adcode是中国行政区域编码。
 */
@JvmInline
value class Adcode(override val location: String) : Location

/**
 * 以英文逗号分隔的经度,纬度坐标。
 */
data class Coordinate(private val longitude: Double, private val latitude: Double) : Location {
    override val location: String
        get() = String.format("%.2f,%.2f", longitude, latitude)
}

/**
 * 地区的名称。
 *
 * - 模糊搜索，当 location 传递的为文字时，支持模糊搜索，即用户可以只输入
 *   城市名称一部分进行搜索，最少一个汉字或 2 个字符，结果将按照相关性和
 *   [Rank](https://dev.qweather.com/docs/resource/glossary/#rank)
 *   值进行排列，便于开发或用户进行选择他们需要查看哪个城市的天气。例如
 *   location = bei，将返回与bei相关性最强的若干结果，包括黎巴嫩的贝鲁
 *   特和中国的北京市。
 * - 当 location 传递的为文字时，可能会出现重名的城市，例如陕西省西安市、
 *   吉林省辽源市下辖的西安区和黑龙江省牡丹江市下辖的西安区，此时会根据
 *   [Rank](https://dev.qweather.com/docs/resource/glossary/#rank)
 *   值排序返回所有结果。在这种情况下，可以通过 adm 参数的方式进一步确定
 *   需要查询的城市或地区，例如 location=西安&adm=黑龙江
 */
@JvmInline
value class Name(override val location: String) : Location