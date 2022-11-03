package com.postliu.wanandroid.model.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginUserEntity(
    @SerializedName("admin")
    val admin: Boolean,
    @SerializedName("chapterTops")
    val chapterTops: List<Any>,
    @SerializedName("coinCount")
    val coinCount: Int,
    @SerializedName("collectIds")
    val collectIds: List<Int>,
    @SerializedName("email")
    val email: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("publicName")
    val publicName: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("username")
    val username: String
) {
    companion object {
        fun empty() = LoginUserEntity(
            false,
            emptyList(),
            0,
            emptyList(),
            "",
            "",
            0,
            "你知道我是谁吗",
            "",
            "",
            "",
            0,
            ""
        )
    }
}