package com.postliu.wanandroid.ui.collect

sealed interface CollectAction {
    object FetchData : CollectAction
    object Refresh : CollectAction
    data class Collect(val id: Int) : CollectAction
    data class ToDetails(val id: Int) : CollectAction
}