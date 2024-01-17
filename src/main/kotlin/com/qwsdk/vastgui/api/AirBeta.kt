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
import com.qwsdk.vastgui.entity.airbeta.now.AirBetaNowBean
import com.qwsdk.vastgui.entity.airbeta.station.AirBetaStationBean
import com.qwsdk.vastgui.utils.LocationID
import com.qwsdk.vastgui.utils.apiCatching
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.jetbrains.annotations.ApiStatus.Experimental

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/1/16

/**
 * [空气质量(beta)](https://dev.qweather.com/docs/api/air-quality/)
 *
 * 全球空气质量，可以轻松的获取指定位置和城市的空气质量数据以及官方监测站数据。
 *
 * **注意：全新的空气质量目前处于beta阶段，标准订阅暂不收取费用。数据内容在正式发布后可能会有所不同。**
 */
@Experimental
class AirBeta internal constructor(private val qwSdk: QWSdk) {
    /**
     * [实时空气质量(beta)](https://dev.qweather.com/docs/api/air-quality/air-now/)
     *
     * 全球空气质量实时数据，我们提供了基于各个国家或地区当地规则的 AQI 以及污染物浓度值，
     * 你可以查询指定城市的当前小时实时数据。
     *
     * 推荐阅读 [空气质量信息文档](https://dev.qweather.com/docs/resource/air-info/) ，
     * 以便了解指数类型、污染物、支持的国家等信息。
     *
     * **注意：全新的实时空气质量目前处于beta阶段，标准订阅暂不收取费用。数据内容在正式发布后可能会有所不同。**
     *
     * @param location 需要查询的地区，仅支持 [LocationID] ，LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    @Experimental
    suspend fun now(
        location: LocationID,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<AirBetaNowBean> = apiCatching {
        qwSdk.client.get {
            url("https://${qwSdk.apiPlan.host}/airquality/v1/now/${location.location}")
            parameter("lang", lang)
            parameter("key", qwSdk.apiKey)
        }.body()
    }

    /**
     * [监测站数据(beta)](https://dev.qweather.com/docs/api/air-quality/air-station/)
     *
     * 全球空气质量监测站数据，提供各个国家或地区监测站的污染物浓度值。
     *
     * 警告：监测站数据是实验性数据，仅供参考，可能受到各种因素的影响，
     * 我们无法确保该数据的可用性，请优先使用空气质量指数数据。
     *
     * **注意：全新的实时空气质量目前处于beta阶段，标准订阅暂不收取费用。数据内容在正式发布后可能会有所不同。**
     *
     * @param location 需要查询的地区，仅支持 [LocationID] ，LocationID 可通过 [GeoAPI][Geo] 获取。
     * @param lang 多语言设置，请阅读 [多语言](https://dev.qweather.com/docs/resource/language/)
     * 文档，了解我们的多语言是如何工作、如何设置以及数据是否支持多语言。
     */
    @Experimental
    suspend fun station(
        location: LocationID,
        lang: QWSdk.Lang = QWSdk.Lang.ZH
    ): Result<AirBetaStationBean> = apiCatching {
        qwSdk.client.get {
            url("https://${qwSdk.apiPlan.host}/airquality/v1/station/${location.location}")
            parameter("lang", lang)
            parameter("key", qwSdk.apiKey)
        }.body()
    }
}