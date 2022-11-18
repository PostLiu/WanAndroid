package com.postliu.wanandroid.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sticky_keys")
data class ArticlePageKey(
    @PrimaryKey
    val id: String,
    val page: Int?,
)