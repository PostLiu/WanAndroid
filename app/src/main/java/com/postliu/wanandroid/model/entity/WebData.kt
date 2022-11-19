package com.postliu.wanandroid.model.entity

import android.net.Uri
import androidx.annotation.Keep
import com.postliu.wanandroid.utils.GsonUtils

@Keep

data class WebData(
    val title: String,
    val url: String,
) {
    override fun toString(): String {
        return Uri.encode(GsonUtils.toJson(this))
    }
}
