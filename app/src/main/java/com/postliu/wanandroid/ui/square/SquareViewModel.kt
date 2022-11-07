package com.postliu.wanandroid.ui.square

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.postliu.wanandroid.common.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class SquareViewModel @Inject constructor(
    private val repository: SquareRepository
) : ViewModel() {

    val square = repository.square().cachedIn(viewModelScope).catch {
        LogUtils.printError(it)
    }

}