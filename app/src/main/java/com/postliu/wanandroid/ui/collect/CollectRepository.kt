package com.postliu.wanandroid.ui.collect

import com.postliu.wanandroid.common.launchPagingFromZeroFlow
import com.postliu.wanandroid.model.ApiService
import javax.inject.Inject

class CollectRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun collectArticle() = launchPagingFromZeroFlow {
        val result = apiService.collectArticle(page = it).result
        result.datas
    }
}
