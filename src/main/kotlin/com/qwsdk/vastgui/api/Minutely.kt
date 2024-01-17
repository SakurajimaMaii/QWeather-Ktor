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
import com.qwsdk.vastgui.entity.minutely.RainMinutely
import com.qwsdk.vastgui.utils.Coordinate
import com.qwsdk.vastgui.utils.apiCatching
import io.ktor.client.call.*
import io.ktor.client.request.*

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/1/16

/**
 * [分钟预报](https://dev.qweather.com/docs/api/minutely/)
 *
 * 分钟级降水 API （临近预报）支持中国 1 公里精度的分钟级降雨预报数据，
 * 为每一分钟的降雨进行精准预测。
 */
class Minutely internal constructor(private val qwSdk: QWSdk) {
    /**
     * [分钟级降水](https://dev.qweather.com/docs/api/minutely/minutely-precipitation/)
     *
     * 分钟级降水 API （临近预报）支持中国1公里精度的未来 2 小时每 5 分钟降雨预报数据。
     *
     * @param location 需要查询地区的以英文逗号分隔的经度,纬度坐标 [Coordinate] 。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    suspend fun rain(
        location: Coordinate,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<RainMinutely> = apiCatching {
        qwSdk.client.get("minutely/5m") {
            url {
                parameter("location", location.location)
                parameter("lang", lang)
            }
        }.body()
    }
}