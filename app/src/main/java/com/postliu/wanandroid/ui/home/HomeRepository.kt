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

    fun bannerList() = flow {
        val result = apiService.bannerList().result
        emit(result)
    }.flowOn(Dispatchers.IO)

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
    fun homeArticle() = launchPagingFromZeroFlow {
        val result = apiService.homeArticle(it).result.datas
        result
    }

    /**
     * 收藏站内文章
     *
     * @param articleId
     */
    fun collectArticleInSite(articleId: Int) = flow {
        val result = apiService.collectArticleInSite(articleId).result
        emit(result)
    }.flowOn(Dispatchers.IO)
}
