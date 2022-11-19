package com.postliu.wanandroid.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.postliu.wanandroid.model.entity.CollectArticleEntity

@Dao
interface UserCollectDao {

    /**
     * 保存用户收藏的文章列表
     *
     * @param collectArticleEntity
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(collectArticleEntity: List<CollectArticleEntity>)

    /**
     * 分页查询收藏的文章列表
     *
     * @return
     */
    @Query("select * from user_collect")
    fun collectArticlePagingSource(): PagingSource<Int, CollectArticleEntity>

    @Query("delete from user_collect where id=:id and originId=:originId")
    suspend fun unCollectArticle(id: Int, originId: Int)

    @Query("delete from user_collect")
    suspend fun clearAll()
}