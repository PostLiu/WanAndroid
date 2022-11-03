package com.postliu.wanandroid.common

import android.content.Context
import android.widget.Toast
import androidx.datastore.preferences.preferencesDataStore

fun Context.toast(msg: Any?) {
    msg?.let { Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show() }
}

val Context.dataStore by preferencesDataStore("wanandroid")