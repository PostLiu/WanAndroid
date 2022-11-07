package com.postliu.wanandroid.model.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * 公众号Tab信息
 *
 * @property author
 * @property courseId
 * @property id
 * @property name
 * @property order
 * @property parentChapterId
 * @property type
 * @property userControlSetTop
 * @property visible
 * @constructor Create empty Official tab entity
 */
@Keep
data class OfficialTabEntity(
    @SerializedName("author")
    val author: String,
    @SerializedName("courseId")
    val courseId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("parentChapterId")
    val parentChapterId: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("userControlSetTop")
    val userControlSetTop: Boolean,
    @SerializedName("visible")
    val visible: Int
)