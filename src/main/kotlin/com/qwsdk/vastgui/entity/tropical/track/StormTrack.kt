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

package com.qwsdk.vastgui.entity.tropical.track

import com.qwsdk.vastgui.entity.Refer
import com.qwsdk.vastgui.utils.QWSdkResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

/**
 * [台风实况和路径](https://dev.qweather.com/docs/api/tropical-cyclone/storm-track/)
 *
 * @property code 请参考 [状态码](https://dev.qweather.com/docs/resource/status-code/) 。
 * @property fxLink 当前数据的响应式页面，便于嵌入网站或应用。
 * @property isActive 是否为活跃台风。1 活跃台风，0 停编。
 * @property now 参考 [Now] ，如果台风已经停止，即 [isActive]=0 ，接口中的 now 字段将不再返回数据 。
 * @property refer 参考 [Refer] 。
 * @property track 参考 [Track] 。
 * @property updateTime 当前 [API的最近更新时间](https://dev.qweather.com/docs/resource/glossary/#update-time) 。
 */
@Serializable
data class StormTrack(
    override val code: String,
    val fxLink: String? = null,
    val isActive: String? = null,
    val now: Now? = null,
    val refer: Refer = Refer(),
    val track: List<Track> = emptyList(),
    val updateTime: String? = null
) : QWSdkResponse {
    /**
     * [台风实况和路径](https://dev.qweather.com/docs/api/tropical-cyclone/storm-track/)
     *
     * @property lat 台风所处纬度。
     * @property lon 台风所处经度。
     * @property move360 台风移动方位 360 度方向。
     * @property moveDir 台风移动方位。
     * @property moveSpeed 台风移动速度。
     * @property pressure 台风中心气压。
     * @property pubTime 台风信息发布时间。
     * @property type 台风类型。
     * @property windRadius30 台风 7 级风圈半径 。
     * @property windRadius50 台风 10 级风圈半径 。
     * @property windRadius64 台风 12 级风圈半径 。
     * @property windSpeed 台风附近最大风速。
     */
    @Serializable
    data class Now(
        val lat: String,
        val lon: String,
        val move360: String,
        val moveDir: String,
        val moveSpeed: String,
        val pressure: String,
        val pubTime: String,
        val type: String,
        val windRadius30: WindRadius30? = null,
        val windRadius50: WindRadius30? = null,
        val windRadius64: WindRadius30? = null,
        val windSpeed: String
    )

    /**
     * [台风实况和路径](https://dev.qweather.com/docs/api/tropical-cyclone/storm-track/)
     *
     * @property lat 台风所处纬度。
     * @property lon 台风所处经度。
     * @property move360 台风移动方位 360 度方向。
     * @property moveDir 台风移动方位。
     * @property moveSpeed 台风移动速度。
     * @property pressure 台风中心气压。
     * @property time 当前台风信息发布时间。
     * @property type 台风类型。
     * @property windRadius30 台风 7 级风圈半径 。
     * @property windRadius50 台风 10 级风圈半径 。
     * @property windRadius64 台风 12 级风圈半径 。
     * @property windSpeed 台风附近最大风速。
     */
    @Serializable
    @OptIn(ExperimentalSerializationApi::class)
    data class Track(
        val lat: String,
        val lon: String,
        val move360: String,
        val moveDir: String,
        val moveSpeed: String,
        val pressure: String,
        val time: String,
        val type: String,
        val windRadius30: WindRadius30? = null,
        val windRadius50: WindRadius30? = null,
        val windRadius64: WindRadius30? = null,
        val windSpeed: String
    )

    /**
     * [台风实况和路径](https://dev.qweather.com/docs/api/tropical-cyclone/storm-track/)
     *
     * @property neRadius 台风风圈东北半径。
     * @property nwRadius 台风风圈西北半径。
     * @property seRadius 台风风圈东南半径。
     * @property swRadius 台风风圈西南半径。
     */
    @Serializable
    data class WindRadius30(
        val neRadius: String,
        val nwRadius: String,
        val seRadius: String,
        val swRadius: String
    )
}