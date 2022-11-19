package com.postliu.wanandroid.network

import com.jeremyliao.liveeventbus.LiveEventBus
import com.postliu.wanandroid.common.BaseConstants
import com.postliu.wanandroid.common.DataResult
import com.postliu.wanandroid.utils.DataStoreUtils
import com.postliu.wanandroid.utils.GsonUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * 读取远程的cookie信息，保存到本地
 *
 * @constructor Create empty Received cookies interceptor
 */
class ReceivedCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request()).run {
            val cookies = headers("Set-Cookie")
            val isLogin = DataStoreUtils.booleanValue(BaseConstants.IS_LOGIN)
            if (!isLogin) {
                cookies.toHashSet().run {
                    DataStoreUtils.putData(BaseConstants.COOKIE, this)
                }
            }
            this
        }
    }
}

/**
 * 读取本地的cookie信息，设置到请求头
 *
 * @constructor Create empty Set cookies interceptor
 */
class SetCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        return chain.request().newBuilder().run {
            DataStoreUtils.setStringValue(BaseConstants.COOKIE).forEach {
                addHeader("Cookie", it)
            }
            chain.proceed(build())
        }
    }
}

class ReLoginInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return kotlin.run {
            val response = chain.proceed(chain.request()).newBuilder().build()
            if (response.isSuccessful) {
                val responseBody = response.body
                val source = responseBody?.source()
                source?.request(Long.MAX_VALUE)
                val buffer = source?.buffer
                val string = buffer?.clone()?.readString(Charsets.UTF_8)
                val result = with(GsonUtils) {
                    fromJson(string.orEmpty(), DataResult::class.java)
                }
                if (result.errorCode == -1001) {
                    DataStoreUtils.putData(BaseConstants.IS_LOGIN, false)
                    DataStoreUtils.putData(BaseConstants.COOKIE, "")
                    LiveEventBus.get<Boolean>(BaseConstants.RE_LOGIN).post(true)
                    responseBody?.close()
                    fakeResponse(chain)
                } else {
                    response
                }
            } else {
                response
            }
        }
    }

    private fun fakeResponse(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().build()
        val response = chain.proceed(request)
        return Response.Builder().code(200).message(request.method).protocol(response.protocol)
            .request(request).body("".toResponseBody()).build()
    }
}