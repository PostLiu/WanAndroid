package com.postliu.wanandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.postliu.wanandroid.model.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StickyArticleDao {

    /**
     * 保存置顶文章
     *
     * @param article
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStickyArticle(article: List<ArticleEntity>)

    /**
     * 查询所有的置顶文章
     *
     * @return
     */
    @Query("select * from article where sticky=:sticky")
    fun getStickyArticleFlow(sticky: Boolean = true): Flow<List<ArticleEntity>>

    @Query("select * from article where sticky=:sticky")
    suspend fun getStickyArticle(sticky: Boolean = true): List<ArticleEntity>

    /**
     * 更新收藏文章状态
     * 收藏文章/取消收藏文章
     *
     * @param articleId
     * @param collect
     */
    @Query("update article set collect=:collect where id=:articleId")
    suspend fun collectedStickyArticle(articleId: Int, collect: Boolean)

    /**
     * 清空置顶文章
     *
     */
    @Query("delete from article where sticky=:sticky")
    suspend fun clearStickyArticle(sticky: Boolean = true)
}