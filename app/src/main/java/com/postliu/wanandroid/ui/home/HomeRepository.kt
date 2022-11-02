package com.postliu.wanandroid.ui.home

import com.postliu.wanandroid.common.launchPagingFromZeroFlow
import com.postliu.wanandroid.model.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService
) {

    /**
     * 首页置顶文章
     *
     */
    fun stickyPostsArticle() = flow {
        val result = apiService.stickyPostsArticle()
        emit(result.result)
    }.flowOn(Dispatchers.IO)

    /**
     * 首页文章
     *
     */
    fun homeArticle() = launchPagingFromZeroFlow { page ->
        val result = apiService.homeArticle(page)
        println("数据：${result.errorCode}->${result.errorMsg}-->${result.data.curPage}")
        result.data.datas
    }
}
