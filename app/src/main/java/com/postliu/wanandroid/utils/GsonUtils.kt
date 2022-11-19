package com.postliu.wanandroid.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object GsonUtils {
    private val gson by lazy { Gson() }

    fun toJson(json: Any): String = gson.toJson(json)

    fun <T> fromJson(json: String, typeToken: TypeToken<T>): T = gson.fromJson(json, typeToken)

    fun <T> fromJson(json: String, type: Type?): T = gson.fromJson(json, type)

    fun <T> fromJson(json: String, clazz: Class<T>): T = gson.fromJson(json, clazz)
}