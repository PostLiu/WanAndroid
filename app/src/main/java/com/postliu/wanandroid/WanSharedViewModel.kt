package com.postliu.wanandroid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.postliu.wanandroid.model.entity.WebData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WanSharedViewModel @Inject constructor() : ViewModel() {

    var webData by mutableStateOf<WebData?>(null)
}