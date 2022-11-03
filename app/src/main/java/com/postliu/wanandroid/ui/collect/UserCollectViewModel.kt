package com.postliu.wanandroid.ui.collect

import androidx.annotation.Keep
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.postliu.wanandroid.common.FlowPagingData
import com.postliu.wanandroid.model.entity.CollectArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCollectViewModel @Inject constructor(
    private val repository: CollectRepository
) : ViewModel() {

    private val article by lazy { repository.collectArticle() }

    var viewState by mutableStateOf(CollectViewState(article = article))

    fun dispatch(action: CollectAction) = viewModelScope.launch {
        when (action) {
            is CollectAction.FetchData -> {
                collectArticle()
            }

            is CollectAction.Refresh -> {
                collectArticle()
            }

            is CollectAction.Collect -> {}
            is CollectAction.ToDetails -> {}
        }
    }

    private fun collectArticle() = viewModelScope.launch {
        viewState = viewState.copy(isRefresh = true)
        val article = repository.collectArticle()
        viewState = viewState.copy(isRefresh = false, article = article)
    }
}

@Keep
data class CollectViewState(
    val isRefresh: Boolean = false,
    val article: FlowPagingData<CollectArticleEntity>
)