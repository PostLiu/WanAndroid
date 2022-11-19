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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

object DataStoreUtils {

    private lateinit var dataStore: DataStore<Preferences>

    private lateinit var coroutineScope: CoroutineScope

    fun init(context: Context, scope: CoroutineScope) {
        dataStore = context.dataStore
        coroutineScope = scope
    }

    fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = coroutineScope.launch(context, start, block)

    @Suppress("UNCHECKED_CAST")
    fun putData(key: String, value: Any) = coroutineScope.launch {
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

    suspend fun longValue(key: String) =
        dataStore.data.map { it[longPreferencesKey(key)] ?: 0L }.first()

    suspend fun doubleValue(key: String) =
        dataStore.data.map { it[doublePreferencesKey(key)] ?: 0.0 }.first()

    suspend fun stringValue(key: String) =
        dataStore.data.map { it[stringPreferencesKey(key)].orEmpty() }.first()

    suspend fun booleanValue(key: String) =
        dataStore.data.map { it[booleanPreferencesKey(key)] ?: false }.first()

    suspend fun intValue(key: String) =
        dataStore.data.map { it[intPreferencesKey(key)] ?: 0 }.first()

    suspend fun setStringValue(key: String) =
        dataStore.data.map { it[stringSetPreferencesKey(key)] ?: emptySet() }.first()

    fun booleanValueFlow(key: String) =
        dataStore.data.map { it[booleanPreferencesKey(key)] ?: false }
}