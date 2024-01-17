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

package com.qwsdk.vastgui

import com.ave.vastgui.core.extension.SingletonHolder
import com.qwsdk.vastgui.QWSdk.BasinType.*
import com.qwsdk.vastgui.QWSdk.IndicesType.*
import com.qwsdk.vastgui.QWSdk.POIType.*
import com.qwsdk.vastgui.api.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

/**
 * [QWSdk](https://dev.qweather.com/docs/api/)
 *
 * 和风天气开发服务提供了基于位置的天气数据，包括实况天气、30天预报、
 * 逐小时预报、空气质量AQI，灾害预警、分钟级降水、生活指数等天气数据服务。
 *
 * - [GeoAPI][Geo] 和风天气 GeoAPI 提供全球地理位位置、全球城市搜索服务，
 * 支持经纬度坐标反查、多语言、模糊搜索等功能。
 * - [城市天气][Weather] 城市天气预报提供包括中国 3000+ 市县区在内的全球
 * 20 万+城市的天气预报，支持实时天气、最多 30 天预报及最多 7 天逐小时天气预报。
 * - [分钟预报][Minutely] 分钟级降水 API （临近预报）支持中国 1 公里精度的分钟
 * 级降雨预报数据，为每一分钟的降雨进行精准预测。
 * - [格点天气][Grid] 以经纬度为基准的全球高精度、公里级、格点化天气预报产品，
 * 包括任意经纬度的实时天气和天气预报。
 * - [预警][Warning] 和风天气灾害预警API提供了全球极端天气预警服务，覆盖中国
 * 及全球数十个国家或地区。
 * - [天气指数][Indices] 天气生活指数包括洗车指数、穿衣指数、感冒指数、过敏指数、
 * 紫外线指数、钓鱼指数等数据。天气指数支持中国 3000+ 个市县区和海外 15 万个城市
 * 天气预报。
 * - [空气质量(beta)][AirBeta] 全球空气质量，可以轻松的获取指定位置和城市的空气
 * 质量数据以及官方监测站数据。
 * - [空气质量][Air] 中国 3000+ 市县区及 1700+ 监测站点的空气质量 AQI 数据，包
 * 括空气质量（AQI）实时数据，空气质量未来 5 天预报。
 * - [时光机][TimeMachine] 时光机可以获取最近 10 天的历史天气和空气质量数据。
 * - [热带气旋（台风）][Tropical] 热带气旋（台风）API 提供全球主要海洋流域的
 * 台风信息，包括台风实时位置、等级、气压、风速，还可查询台风路径和台风预报信息。
 * - [海洋数据][Ocean] 海洋数据API提供全球主要港口和城市的潮汐和潮流数据。
 * - [太阳辐射][SolarRadiation] 太阳辐射 API 支持获取全球任意坐标的辐射数据，
 * 包括净太阳辐射，太阳散射辐射和太阳直接辐射。
 * - [天文][Astronomy] 天文API提供了全球任意地点未来 60 天的日出日落、太阳
 * 高度角、月升月落和月相数据。
 *
 * ```kotlin
 * // 获取单例对象
 * val qw = QWSdk
 *     .getInstance(Configuration(Plan.Standard, "<Your-Key>"))
 * ```
 */
@OptIn(ExperimentalSerializationApi::class)
class QWSdk private constructor(private val configuration: Configuration) {

    /**
     * QWSdk 配置
     *
     * @property plan 订阅计划。
     * @property apiKey Key，点击 [项目和KEY](https://dev.qweather.com/docs/configuration/project-and-key/) 了解详情。
     */
    class Configuration(internal val plan: Plan, internal val apiKey: String)

    companion object : SingletonHolder<QWSdk, Configuration>(::QWSdk)

