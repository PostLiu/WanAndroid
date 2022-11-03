package com.postliu.wanandroid.network

import com.postliu.wanandroid.common.BaseConstants
import com.postliu.wanandroid.common.DataStoreUtils
import com.postliu.wanandroid.common.LogUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 读取cookie信息，保存到本地
 *
 * @constructor Create empty Received cookies interceptor
 */
class ReceivedCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request()).run {
            val cookies = headers("Set-Cookie")
            DataStoreUtils.booleanValue(BaseConstants.IS_LOGIN).also {
                if (!it) {
                    if (cookies.isNotEmpty()) {
                        cookies.toHashSet().run {
                            println("设置cookies：$this")
                            DataStoreUtils.save(BaseConstants.COOKIE, this)
                        }
                    }
                }
            }
            LogUtils.printInfo(cookies)
            this
        }
    }
}

class SetCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder().run {
            DataStoreUtils.setStringValue(BaseConstants.COOKIE).forEach { cookies ->
                println("读取cookies：$cookies")
                addHeader("Cookie", cookies)
            }
            chain.proceed(build())
        }
    }
}