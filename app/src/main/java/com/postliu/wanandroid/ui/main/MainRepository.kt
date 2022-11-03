package com.postliu.wanandroid.ui.main

import com.postliu.wanandroid.model.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun userInfo() = flow {
        val result = apiService.userInfo().result
        emit(result)
    }
}