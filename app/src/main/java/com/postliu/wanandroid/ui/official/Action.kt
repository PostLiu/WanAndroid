package com.postliu.wanandroid.ui.official

sealed interface OfficialAction {

    object FetchData : OfficialAction

    object Refresh : OfficialAction

    data class SelectedTab(val index: Int) : OfficialAction
}