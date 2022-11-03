package com.postliu.wanandroid.model.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * 个人信息
 *
 * @property coinInfo 积分信息
 * @property collectArticleInfo 收藏文章信息
 * @property userInfo 用户信息
 * @constructor Create empty User entity
 */
@Keep
data class UserEntity(
    @SerializedName("coinInfo")
    val coinInfo: CoinInfo,
    @SerializedName("collectArticleInfo")
    val collectArticleInfo: CollectArticleInfo,
    @SerializedName("userInfo")
    val userInfo: LoginUserEntity
) {
    @Keep
    data class CoinInfo(
        @SerializedName("coinCount")
        val coinCount: Int,
        @SerializedName("level")
        val level: Int,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("rank")
        val rank: String,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("username")
        val username: String
    )

    @Keep
    data class CollectArticleInfo(
        @SerializedName("count")
        val count: Int
    )
}