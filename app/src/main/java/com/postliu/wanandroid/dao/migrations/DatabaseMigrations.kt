package com.postliu.wanandroid.dao.migrations

import androidx.room.migration.AutoMigrationSpec
import com.postliu.wanandroid.dao.UserCollectDao
import com.postliu.wanandroid.model.entity.CollectArticleEntity

class DatabaseMigrations {
    /**
     * 数据库升级，从版本1升级到版本2
     *
     * 此版本添加[CollectArticleEntity]
     *
     * @see [UserCollectDao]
     *
     */
    class Schema1to2 : AutoMigrationSpec

}