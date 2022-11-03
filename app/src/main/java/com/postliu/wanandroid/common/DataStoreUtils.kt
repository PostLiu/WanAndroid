package com.postliu.wanandroid.common

import android.annotation.SuppressLint
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.postliu.wanandroid.WanAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@SuppressLint("StaticFieldLeak")
@OptIn(DelicateCoroutinesApi::class)
object DataStoreUtils {
    val context by lazy { WanAndroidApp.instance }

    @Suppress("UNCHECKED_CAST")
    fun save(key: String, value: Any) = GlobalScope.launch {
        with(context.dataStore) {
            when (value) {
                is String -> {
                    edit { it[stringPreferencesKey(key)] = value }
                }

                is Boolean -> {
                    edit { it[booleanPreferencesKey(key)] = value }
                }

                is Int -> {
                    edit { it[intPreferencesKey(key)] = value }
                }

                is Set<*> -> {
                    edit { it[stringSetPreferencesKey(key)] = value as Set<String> }
                }

                else -> {
                    edit { it[stringPreferencesKey(key)] = value.toString() }
                }
            }
        }
    }

    fun stringValue(key: String) = runBlocking {
        with(context.dataStore) {
            data.map { it[stringPreferencesKey(key)].orEmpty() }.first()
        }
    }

    fun booleanValue(key: String) = runBlocking {
        with(context.dataStore) {
            data.map { it[booleanPreferencesKey(key)] ?: false }
        }.first()
    }

    fun intValue(key: String) = runBlocking {
        with(context.dataStore) {
            data.map { it[intPreferencesKey(key)] ?: 0 }
        }.first()
    }

    fun setStringValue(key: String) = runBlocking {
        with(context.dataStore) {
            data.map { it[stringSetPreferencesKey(key)] ?: emptySet() }
        }.first()
    }

    fun setStringValueAsync(key: String, value: (Set<String>) -> Unit) = with(context.dataStore) {
        GlobalScope.launch {
            val result = data.map { it[stringSetPreferencesKey(key)] ?: emptySet() }.first()
            value.invoke(result)
        }
    }
}