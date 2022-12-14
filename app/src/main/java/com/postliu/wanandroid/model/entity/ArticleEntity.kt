package com.postliu.wanandroid.model.entity


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * 文章信息
 *
 * @property adminAdd
 * @property apkLink
 * @property audit
 * @property author
 * @property canEdit
 * @property chapterId
 * @property chapterName
 * @property collect 是否已收藏
 * @property courseId
 * @property desc
 * @property descMd
 * @property envelopePic
 * @property fresh 是否是最新文章
 * @property host
 * @property id
 * @property hasAdminAdd
 * @property link
 * @property niceDate 文章时间
 * @property niceShareDate 分享文章时间
 * @property origin
 * @property prefix
 * @property projectLink
 * @property publishTime
 * @property realSuperChapterId
 * @property selfVisible
 * @property shareDate
 * @property shareUser
 * @property superChapterId
 * @property superChapterName
 * @property tags
 * @property title
 * @property type
 * @property userId
 * @property visible
 * @property zan
 * @property sticky 是否是置顶文章
 * @constructor Create empty Article entity
 */
@Entity(tableName = "article")
@Keep
data class ArticleEntity(
    @SerializedName("adminAdd")
    val adminAdd: Boolean,
    @JvmField
    @SerializedName("isAdminAdd")
    val hasAdminAdd: Boolean,
    @SerializedName("apkLink")
    val apkLink: String,
    @SerializedName("audit")
    val audit: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("canEdit")
    val canEdit: Boolean,
    @SerializedName("chapterId")
    val chapterId: Int,
    @SerializedName("chapterName")
    val chapterName: String,
    @SerializedName("collect")
    val collect: Boolean,
    @SerializedName("courseId")
    val courseId: Int,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("descMd")
    val descMd: String,
    @SerializedName("envelopePic")
    val envelopePic: String,
    @SerializedName("fresh")
    val fresh: Boolean,
    @SerializedName("host")
    val host: String,
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("niceDate")
    val niceDate: String,
    @SerializedName("niceShareDate")
    val niceShareDate: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("prefix")
    val prefix: String,
    @SerializedName("projectLink")
    val projectLink: String,
    @SerializedName("publishTime")
    val publishTime: Long,
    @SerializedName("realSuperChapterId")
    val realSuperChapterId: Int,
    @SerializedName("selfVisible")
    val selfVisible: Int,
    @SerializedName("shareDate")
    val shareDate: Long,
    @SerializedName("shareUser")
    val shareUser: String,
    @SerializedName("superChapterId")
    val superChapterId: Int,
    @SerializedName("superChapterName")
    val superChapterName: String,
    @SerializedName("tags")
    val tags: List<Tag>,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("visible")
    val visible: Int,
    @SerializedName("zan")
    val zan: Int,
    val sticky: Boolean,
) {
    @Keep
    data class Tag(
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
        val url: String
    )

    constructor() : this(
        false,
        false,
        "",
        0,
        "",
        false,
        0,
        "",
        false,
        0,
        "",
        "",
        "",
        false,
        "",
        0,
        -1,
        "",
        "",
        "",
        "",
        "",
        "",
        0L,
        0,
        0,
        0L,
        "",
        0,
        "",
        emptyList(),
        "",
        0,
        0,
        0,
        0,
        false
    )
}