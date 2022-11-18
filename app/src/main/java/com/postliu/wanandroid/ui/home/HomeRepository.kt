package com.postliu.wanandroid.ui.home

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.withTransaction
import com.postliu.wanandroid.common.LogUtils
import com.postliu.wanandroid.dao.WanAndroidDatabase
import com.postliu.wanandroid.model.ApiService
import com.postliu.wanandroid.model.entity.ArticleEntity
import com.postliu.wanandroid.model.remote.ArticleRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val database: WanAndroidDatabase,
) {

    fun bannerList() = flow {
        val result = apiService.bannerList().result
        emit(result)
    }.flowOn(Dispatchers.IO)

    /**
     * 首页置顶文章
     *
     */
    fun stickyPostsArticle() = flow {
        val stickyArticleDao = database.stickyArticleDao()
        stickyArticleDao.clearStickyArticle(sticky = true)
        val data = database.withTransaction {
            val result = apiService.stickyPostsArticle().result.map { it.copy(sticky = true) }
            stickyArticleDao.saveStickyArticle(result)
            stickyArticleDao.getStickyArticle(sticky = true)
        }
        emit(data)
    }.flowOn(Dispatchers.IO)

    /**
     * 收藏置顶文章
     *
     * @param article
     */
    fun collectStickyArticle(article: ArticleEntity) = flow {
        val stickyArticleDao = database.stickyArticleDao()
        val result = database.withTransaction {
            kotlin.runCatching {
                apiService.collectArticleInSite(article.id)
            }.onFailure {
                LogUtils.printError("收藏失败：$it")
            }
            stickyArticleDao.collectedStickyArticle(
                articleId = article.id,
                collect = true
            )
            stickyArticleDao.getStickyArticle(sticky = true)
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    /**
     * 首页文章
     *
     */
    @OptIn(ExperimentalPagingApi::class)
    fun homeArticle() = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = ArticleRemoteMediator(apiService, database)
    ) {
        database.homeArticleDao().queryPagingSourceArticle(sticky = false)
    }.flow.flowOn(Dispatchers.IO)
}
