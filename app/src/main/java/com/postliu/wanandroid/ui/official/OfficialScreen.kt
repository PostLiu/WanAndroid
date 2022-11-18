package com.postliu.wanandroid.ui.official

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.gson.reflect.TypeToken
import com.postliu.wanandroid.common.GsonUtils
import com.postliu.wanandroid.common.Routes
import com.postliu.wanandroid.model.entity.OfficialTabEntity
import com.postliu.wanandroid.ui.theme.WanAndroidTheme

fun NavGraphBuilder.official(navController: NavController) {
    composable(Routes.Official) {
        val viewModel: OfficialViewModel = hiltViewModel()
        val viewState = viewModel.viewState
        val selectedTabIndex = viewState.selectedIndex
        val tabList = viewState.tabList
        OfficialPage(
            tabList = tabList,
            selectedTabIndex = selectedTabIndex,
            tabOnClick = { index, tabEntity ->
                viewModel.dispatch(OfficialAction.SelectedTab(index))
            }
        )
    }
}

@Composable
fun OfficialPage(
    tabList: List<OfficialTabEntity>,
    selectedTabIndex: Int = -1,
    tabOnClick: (Int, OfficialTabEntity) -> Unit = { _, _ -> }
) {
    Column(Modifier.fillMaxSize()) {
        if (tabList.isNotEmpty()) {
            ScrollableTabRow(selectedTabIndex = selectedTabIndex, edgePadding = 12.dp) {
                tabList.forEachIndexed { index, it ->
                    Tab(selected = false, onClick = { tabOnClick(index, it) }) {
                        Text(
                            text = it.name,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 12.dp)
                        )
                    }
                }
            }
        }
        Text(text = "测试自动更新")
        Text(text = "再来一个")
        Text(text = "第三个")
    }
}

@Preview
@Composable
fun OfficialPagePreview() {
    WanAndroidTheme {
        val json =
            "[\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 408,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"鸿洋\",\n" + "            \"order\": 190000,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 409,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"郭霖\",\n" + "            \"order\": 190001,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 410,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"玉刚说\",\n" + "            \"order\": 190002,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 411,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"承香墨影\",\n" + "            \"order\": 190003,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 413,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"Android群英传\",\n" + "            \"order\": 190004,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 414,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"code小生\",\n" + "            \"order\": 190005,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 415,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"谷歌开发者\",\n" + "            \"order\": 190006,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 416,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"奇卓社\",\n" + "            \"order\": 190007,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 417,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"美团技术团队\",\n" + "            \"order\": 190008,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 420,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"GcsSloop\",\n" + "            \"order\": 190009,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 421,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"互联网侦察\",\n" + "            \"order\": 190010,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 427,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"susion随心\",\n" + "            \"order\": 190011,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 428,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"程序亦非猿\",\n" + "            \"order\": 190012,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        },\n" + "        {\n" + "            \"articleList\": [],\n" + "            \"author\": \"\",\n" + "            \"children\": [],\n" + "            \"courseId\": 13,\n" + "            \"cover\": \"\",\n" + "            \"desc\": \"\",\n" + "            \"id\": 434,\n" + "            \"lisense\": \"\",\n" + "            \"lisenseLink\": \"\",\n" + "            \"name\": \"Gityuan\",\n" + "            \"order\": 190013,\n" + "            \"parentChapterId\": 407,\n" + "            \"type\": 0,\n" + "            \"userControlSetTop\": false,\n" + "            \"visible\": 1\n" + "        }\n" + "    ]"
        val tabList = GsonUtils.fromJson<List<OfficialTabEntity>>(
            json,
            object : TypeToken<List<OfficialTabEntity>>() {}.type
        )
        OfficialPage(tabList = tabList)
    }
}