package com.postliu.wanandroid.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.postliu.wanandroid.WanSharedViewModel
import com.postliu.wanandroid.common.Routes
import com.postliu.wanandroid.common.sharedViewModel
import com.postliu.wanandroid.widgets.TopDefaultAppBar

fun NavGraphBuilder.articleDetails(navController: NavController) =
    composable(Routes.ArticleDetails) {
        val sharedViewModel = it.sharedViewModel<WanSharedViewModel>(navController)
        val webData = sharedViewModel.webData
        ArticleDetailsPage(popBackStack = {
            navController.popBackStack()
        }, title = webData?.title.orEmpty(), url = webData?.url.orEmpty())
    }

@Composable
fun ArticleDetailsPage(
    title: String = "文章详情",
    url: String = "",
    popBackStack: () -> Unit = {}
) {
    val webViewState = rememberWebViewState(url = url)
    Column(modifier = Modifier.fillMaxSize()) {
        TopDefaultAppBar(title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }, navigationIcon = {
            IconButton(onClick = popBackStack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }, modifier = Modifier.fillMaxWidth())
        WebView(state = webViewState, modifier = Modifier.weight(1f))
    }
}