    internal var apiPlan: Plan = configuration.plan
    internal var apiKey: String = configuration.apiKey
    internal var client: HttpClient = HttpClient(CIO) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = configuration.plan.host
                path("v7/")
                parameters.append("key", apiKey)
            }
        }
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                explicitNulls = false
            })
        }
        install(ContentEncoding) {
            gzip(1f)
        }
    }

    /**
     * @see Air
     */
    fun air(): Air = Air(this)

    /**
     * @see AirBeta
     */
    fun airBeta(): AirBeta = AirBeta(this)

    /**
     * @see Astronomy
     */
    fun astronomy(): Astronomy = Astronomy(this)

    /**
     * @see Geo
     */
    fun geo(): Geo = Geo(this)

    /**
     * @see Grid
     */
    fun grid(): Grid = Grid(this)

    /**
     * @see Indices
     */
    fun indices(): Indices = Indices(this)

    /**
     * @see Minutely
     */
    fun minutely(): Minutely = Minutely(this)

    /**
     * @see Ocean
     */
    fun ocean(): Ocean = Ocean(this)

    /**
     * @see SolarRadiation
     */
    fun solarRadiation(): SolarRadiation = SolarRadiation(this)

    /**
     * @see TimeMachine
     */
    fun timeMachine(): TimeMachine = TimeMachine(this)

    /**
     * @see Tropical
     */
    fun tropical(): Tropical = Tropical(this)

    /**
     * @see Warning
     */
    fun warning(): Warning = Warning(this)

    /**
     * @see Weather
     */
    fun weather(): Weather = Weather(this)

    /**
     * 需要查询的台风所在的流域，例如中国处于西北太平洋，即 basin=NP。当前仅支持 NP
     *
     * @property AL North Atlantic 北大西洋
     * @property EP Eastern Pacific 东太平洋
     * @property NP NorthWest Pacific 西北太平洋
     * @property SP SouthWestern Pacific 西南太平洋
     * @property NI North Indian 北印度洋
     * @property SI South Indian 南印度洋
     */
    enum class BasinType {
        NP, AL, EP, SP, NI, SI
    }

    /**
     * [ISO 3166
     * 国际标准化组织（ISO）发布的全球国家代码标准](https://github.com/qwd/LocationList/blob/master/iso3166.csv)
     */
    enum class CountryCode {
        AF, AX, AL, DZ, AS, AD, AO, AI, AQ, AG,
        AR, AM, AW, AU, AT, AZ, BS, BH, BD, BB,
        BY, BE, BZ, BJ, BM, BT, BO, BQ, BA, BW,
        BV, BR, IO, BN, BG, BF, BI, CV, KH, CM,
        CA, KY, CF, TD, CL, CN, CX, CC, CO, KM,
        CG, CD, CK, CR, CI, HR, CU, CW, CY, CZ,
        DK, DJ, DM, DO, EC, EG, SV, GQ, ER, EE,
        ET, FK, FO, FJ, FI, FR, GF, PF, TF, GA,
        GM, GE, DE, GH, GI, GR, GL, GD, GP, GU,
        GT, GG, GN, GW, GY, HT, HM, VA, HN, HK,
        HU, IS, IN, ID, IR, IQ, IE, IM, IL, IT,
        JM, JP, JE, JO, KZ, KE, KI, KP, KR, KW,
        KG, LA, LV, LB, LS, LR, LY, LI, LT, LU,
        MO, MK, MG, MW, MY, MV, ML, MT, MH, MQ,
        MR, MU, YT, MX, FM, MD, MC, MN, ME, MS,
        MA, MZ, MM, NA, NR, NP, NL, NC, NZ, NI,
        NE, NG, NU, NF, MP, NO, OM, PK, PW, PS,
        PA, PG, PY, PE, PH, PN, PL, PT, PR, QA,
        RE, RO, RU, RW, BL, SH, KN, LC, MF, PM,
        VC, WS, SM, ST, SA, SN, RS, SC, SL, SG,
        SX, SK, SI, SB, SO, ZA, GS, SS, ES, LK,
        SD, SR, SJ, SZ, SE, CH, SY, TW, TJ, TZ,
        TH, TL, TG, TK, TO, TT, TN, TR, TM, TC,
        TV, UG, UA, AE, GB, US, UM, UY, UZ, VU,
        VE, VN, VG, VI, WF, EH, YE, ZM, ZW;

        override fun toString(): String {
            return name.lowercase()
        }
    }

    /**
     * [天气指数类型](https://dev.qweather.com/docs/resource/indices-info/)
     *
     * @property ALL 全部天气指数
     * @property SPORT 运动指数
     * @property WASH_CAR 洗车指数
     * @property CLOTHING 穿衣指数
     * @property FISHING 钓鱼指数
     * @property UV_RAY 紫外线指数
     * @property TRAVEL 旅游指数
     * @property POLLEN_ALLERGY 花粉过敏指数
     * @property COMFORT 舒适度指数
     * @property COLD 感冒指数
     * @property AIR_POLLUTION_DIFFUSION_CONDITION 空气污染扩散条件指数
     * @property AIR_CONDITIONER 空调开启指数
     * @property SUNGLASSES 太阳镜指数
     * @property MAKEUP 化妆指数
     * @property DRYING 晾晒指数
     * @property TRAFFIC 交通指数
     * @property SPF 防晒指数
     */
    enum class IndicesType {
        ALL, SPORT, WASH_CAR, CLOTHING, FISHING,
        UV_RAY, TRAVEL, POLLEN_ALLERGY, COMFORT,
        COLD, AIR_POLLUTION_DIFFUSION_CONDITION,
        AIR_CONDITIONER, SUNGLASSES, MAKEUP, DRYING,
        TRAFFIC, SPF
    }

    /** [多语言代码](https://dev.qweather.com/docs/resource/language/#language-code) */
    enum class Lang {
        ZH, ZH_HANT, EN, DE, ES, FR, IT,
        JA, KO, RU, HI, TH, AR, PT, BN,
        MS, NL, EL, LA, SV, ID, PL, TR,
        CS, ET, VI, FIL, FI, HE, IS, NB;

        override fun toString(): String {
            return name.lowercase()
        }
    }

    /**
     * [订阅](https://dev.qweather.com/docs/finance/subscription)
     */
    enum class Plan(internal val host: String) {
        Free("devapi.qweather.com"),
        Standard("api.qweather.com");

        internal fun isFree() = this == Free
    }

    /**
     * POI类型
     *
     * @property scenic 景点
     * @property CSTA 潮流站点
     * @property TSTA 潮汐站点
     */
    enum class POIType {
        scenic, CSTA, TSTA;

        override fun toString(): String = name
    }

    /** [度量衡单位](https://dev.qweather.com/docs/resource/unit) */
    enum class Units {
        M, I;

        override fun toString(): String {
            return name.lowercase()
        }
    }
}