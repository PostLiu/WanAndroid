package com.postliu.wanandroid.ui.collect

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.jeremyliao.liveeventbus.LiveEventBus
import com.postliu.wanandroid.WanSharedViewModel
import com.postliu.wanandroid.common.BaseConstants
import com.postliu.wanandroid.utils.GsonUtils
import com.postliu.wanandroid.common.Routes
import com.postliu.wanandroid.common.collectAsStateWithLifecycle
import com.postliu.wanandroid.common.sharedViewModel
import com.postliu.wanandroid.model.entity.CollectArticleEntity
import com.postliu.wanandroid.model.entity.WebData
import com.postliu.wanandroid.ui.theme.WanAndroidTheme
import com.postliu.wanandroid.widgets.RefreshPagingList
import com.postliu.wanandroid.widgets.TopDefaultAppBar

@OptIn(ExperimentalLifecycleComposeApi::class)
fun NavGraphBuilder.userCollect(navController: NavController) {
    composable(Routes.Collect) { navBackStackEntry ->
        val sharedViewModel: WanSharedViewModel = navBackStackEntry.sharedViewModel(navController)
        val viewModel: UserCollectViewModel = hiltViewModel()
        val viewState = viewModel.viewState
        val isRefresh = viewState.isRefresh
        val reLoginState by LiveEventBus.get<Boolean>(BaseConstants.RE_LOGIN)
            .collectAsStateWithLifecycle(initialValue = false)
        val article = viewState.article.collectAsLazyPagingItems()
        val lazyListState = if (article.itemCount > 0) viewModel.lazyListState else LazyListState()
        DisposableEffect(key1 = Unit, effect = {
            viewModel.dispatch(CollectAction.FetchData)
            onDispose { }
        })
        UserCollectPage(lazyListState = lazyListState,
            article = article,
            isRefresh = isRefresh,
            onRefresh = { viewModel.dispatch(CollectAction.Refresh) },
            reLoginState = reLoginState,
            popBackStack = { navController.popBackStack() },
            toLogin = { navController.navigate(Routes.Login) },
            unCollect = { id, originId ->
                viewModel.dispatch(CollectAction.UnCollect(id, originId))
            }, toDetails = {
                val webData = WebData(title = it.title, url = it.link)
                sharedViewModel.webData = webData
                navController.navigate(Routes.ArticleDetails)
            })
    }
}

@Composable
fun UserCollectPage(
    lazyListState: LazyListState,
    article: LazyPagingItems<CollectArticleEntity>,
    isRefresh: Boolean,
    onRefresh: () -> Unit = {},
    reLoginState: Boolean,
    popBackStack: () -> Unit = {},
    toLogin: () -> Unit = {},
    unCollect: (Int, Int) -> Unit = { _, _ -> },
    toDetails: (CollectArticleEntity) -> Unit = {}
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopDefaultAppBar(title = { Text(text = "????????????") }, navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        })
    }, bottomBar = {
        AnimatedVisibility(visible = reLoginState) {
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = toLogin) {
                    Text(text = "???????????????????????????")
                }
            }
        }
    }) { paddingValues ->
        RefreshPagingList(
            lazyPagingItems = article,
            isRefreshing = isRefresh,
            onRefresh = onRefresh,
            paddingValues = paddingValues,
            listState = lazyListState
        ) {
            items(article) { entity ->
                entity?.let {
                    CollectArticleItem(
                        collectArticle = it,
                        unCollect = { id, originId ->
                            unCollect.invoke(id, originId)
                        }, toDetails = toDetails
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CollectArticleItem(
    collectArticle: CollectArticleEntity,
    unCollect: (Int, Int) -> Unit = { _, _ -> },
    toDetails: (CollectArticleEntity) -> Unit = {}
) {
    ListItem(modifier = Modifier
        .fillMaxWidth()
        .clickable { toDetails(collectArticle) }
        .background(MaterialTheme.colors.background),
        singleLineSecondaryText = true,
        secondaryText = {
            Text(text = collectArticle.author)
        },
        trailing = {
            IconButton(onClick = {
                unCollect.invoke(collectArticle.id, collectArticle.originId)
            }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        },
        text = {
            Text(
                text = collectArticle.title,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        })
}

@Preview
@Composable
fun CollectArticlePreview() {
    WanAndroidTheme {
        val json =
            "{\n" + "                \"author\": \"zsml2016\",\n" + "                \"chapterId\": 358,\n" + "                \"chapterName\": \"??????????????????\",\n" + "                \"courseId\": 13,\n" + "                \"desc\": \"??????????????????APP??????????????????????????????????????????APP???????????????????????????????????????????????????????????????????????????TextBannerView???????????????????????????????????????????????????????????????????????????????????????????????????\",\n" + "                \"envelopePic\": \"http://wanandroid.com/resources/image/pc/default_project_img.jpg\",\n" + "                \"id\": 132179,\n" + "                \"link\": \"http://www.wanandroid.com/blog/show/2382\",\n" + "                \"niceDate\": \"2020-05-11 10:11\",\n" + "                \"origin\": \"\",\n" + "                \"originId\": 3453,\n" + "                \"publishTime\": 1589163090000,\n" + "                \"title\": \"Android?????????????????? TextBannerView\",\n" + "                \"userId\": 41641,\n" + "                \"visible\": 0,\n" + "                \"zan\": 0\n" + "            }"
        val articleEntity = with(GsonUtils) {
            fromJson(json, CollectArticleEntity::class.java)
        }
        CollectArticleItem(collectArticle = articleEntity)
    }
}
