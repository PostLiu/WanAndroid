package com.postliu.wanandroid.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.postliu.wanandroid.model.entity.ArticleEntity

@Dao
interface HomeArticleDao {

    /**
     * 保存文章
     *
     * @param list
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(list: List<ArticleEntity>)

    /**
     * 获取分页文章列表
     *
     * @return
     */
    @Query("select * from article where sticky=:sticky")
    fun queryPagingSourceArticle(sticky: Boolean = false): PagingSource<Int, ArticleEntity>

    /**
     * 清空文章列表
     *
     */
    @Query("delete from article where sticky=:sticky")
    suspend fun clearAll(sticky: Boolean = false)
}