package com.postliu.wanandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.postliu.wanandroid.model.entity.ArticlePageKey

@Dao
interface ArticleKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: ArticlePageKey)

    @Query("select * from sticky_keys where id=:id")
    suspend fun getRemoteKey(id: String): ArticlePageKey?

    @Query("delete from sticky_keys where id=:id")
    suspend fun clearRemoteKey(id: String)
}