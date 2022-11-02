package com.postliu.wanandroid.ui.main

import androidx.lifecycle.ViewModel
import com.postliu.wanandroid.R
import com.postliu.wanandroid.common.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val defaultBottom = listOf(
        BottomNav.Home,
        BottomNav.Square,
        BottomNav.Official,
        BottomNav.System,
        BottomNav.Project
    )
}

sealed class BottomNav(val icon: Int, val name: String, val route: String) {
    object Home : BottomNav(R.drawable.icon_home, "首页", Routes.Home)
    object Square : BottomNav(R.drawable.icon_square, "广场", Routes.Square)
    object Official : BottomNav(R.drawable.icon_official, "公众号", Routes.Official)
    object System : BottomNav(R.drawable.icon_system, "体系", Routes.System)
    object Project : BottomNav(R.drawable.icon_project, "项目", Routes.Project)
}