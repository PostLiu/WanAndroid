package com.postliu.wanandroid.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.postliu.wanandroid.common.dataStore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(DelicateCoroutinesApi::class)
object DataStoreUtils {

    private lateinit var dataStore: DataStore<Preferences>

    fun init(context: Context) {
        dataStore = context.dataStore
    }

    @Suppress("UNCHECKED_CAST")
    fun putData(key: String, value: Any) = GlobalScope.launch {
        with(dataStore) {
            when (value) {
                is Long -> edit { it[longPreferencesKey(key)] = value }
                is Double -> edit { it[doublePreferencesKey(key)] = value }
                is String -> edit { it[stringPreferencesKey(key)] = value }
                is Boolean -> edit { it[booleanPreferencesKey(key)] = value }
                is Int -> edit { it[intPreferencesKey(key)] = value }
                is Set<*> -> edit { it[stringSetPreferencesKey(key)] = value as Set<String> }
                else -> edit { it[stringPreferencesKey(key)] = value.toString() }
            }
        }
    }

    fun longValue(key: String) = runBlocking(Dispatchers.IO) {
        dataStore.data.map { it[longPreferencesKey(key)] ?: 0L }.first()
    }

    fun doubleValue(key: String) = runBlocking(Dispatchers.IO) {
        dataStore.data.map { it[doublePreferencesKey(key)] ?: 0.0 }.first()
    }

    fun stringValue(key: String) = runBlocking(Dispatchers.IO) {
        dataStore.data.map { it[stringPreferencesKey(key)].orEmpty() }.first()
    }

    fun booleanValue(key: String) = runBlocking(Dispatchers.IO) {
        dataStore.data.map { it[booleanPreferencesKey(key)] ?: false }.first()
    }

    fun intValue(key: String) = runBlocking(Dispatchers.IO) {
        dataStore.data.map { it[intPreferencesKey(key)] ?: 0 }.first()
    }

    fun setStringValue(key: String) = runBlocking(Dispatchers.IO) {
        dataStore.data.map { it[stringSetPreferencesKey(key)] ?: emptySet() }.first()
    }

    fun booleanValueFlow(key: String) =
        dataStore.data.map { it[booleanPreferencesKey(key)] ?: false }
}