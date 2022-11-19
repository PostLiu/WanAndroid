package com.postliu.wanandroid.model.entity


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_collect")
@Keep
data class CollectArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("chapterId")
    val chapterId: Int,
    @SerializedName("chapterName")
    val chapterName: String,
    @SerializedName("courseId")
    val courseId: Int,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("envelopePic")
    val envelopePic: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("niceDate")
    val niceDate: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("originId")
    val originId: Int,
    @SerializedName("publishTime")
    val publishTime: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("visible")
    val visible: Int,
    @SerializedName("zan")
    val zan: Int
) {
    constructor() : this(0, "", 0, "", 0, "", "", 0, "", "", "", 0, 0L, "", 0, 0, 0)
}