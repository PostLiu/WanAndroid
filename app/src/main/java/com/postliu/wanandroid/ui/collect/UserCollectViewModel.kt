@file:OptIn(ExperimentalCoroutinesApi::class)

package com.postliu.wanandroid.ui.collect

import androidx.annotation.Keep
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.postliu.wanandroid.common.FlowPagingData
import com.postliu.wanandroid.model.entity.CollectArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCollectViewModel @Inject constructor(
    private val repository: CollectRepository
) : ViewModel() {

    private val article by lazy { repository.collectArticle() }

    val lazyListState: LazyListState = LazyListState()

    var viewState by mutableStateOf(CollectViewState(article = article))

    fun dispatch(action: CollectAction) = viewModelScope.launch {
        when (action) {
            is CollectAction.FetchData -> {
                collectArticle()
            }

            is CollectAction.Refresh -> {
                collectArticle()
            }

            is CollectAction.UnCollect -> {
                unCollect(action.id, action.originId)
            }

            is CollectAction.ToDetails -> {}
        }
    }

    private fun collectArticle() = viewModelScope.launch {
        viewState = viewState.copy(isRefresh = true)
        val article = repository.collectArticle()
        viewState = viewState.copy(isRefresh = false, article = article)
    }

    private fun unCollect(id: Int, originId: Int) = viewModelScope.launch {
        repository.unCollectArticle(id, originId).mapLatest {
            repository.collectArticle()
        }.onStart {
            viewState = viewState.copy(isRefresh = false)
        }.catch {
            viewState = viewState.copy(isRefresh = false)
        }.collectLatest {
            viewState = viewState.copy(isRefresh = false, article = it)
        }
    }
}

@Keep
data class CollectViewState(
    val isRefresh: Boolean = false, val article: FlowPagingData<CollectArticleEntity>
)