package com.postliu.wanandroid.ui.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.postliu.wanandroid.common.FlowPagingData
import com.postliu.wanandroid.model.entity.ArticleEntity
import com.postliu.wanandroid.model.entity.BannerEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val pager by lazy {
        repository.homeArticle().cachedIn(viewModelScope)
    }

    val listState: LazyListState = LazyListState()

    var viewState by mutableStateOf(HomeViewState(article = pager))
        private set

    fun dispatch(action: HomeAction) {
        when (action) {
            is HomeAction.Collect -> {
                collectArticle(article = action.article)
            }

            is HomeAction.FetchData -> {
                fetchData()
            }

            is HomeAction.Refresh -> {
                refresh()
            }

            is HomeAction.ToDetails -> {

            }
        }
    }

    private fun refresh() {
        fetchData()
    }

    private fun fetchData() = viewModelScope.launch {
        val bannerList = repository.bannerList()
        val stickyPostsArticle = repository.stickyPostsArticle()
        bannerList.zip(stickyPostsArticle) { banners, sticky ->
            viewState =
                viewState.copy(bannerList = banners, stickyPostsArticle = sticky, isRefresh = false)
        }.onStart {
            viewState = viewState.copy(isRefresh = true)
        }.catch {
            viewState = viewState.copy(isRefresh = false)
        }.collect()
    }

    private fun collectArticle(article: ArticleEntity) = viewModelScope.launch {
        repository.collectStickyArticle(article = article).collectLatest {
            viewState = viewState.copy(stickyPostsArticle = it)
        }
    }
}

data class HomeViewState(
    val isRefresh: Boolean = false,
    val bannerList: List<BannerEntity> = emptyList(),
    val stickyPostsArticle: List<ArticleEntity> = emptyList(),
    val article: FlowPagingData<ArticleEntity> = flowOf()
)