package com.postliu.wanandroid.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtils {
    private val gson by lazy { Gson() }

    fun toJson(json: Any): String = gson.toJson(json)

    fun <T> fromJson(json: String, typeToken: TypeToken<T>): T = gson.fromJson(json, typeToken)

    fun <T> fromJson(json: String, clazz: Class<T>): T = gson.fromJson(json, clazz)
}