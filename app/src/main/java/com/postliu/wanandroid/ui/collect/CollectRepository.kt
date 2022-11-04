package com.postliu.wanandroid.ui.collect

import com.postliu.wanandroid.common.launchPagingFromZeroFlow
import com.postliu.wanandroid.model.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CollectRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun collectArticle() = launchPagingFromZeroFlow {
        val result = apiService.collectArticle(page = it).result
        result.datas
    }

    fun unCollectArticle(id: Int, originId: Int) = flow {
        val result = apiService.collectArticleUnCollect(id, originId).result
        emit(result)
    }.flowOn(Dispatchers.IO)
}
