package com.postliu.wanandroid.ui.collect

sealed interface CollectAction {
    object FetchData : CollectAction
    object Refresh : CollectAction
    data class UnCollect(val id: Int, val originId: Int) : CollectAction
    data class ToDetails(val id: Int) : CollectAction
}