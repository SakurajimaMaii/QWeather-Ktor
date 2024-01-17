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

package com.qwsdk.vastgui.entity.warning.list

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.GeoLocationID
import com.qwsdk.vastgui.utils.LocationID
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [天气预警城市列表](https://dev.qweather.com/docs/api/warning/weather-warning-city-list/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property refer 参考 [Refer] 。
 * @property updateTime 当前 [API的最近更新时间](https://dev.qweather.com/docs/resource/glossary/#update-time) 。
 * @property warningLocList 参考 [WarningLoc] 。
 */
@Serializable
data class WarningCityList(
    override val code: String,
    val refer: Refer = Refer(),
    val updateTime: String? = null,
    val warningLocList: List<WarningLoc> = emptyList()
) : QWSdkResponse {
    /**
     * [天气预警城市列表](https://dev.qweather.com/docs/api/warning/weather-warning-city-list/)
     *
     * @property locationId 当前国家预警的 LocationID 。
     */
    @Serializable
    data class WarningLoc(
        val locationId: String
    ) : GeoLocationID {
        override fun getLocationID(): LocationID = LocationID(locationId)
    }
}