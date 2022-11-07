package com.postliu.wanandroid.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.postliu.wanandroid.common.LogUtils

@Composable
fun <T : Any> RefreshPagingList(
    lazyPagingItems: LazyPagingItems<T>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    listState: LazyListState = rememberLazyListState(),
    paddingValues: PaddingValues = PaddingValues(),
    itemContent: LazyListScope.() -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val error = lazyPagingItems.loadState.refresh is LoadState.Error
    if (error) {
        LoadErrorContent {
            lazyPagingItems.retry()
        }
        return
    }
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            onRefresh.invoke()
            lazyPagingItems.refresh()
        }, modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize()
    ) {
        swipeRefreshState.isRefreshing =
            ((lazyPagingItems.loadState.refresh is LoadState.Loading) || isRefreshing)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {
            itemContent()
            if (!swipeRefreshState.isRefreshing) {
                item {
                    when (lazyPagingItems.loadState.append) {
                        is LoadState.Loading -> {
                            LoadingItem()
                        }

                        is LoadState.Error -> {
                            ErrorItem {
                                lazyPagingItems.retry()
                            }
                        }

                        is LoadState.NotLoading -> {
                            if (lazyPagingItems.loadState.append.endOfPaginationReached) {
                                NoMoreItem()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadEmpty(title: String = "没有任何内容！") {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = title)
    }
}

@Composable
fun LoadErrorContent(retry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text(text = "请求出错啦！")
            Button(onClick = retry) {
                Text(text = "点击重试")
            }
        }
    }
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun NoMoreItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "没有更多数据了！", modifier = Modifier.padding(12.dp))
    }
}

@Composable
fun ErrorItem(retry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        Button(onClick = retry) {
            Text(text = "重试")
        }
    }
}