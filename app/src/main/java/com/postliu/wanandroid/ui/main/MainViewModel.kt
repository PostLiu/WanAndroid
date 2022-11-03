package com.postliu.wanandroid.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.postliu.wanandroid.R
import com.postliu.wanandroid.common.Routes
import com.postliu.wanandroid.model.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    var userState by mutableStateOf<MutableStateFlow<UserEntity?>>(MutableStateFlow(null))
        private set

    fun userInfo() = viewModelScope.launch {
        repository.userInfo().collectLatest {
            userState = MutableStateFlow(it)
        }
    }
}

sealed class BottomNav(val icon: Int, val name: String, val route: String) {
    object Home : BottomNav(R.drawable.icon_home, "首页", Routes.Home)
    object Square : BottomNav(R.drawable.icon_square, "广场", Routes.Square)
    object Official : BottomNav(R.drawable.icon_official, "公众号", Routes.Official)
    object System : BottomNav(R.drawable.icon_system, "体系", Routes.System)
    object Project : BottomNav(R.drawable.icon_project, "项目", Routes.Project)
}