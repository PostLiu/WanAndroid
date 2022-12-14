package com.postliu.wanandroid.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.postliu.wanandroid.WanSharedViewModel
import com.postliu.wanandroid.utils.GsonUtils
import com.postliu.wanandroid.common.Routes
import com.postliu.wanandroid.common.sharedViewModel
import com.postliu.wanandroid.model.entity.ArticleEntity
import com.postliu.wanandroid.model.entity.BannerEntity
import com.postliu.wanandroid.model.entity.WebData
import com.postliu.wanandroid.ui.theme.WanAndroidTheme
import com.postliu.wanandroid.widgets.RefreshPagingList
import com.zj.banner.BannerPager
import com.zj.banner.ui.config.BannerConfig

fun NavGraphBuilder.home(navController: NavController) {
    composable(Routes.Home) { navBackStackEntry ->
        val sharedViewModel =
            navBackStackEntry.sharedViewModel<WanSharedViewModel>(navController = navController)
        val viewModel: HomeViewModel = hiltViewModel()
        val homeArticleState = viewModel.viewState.article.collectAsLazyPagingItems()
        val bannerList = viewModel.viewState.bannerList
        val sticky = viewModel.viewState.stickyPostsArticle
        val isRefresh = viewModel.viewState.isRefresh
        val lazyListState =
            if (homeArticleState.itemCount > 0) viewModel.listState else LazyListState()
        DisposableEffect(key1 = Unit, effect = {
            viewModel.dispatch(HomeAction.Refresh)
            onDispose { }
        })
        HomePage(
            lazyListState = lazyListState,
            bannerList = bannerList,
            sticky = sticky,
            articleList = homeArticleState,
            isRefresh = isRefresh,
            onRefresh = { viewModel.dispatch(HomeAction.Refresh) },
            bannerClick = {},
            articleClick = {
                val webData = WebData(title = it.title, url = it.link)
                sharedViewModel.webData = webData
                navController.navigate(Routes.ArticleDetails)
            },
            collect = { viewModel.dispatch(HomeAction.Collect(it)) },
        )
    }
}

@Composable
fun HomePage(
    lazyListState: LazyListState,
    bannerList: List<BannerEntity> = emptyList(),
    sticky: List<ArticleEntity>,
    articleList: LazyPagingItems<ArticleEntity>,
    isRefresh: Boolean,
    onRefresh: () -> Unit = {},
    bannerClick: (BannerEntity) -> Unit = {},
    articleClick: (ArticleEntity) -> Unit = {},
    collect: (ArticleEntity) -> Unit = {}
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        RefreshPagingList(
            lazyPagingItems = articleList,
            isRefreshing = isRefresh,
            onRefresh = { onRefresh() },
            paddingValues = paddingValues,
            listState = lazyListState
        ) {
            if (bannerList.isNotEmpty()) {
                item {
                    Banner(dataList = bannerList) { entity ->
                        bannerClick.invoke(entity)
                    }
                }
            }
            if (sticky.isNotEmpty()) {
                items(sticky) {
                    HomeArticleView(articleEntity = it,
                        isStick = true,
                        collect = { entity -> collect.invoke(entity) },
                        click = { entity -> articleClick.invoke(entity) })
                    Divider()
                }
            }
            items(articleList) { articleEntity ->
                articleEntity?.let { entity ->
                    HomeArticleView(articleEntity = entity,
                        collect = { collect.invoke(it) },
                        click = { article -> articleClick.invoke(article) })
                }
                Divider()
            }
        }
    }
}

@Composable
fun HomeArticleView(
    articleEntity: ArticleEntity,
    isStick: Boolean = false,
    collect: (ArticleEntity) -> Unit = {},
    click: (ArticleEntity) -> Unit = {},
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { click.invoke(articleEntity) }
        .background(MaterialTheme.colors.background)
        .padding(12.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.Top) {
                if (articleEntity.fresh) {
                    Text(
                        text = "???",
                        color = Color.Red,
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier
                            .padding(end = 4.dp, top = 3.dp)
                            .border(1.dp, Color.Red, MaterialTheme.shapes.small)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                if (isStick) {
                    Text(
                        text = "??????",
                        color = Color.Green,
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier
                            .padding(end = 4.dp, top = 3.dp)
                            .border(1.dp, Color.Green, MaterialTheme.shapes.small)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = articleEntity.title,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body1,
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                articleEntity.tags.map {
                    Text(
                        text = it.name,
                        color = Color.Blue,
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier
                            .padding(end = 4.dp, top = 3.dp)
                            .border(1.dp, Color.Blue, MaterialTheme.shapes.small)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle().copy(
                                color = Color.Gray, fontSize = 12.sp
                            )
                        ) {
                            if (articleEntity.superChapterName.isNotEmpty()) {
                                append(articleEntity.superChapterName)
                                append("\t/\t")
                            }
                            append(articleEntity.author.ifEmpty { articleEntity.shareUser })
                        }
                    }, style = MaterialTheme.typography.h1, color = Color.Gray
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = articleEntity.niceDate, style = MaterialTheme.typography.h1
                )
            }
        }
        IconButton(onClick = {
            collect(articleEntity)
        }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = if (articleEntity.collect) Color.Red else Color.Gray
            )
        }
    }
}

@Composable
fun Banner(
    dataList: List<BannerEntity>,
    config: BannerConfig = BannerConfig(),
    itemClick: (BannerEntity) -> Unit = {}
) {
    BannerPager(
        onBannerClick = {
            itemClick(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f),
        items = dataList,
        config = config,
        indicatorGravity = Alignment.BottomCenter,
        indicatorIsVertical = false
    )
}

@Preview
@Composable
fun HomeArticleViewPreview() {
    WanAndroidTheme {
        val json =
            "{\n" + "\"adminAdd\": false,\n" + "\"apkLink\": \"\",\n" + "\"audit\": 1,\n" + "\"author\": \"??????\",\n" + "\"canEdit\": false,\n" + "\"chapterId\": 409,\n" + "\"chapterName\": \"??????\",\n" + "\"collect\": true,\n" + "\"courseId\": 13,\n" + "\"desc\": \"\",\n" + "\"descMd\": \"\",\n" + "\"envelopePic\": \"\",\n" + "\"fresh\": true,\n" + "\"host\": \"\",\n" + "\"id\": 24838,\n" + "\"isAdminAdd\": false,\n" + "\"link\": \"https://mp.weixin.qq.com/s/UmBIVXqKXrjtzSdmUn41pA\",\n" + "\"niceDate\": \"1??????\",\n" + "\"niceShareDate\": \"15?????????\",\n" + "\"origin\": \"\",\n" + "\"prefix\": \"\",\n" + "\"projectLink\": \"\",\n" + "\"publishTime\": 1667232000000,\n" + "\"realSuperChapterId\": 407,\n" + "\"selfVisible\": 0,\n" + "\"shareDate\": 1667314443000,\n" + "\"shareUser\": \"\",\n" + "\"superChapterId\": 408,\n" + "\"superChapterName\": \"?????????\",\n" + "\"tags\": [\n" + "{\n" + "\"name\": \"?????????\",\n" + "\"url\": \"/wxarticle/list/409/1\"\n" + "}\n" + "],\n" + "\"title\": \"Kotlin | ???????????????????????????????????????????????????\",\n" + "\"type\": 0,\n" + "\"userId\": -1,\n" + "\"visible\": 1,\n" + "\"zan\": 0\n" + "}"
        val articleEntity = with(GsonUtils) {
            fromJson(json = json, ArticleEntity::class.java)
        }
        HomeArticleView(articleEntity = articleEntity, isStick = true)
    }
}