package com.postliu.wanandroid.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.postliu.wanandroid.model.entity.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val article by lazy {
        repository.homeArticle().cachedIn(viewModelScope)
    }

    var viewState by mutableStateOf(HomeViewState(article = article))
        private set

    fun homeArticle() = viewModelScope.launch {
        repository.homeArticle().cachedIn(viewModelScope)
    }
}

data class HomeViewState(
    val article: Flow<PagingData<ArticleEntity>>
)