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

package com.qwsdk.vastgui.entity.astronomy

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable

/**
 * [太阳高度角](https://dev.qweather.com/docs/api/astronomy/solar-elevation-angle/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property hourAngle 时角。
 * @property refer 参考 [Refer] 。
 * @property solarAzimuthAngle 太阳方位角，正北顺时针方向角度。
 * @property solarElevationAngle 太阳高度角。
 * @property solarHour 太阳时，HHmm格式。
 */
@Serializable
data class SolarElevationAngle(
    override val code: String,
    val hourAngle: String? = null,
    val refer: Refer = Refer(),
    val solarAzimuthAngle: String? = null,
    val solarElevationAngle: String? = null,
    val solarHour: String? = null
) : QWSdkResponse