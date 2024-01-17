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

package com.qwsdk.vastgui.entity.airbeta.now

import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.ApiStatus.Experimental

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/1/16

/**
 * [实时空气质量(beta)](https://dev.qweather.com/docs/api/air-quality/air-now/)
 *
 * 因为该 API 目前处于实验性，因此具体字段说明请参考链接。
 */
@Experimental
@Serializable
data class AirBetaNowBean(
    val aqi: List<Aqi> = emptyList(),
    override val code: String,
    val fxLink: String? = null,
    val pollutant: List<Pollutant> = emptyList(),
    val source: List<String> = emptyList(),
    val station: List<Station> = emptyList(),
    val updateTime: String? = null
) : QWSdkResponse {
    @Serializable
    data class Aqi(
        val category: String,
        val code: String,
        val color: String,
        val level: String,
        val name: String,
        val primaryPollutant: PrimaryPollutant? = null,
        val value: String
    )

    @Serializable
    data class Pollutant(
        val code: String,
        val concentration: Concentration,
        val fullName: String,
        val name: String,
        val subIndex: SubIndex? = null
    )

    @Serializable
    data class Station(
        val id: String,
        val name: String
    )

    @Serializable
    data class SubIndex(
        val category: String,
        val value: String
    )

    @Serializable
    data class Concentration(
        val unit: String,
        val value: String
    )

    @Serializable
    data class PrimaryPollutant(
        val code: String? = null,
        val fullName: String? = null,
        val name: String? = null
    )
}