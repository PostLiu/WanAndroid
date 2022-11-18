package com.postliu.wanandroid.ui.official

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.postliu.wanandroid.model.entity.OfficialTabEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfficialViewModel @Inject constructor(
    private val repository: OfficialRepository
) : ViewModel() {

    init {
        dispatch(OfficialAction.FetchData)
    }

    var viewState by mutableStateOf(OfficialViewState())
        private set

    fun dispatch(action: OfficialAction) {
        when (action) {
            is OfficialAction.FetchData -> {
                officialTabData()
            }

            is OfficialAction.Refresh -> {

            }

            is OfficialAction.SelectedTab -> {
                updateTabSelectedIndex(action.index)
            }
        }
    }

    private fun officialTabData() = viewModelScope.launch {
        repository.officialTab().map {
            viewState = viewState.copy(tabList = it)
        }.launchIn(viewModelScope)
    }

    private fun updateTabSelectedIndex(index: Int) = viewModelScope.launch {
        viewState = viewState.copy(selectedIndex = index)
    }
}

data class OfficialViewState(
    val tabList: List<OfficialTabEntity> = emptyList(),
    val selectedIndex: Int = 0,
)