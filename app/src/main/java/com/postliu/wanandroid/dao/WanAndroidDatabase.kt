package com.postliu.wanandroid.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.postliu.wanandroid.dao.converters.ArticleTagConverters
import com.postliu.wanandroid.model.entity.ArticleEntity
import com.postliu.wanandroid.model.entity.ArticlePageKey

@Database(
    entities = [ArticleEntity::class, ArticlePageKey::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(value = [ArticleTagConverters::class])
abstract class WanAndroidDatabase : RoomDatabase() {

    abstract fun stickyArticleDao(): StickyArticleDao

    abstract fun articleKeyDao(): ArticleKeyDao

    abstract fun homeArticleDao(): HomeArticleDao
}