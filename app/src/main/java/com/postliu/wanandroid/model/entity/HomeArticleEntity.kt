package com.postliu.wanandroid.model.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class HomeArticleEntity(
    @SerializedName("curPage")
    val curPage: Int,
    @SerializedName("datas")
    val datas: List<ArticleEntity>,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("over")
    val over: Boolean,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("total")
    val total: Int
)