package com.postliu.wanandroid.model.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.postliu.wanandroid.dao.WanAndroidDatabase
import com.postliu.wanandroid.model.ApiService
import com.postliu.wanandroid.model.entity.ArticlePageKey
import com.postliu.wanandroid.model.entity.CollectArticleEntity

@OptIn(ExperimentalPagingApi::class)
class UserCollectArticleRemoteMediator(
    private val apiService: ApiService,
    private val database: WanAndroidDatabase
) : RemoteMediator<Int, CollectArticleEntity>() {

    companion object {
        private const val keyName = "collect_key"
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CollectArticleEntity>
    ): MediatorResult {
        return kotlin.runCatching {
            val articleDao = database.userCollectArticleDao()
            val keyDao = database.articleKeyDao()
            val pageKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction { keyDao.getRemoteKey(keyName) }
                    if (remoteKey?.page == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.page
                }
            }
            val page = pageKey ?: 0
            val query = apiService.collectArticle(page = page).result.datas
            val endOfPaginationReached = query.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    articleDao.clearAll()
                    keyDao.clearRemoteKey(keyName)
                }
                val nextPage = if (endOfPaginationReached) null else page + 1
                val key = ArticlePageKey(id = keyName, page = nextPage)
                articleDao.insert(query)
                keyDao.insert(key = key)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }.getOrElse { MediatorResult.Error(it) }
    }
}