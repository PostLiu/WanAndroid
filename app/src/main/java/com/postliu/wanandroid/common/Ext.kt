package com.postliu.wanandroid.common

import android.content.Context
import android.widget.Toast

fun Context.toast(msg: Any?) {
    msg?.let { Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show() }
}