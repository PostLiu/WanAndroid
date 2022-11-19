package com.postliu.wanandroid.ui.collect

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.withTransaction
import com.postliu.wanandroid.dao.WanAndroidDatabase
import com.postliu.wanandroid.model.ApiService
import com.postliu.wanandroid.model.remote.UserCollectArticleRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CollectRepository @Inject constructor(
    private val apiService: ApiService,
    private val database: WanAndroidDatabase,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun collectArticle() = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = UserCollectArticleRemoteMediator(apiService, database)
    ) {
        database.userCollectArticleDao().collectArticlePagingSource()
    }.flow.flowOn(Dispatchers.IO)

    fun unCollectArticle(id: Int, originId: Int) = flow {
        database.withTransaction {
            apiService.collectArticleUnCollect(id, originId).result
            database.userCollectArticleDao().unCollectArticle(id, originId)
        }
        emit(true)
    }.flowOn(Dispatchers.IO)
}
