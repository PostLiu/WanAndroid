@file:OptIn(ExperimentalPagingApi::class)

package com.postliu.wanandroid.model.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.postliu.wanandroid.dao.WanAndroidDatabase
import com.postliu.wanandroid.model.ApiService
import com.postliu.wanandroid.model.entity.ArticleEntity
import com.postliu.wanandroid.model.entity.ArticlePageKey

class ArticleRemoteMediator(
    private val apiService: ApiService, private val database: WanAndroidDatabase
) : RemoteMediator<Int, ArticleEntity>() {

    companion object {
        private const val keyName = "article_key"
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return kotlin.runCatching {
            val stickyArticleDao = database.homeArticleDao()
            val articleKeyDao = database.articleKeyDao()
            val pageKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        articleKeyDao.getRemoteKey(keyName)
                    }
                    if (remoteKey?.page == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.page
                }
            }
            val page = pageKey ?: 0
            val query =
                apiService.homeArticle(page = page).result.datas.map { it.copy(sticky = false) }
            val endOfPaginationReached = query.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    stickyArticleDao.clearAll(sticky = false)
                    articleKeyDao.clearRemoteKey(keyName)
                }
                val nextPage = if (endOfPaginationReached) null else page + 1
                val key = ArticlePageKey(id = keyName, page = nextPage)
                stickyArticleDao.insert(query)
                articleKeyDao.insert(key = key)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }.getOrElse { MediatorResult.Error(it) }
    }
}