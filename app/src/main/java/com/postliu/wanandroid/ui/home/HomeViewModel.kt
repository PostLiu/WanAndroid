package com.postliu.wanandroid.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.postliu.wanandroid.model.entity.ArticleEntity
import com.postliu.wanandroid.model.entity.BannerEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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

    var viewState by mutableStateOf(HomeViewState(article = pager))
        private set

    init {
        dispatch(HomeAction.FetchData)
    }

    fun dispatch(action: HomeAction) {
        when (action) {
            is HomeAction.Collect -> {

            }

            is HomeAction.FetchData -> {
                fetchData()
            }

            is HomeAction.Refresh -> {
                fetchData()
            }

            is HomeAction.ToDetails -> {

            }
        }
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
}

data class HomeViewState(
    val isRefresh: Boolean = false,
    val bannerList: List<BannerEntity> = emptyList(),
    val stickyPostsArticle: List<ArticleEntity> = emptyList(),
    val article: Flow<PagingData<ArticleEntity>>
)