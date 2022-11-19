package com.postliu.wanandroid.dao

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.postliu.wanandroid.dao.converters.ArticleTagConverters
import com.postliu.wanandroid.dao.migrations.DatabaseMigrations
import com.postliu.wanandroid.model.entity.ArticleEntity
import com.postliu.wanandroid.model.entity.ArticlePageKey
import com.postliu.wanandroid.model.entity.CollectArticleEntity

@Database(
    entities = [ArticleEntity::class, ArticlePageKey::class, CollectArticleEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = DatabaseMigrations.Schema1to2::class)
    ]
)
@TypeConverters(value = [ArticleTagConverters::class])
abstract class WanAndroidDatabase : RoomDatabase() {

    abstract fun stickyArticleDao(): StickyArticleDao

    abstract fun articleKeyDao(): ArticleKeyDao

    abstract fun homeArticleDao(): HomeArticleDao

    abstract fun userCollectArticleDao(): UserCollectDao
}