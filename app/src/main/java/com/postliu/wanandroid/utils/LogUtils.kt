package com.postliu.wanandroid.utils

import android.util.Log
import com.postliu.wanandroid.BuildConfig

object LogUtils {
    const val TAG = "LogUtils"

    inline fun <reified T : Any> printInfo(msg: T?) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "print: $msg")
        }
    }

    inline fun <reified T : Any> printWarning(msg: T?) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, "printWarning: $msg")
        }
    }

    inline fun <reified T : Any> printError(msg: T?) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "printError: $msg")
        }
    }
}