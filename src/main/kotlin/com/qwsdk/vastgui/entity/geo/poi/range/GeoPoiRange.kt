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

package com.qwsdk.vastgui.entity.geo.poi.range

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.entity.geo.poi.GeoPoi
import com.qwsdk.vastgui.entity.geo.poi.Poi
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [POI范围搜索](https://dev.qweather.com/docs/api/geoapi/poi-range/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property poi 参考 [GeoPoi] 。
 * @property refer 参考 [Refer] 。
 */
@Serializable
data class GeoPoiRange(
    override val code: String,
    val poi: List<Poi> = emptyList(),
    val refer: Refer = Refer()
) : QWSdkResponse