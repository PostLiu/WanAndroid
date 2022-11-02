package com.postliu.wanandroid.ui.home

sealed class HomeAction {
    object Refresh : HomeAction()
    data class Collect(val id: Int) : HomeAction()
    data class ToDetails(val id: Int) : HomeAction()
}