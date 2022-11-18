package com.postliu.wanandroid.ui.home

import com.postliu.wanandroid.model.entity.ArticleEntity

sealed class HomeAction {
    object FetchData : HomeAction()
    object Refresh : HomeAction()
    data class Collect(val article: ArticleEntity) : HomeAction()
    data class ToDetails(val id: Int) : HomeAction()
}