package com.postliu.wanandroid.ui.main

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.postliu.wanandroid.common.Routes
import com.postliu.wanandroid.ui.home.home
import com.postliu.wanandroid.ui.login.login
import com.postliu.wanandroid.ui.login.register
import com.postliu.wanandroid.ui.official.official
import com.postliu.wanandroid.ui.project.project
import com.postliu.wanandroid.ui.square.square
import com.postliu.wanandroid.ui.system.system
import com.postliu.wanandroid.ui.theme.WanAndroidTheme
import kotlinx.coroutines.launch

@Composable
fun MainPage() {
    val navController: NavHostController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scope = rememberCoroutineScope()

    ModalDrawer(
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        drawerContent = {
            DrawerContent() {
                navController.navigate(Routes.Login)
                scope.launch {
                    drawerState.close()
                }
            }
        },
        scrimColor = Color(0x50000000),
        content = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .navigationBarsPadding(),
                bottomBar = {
                    when (currentDestination?.route) {
                        Routes.Home -> BottomNavBarView(navController = navController)
                        Routes.Square -> BottomNavBarView(navController = navController)
                        Routes.Official -> BottomNavBarView(navController = navController)
                        Routes.System -> BottomNavBarView(navController = navController)
                        Routes.Project -> BottomNavBarView(navController = navController)
                    }
                }) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Home,
                    modifier = Modifier.padding(it)
                ) {
                    home(navController)
                    square(navController)
                    official(navController)
                    system(navController)
                    project(navController)
                    login(navController)
                    register(navController)
                }
            }
        }
    )
}

@Composable
fun DrawerContent(
    loginState: Boolean = false,
    loginName: String = "你知道我是谁吗",
    login: () -> Unit = {},
) {
    val width = with(LocalDensity.current) {
        (Resources.getSystem().displayMetrics.widthPixels / 4 * 3) / density
    }
    Column(
        Modifier
            .width(width = width.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            rememberAsyncImagePainter(model = "https://avatars.githubusercontent.com/u/28628369?s=40&v=4"),
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
                .size(width.div(3).dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colors.primary, CircleShape),
            contentScale = ContentScale.FillWidth
        )
        if (loginState) {
            Text(text = loginName)
        } else {
            TextButton(onClick = {
                login.invoke()
            }) {
                Text(text = "去登录")
            }
        }
    }
}

@Composable
fun BottomNavBarView(
    navController: NavHostController,
    bottomNav: List<BottomNav> = listOf(
        BottomNav.Home,
        BottomNav.Square,
        BottomNav.Official,
        BottomNav.System,
        BottomNav.Project
    )
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNav.forEach { nav ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == nav.route } == true,
                onClick = {
                    println("BottomNavView当前路由 ===> ${currentDestination?.hierarchy?.toList()}")
                    println("当前路由栈 ===> ${navController.graph.nodes}")
                    if (currentDestination?.route != nav.route) {
                        navController.navigate(nav.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = nav.icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                },
                label = {
                    Text(text = nav.name)
                },
                alwaysShowLabel = true,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Gray,
            )
        }
    }
}

@Preview
@Composable
fun MainPagePreview() {
    WanAndroidTheme {
        Column {
            MainPage()
            DrawerContent() {

            }
        }
    }
}