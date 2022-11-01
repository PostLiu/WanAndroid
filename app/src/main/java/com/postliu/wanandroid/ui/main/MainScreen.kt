@file:OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.postliu.wanandroid.ui.main

import android.content.res.Resources
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.postliu.wanandroid.common.Routes
import com.postliu.wanandroid.ui.theme.WanAndroidTheme

fun NavGraphBuilder.main(navController: NavController) {
    composable(Routes.Home) {
        MainPage(navController = navController)
    }
}

@Composable
fun MainPage(navController: NavController = rememberAnimatedNavController()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController
            )
        },
        scrimColor = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
        content = {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                TopAppBar(title = { Text(text = "首页") }, navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                })
            }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Button(onClick = { navController.navigate(Routes.Login) }) {
                        Text(text = "去登录")
                    }
                }
            }
        }
    )
}

@Composable
fun DrawerContent(
    loginState: Boolean = false,
    loginName: String = "你知道我是谁吗",
    navController: NavController = rememberAnimatedNavController(),
) {
    val width = with(LocalDensity.current) {
        (Resources.getSystem().displayMetrics.widthPixels / 4 * 3) / density
    }
    Column(
        Modifier
            .width(width = width.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            rememberAsyncImagePainter(model = "https://avatars.githubusercontent.com/u/28628369?s=40&v=4"),
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
                .size(width.div(3).dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.FillWidth
        )
        if (loginState) {
            Text(text = loginName)
        } else {
            TextButton(onClick = {
                navController.navigate(Routes.Login)
            }) {
                Text(text = "去登录")
            }
        }
    }
}

@Preview
@Composable
fun MainPagePreview() {
    WanAndroidTheme {
        Column {
            DrawerContent()
        }
    }
}