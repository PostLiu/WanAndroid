package com.postliu.wanandroid.common

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import okio.IOException

class TokenException(val code: Int, override val message: String) : IOException(message)

class QuestException(val code: Int, override val message: String) : IOException(message)

@Keep
data class DataResult<out T>(
    val errorCode: Int,
    val errorMsg: String,
    @SerializedName("data")
    val `data`: T
) {
    val result
        get() = when (errorCode) {
            0 -> `data`
            -1001 -> throw TokenException(errorCode, errorMsg)
            else -> throw QuestException(errorCode, errorMsg)
        }

    val success get() = errorCode == 0
}

sealed interface UIResult<out T> {
    object Loading : UIResult<Nothing>
    data class Throwable(val throwable: kotlin.Throwable) : UIResult<Nothing>
    data class Failed(val code: Int, val message: String) : UIResult<Nothing>
    data class Success<T>(val data: T) : UIResult<T>

    companion object {

        inline fun <T> UIResult<T>.doLoading(block: () -> Unit) {
            if (this is Loading) {
                block.invoke()
            }
        }

        inline fun <T> UIResult<T>.doThrowable(block: (kotlin.Throwable) -> Unit) {
            if (this is Throwable) {
                block.invoke(throwable)
            }
        }

        inline fun <T> UIResult<T>.doFailed(block: (String) -> Unit) {
            if (this is Failed) {
                block.invoke(message)
            }
        }

        inline fun <T> UIResult<T>.doSuccess(block: (T) -> Unit) {
            if (this is Success) {
                block.invoke(data)
            }
        }

        val <T> UIResult<T>.successData get() = if (this is Success) data else null

        val <T> UIResult<T>.failedMsg get() = if (this is Failed) message else ""
    }
}