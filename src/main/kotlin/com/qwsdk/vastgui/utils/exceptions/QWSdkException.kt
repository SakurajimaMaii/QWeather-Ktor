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

package com.qwsdk.vastgui.utils.exceptions

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/1/16

/**
 * [错误状态码](https://dev.qweather.com/docs/resource/status-code/)
 */
sealed class QWSdkException(val code: Int, message: String) : Exception(message) {
    class E204() :
        QWSdkException(204, "请求成功，但你查询的地区暂时没有你需要的数据。")

    class E400() :
        QWSdkException(400, "请求错误，可能包含错误的请求参数或缺少必选的请求参数。")

    class E401() :
        QWSdkException(401, "认证失败，可能使用了错误的KEY、数字签名错误、KEY的类型错误（如使用SDK的KEY去访问Web API）。")

    class E402() :
        QWSdkException(402, "超过访问次数或余额不足以支持继续访问服务，你可以充值、升级访问量或等待访问量重置。")

    class E403() :
        QWSdkException(403, "无访问权限，可能是绑定的PackageName、BundleID、域名IP地址不一致，或者是需要额外付费的数据。")

    class E404() :
        QWSdkException(404, "查询的数据或地区不存在。")

    class E429() :
        QWSdkException(429, "超过限定的QPM（每分钟访问次数），请参考QPM说明 https://dev.qweather.com/docs/resource/glossary/#qpm")

    class E500() :
        QWSdkException(500, "无响应或超时，接口服务异常请联系我们 https://www.qweather.com/contact")

    override fun toString(): String = "错误代码：$code，错误信息：${message}，详情参考：https://dev.qweather.com/docs/resource/status-code/"
}