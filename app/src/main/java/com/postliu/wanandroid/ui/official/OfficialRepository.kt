package com.postliu.wanandroid.ui.official

import com.postliu.wanandroid.model.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OfficialRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun officialTab() = flow {
        val result = apiService.officialTab().result
        emit(result)
    }.flowOn(Dispatchers.IO)
}
