package com.postliu.wanandroid.ui.square

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.postliu.wanandroid.common.GsonUtils
import com.postliu.wanandroid.common.Routes
import com.postliu.wanandroid.model.entity.ArticleEntity
import com.postliu.wanandroid.ui.theme.WanAndroidTheme
import com.postliu.wanandroid.widgets.RefreshPagingList

fun NavGraphBuilder.square(navController: NavController) {
    composable(Routes.Square) {
        val viewModel: SquareViewModel = hiltViewModel()
        val articleState = viewModel.square.collectAsLazyPagingItems()
        SquarePage(articleEntity = articleState)
    }
}

@Composable
fun SquarePage(articleEntity: LazyPagingItems<ArticleEntity>) {
    RefreshPagingList(lazyPagingItems = articleEntity, itemContent = {
        items(articleEntity) { entity ->
            entity?.let { SquareItem(data = it) }
        }
    })
}

@Composable
fun SquareItem(
    data: ArticleEntity,
    toDetails: (ArticleEntity) -> Unit = {},
    collected: (ArticleEntity) -> Unit = {}
) {
    var collectState by remember(data.collect) { mutableStateOf(data.collect) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { toDetails.invoke(data) }
            .background(MaterialTheme.colors.background)
            .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 6.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (data.fresh) {
                Text(
                    text = "新",
                    color = Color.Red,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .border(1.dp, Color.Red, MaterialTheme.shapes.small)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            Text(
                text = data.author.ifEmpty { data.shareUser },
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = data.niceDate, style = MaterialTheme.typography.h1)
        }
        Text(
            text = data.title,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            modifier = Modifier.padding(top = 4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = buildAnnotatedString {
                if (data.superChapterName.isNotEmpty()) {
                    append(data.superChapterName)
                    append("\t/\t")
                }
                append(data.chapterName)
            }, style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                collectState = !collectState
                collected.invoke(data)
            }) {
                Icon(
                    imageVector = Icons.Default.Favorite, contentDescription = null,
                    tint = if (collectState) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun SquareItemPreview() {
    WanAndroidTheme {
        val json =
            "{\n" + "                \"adminAdd\": false,\n" + "                \"apkLink\": \"\",\n" + "                \"audit\": 1,\n" + "                \"author\": \"\",\n" + "                \"canEdit\": false,\n" + "                \"chapterId\": 494,\n" + "                \"chapterName\": \"广场\",\n" + "                \"collect\": false,\n" + "                \"courseId\": 13,\n" + "                \"desc\": \"\",\n" + "                \"descMd\": \"\",\n" + "                \"envelopePic\": \"\",\n" + "                \"fresh\": true,\n" + "                \"host\": \"\",\n" + "                \"id\": 24883,\n" + "                \"isAdminAdd\": false,\n" + "                \"link\": \"https://juejin.cn/post/7162532709112217614\",\n" + "                \"niceDate\": \"54分钟前\",\n" + "                \"niceShareDate\": \"54分钟前\",\n" + "                \"origin\": \"\",\n" + "                \"prefix\": \"\",\n" + "                \"projectLink\": \"\",\n" + "                \"publishTime\": 1667782311000,\n" + "                \"realSuperChapterId\": 493,\n" + "                \"selfVisible\": 0,\n" + "                \"shareDate\": 1667782311000,\n" + "                \"shareUser\": \"goweii\",\n" + "                \"superChapterId\": 494,\n" + "                \"superChapterName\": \"广场Tab\",\n" + "                \"tags\": [],\n" + "                \"title\": \"Android进阶宝典 -- 并发编程之JMM模型和锁机制\",\n" + "                \"type\": 0,\n" + "                \"userId\": 20382,\n" + "                \"visible\": 0,\n" + "                \"zan\": 0\n" + "            }"
        val data = with(GsonUtils) {
            fromJson(json, ArticleEntity::class.java)
        }
        SquareItem(data = data)
    }
}