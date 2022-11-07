package com.postliu.wanandroid.ui.square

import com.postliu.wanandroid.common.launchPagingFromZeroFlow
import com.postliu.wanandroid.model.ApiService
import javax.inject.Inject

class SquareRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun square() = launchPagingFromZeroFlow {
        val result = apiService.square(page = it).result
        result.datas
    }
}
