@file:OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)

package com.postliu.wanandroid.ui.main

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.postliu.wanandroid.R
import com.postliu.wanandroid.common.BaseConstants
import com.postliu.wanandroid.common.DataStoreUtils
import com.postliu.wanandroid.common.Routes
import com.postliu.wanandroid.model.entity.LoginUserEntity
import com.postliu.wanandroid.ui.collect.userCollect
import com.postliu.wanandroid.ui.home.home
import com.postliu.wanandroid.ui.login.login
import com.postliu.wanandroid.ui.login.register
import com.postliu.wanandroid.ui.official.official
import com.postliu.wanandroid.ui.project.project
import com.postliu.wanandroid.ui.square.square
import com.postliu.wanandroid.ui.system.system
import com.postliu.wanandroid.ui.theme.WanAndroidTheme
import com.postliu.wanandroid.widgets.TopSearchAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainPage(reLogin: Boolean = false) {
    val navController: NavHostController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scope = rememberCoroutineScope()
    val viewModel: MainViewModel = hiltViewModel()
    val userInfo by viewModel.userState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = drawerState.isOpen, block = {
        if (DataStoreUtils.booleanValue(BaseConstants.IS_LOGIN)) {
            viewModel.userInfo()
        }
    })
    DisposableEffect(key1 = reLogin, effect = {
        if (reLogin) {
            navController.navigate(Routes.Login)
        }
        onDispose {}
    })
    ModalDrawer(modifier = Modifier.fillMaxSize(), drawerState = drawerState, drawerContent = {
        DrawerContent(
            loginState = DataStoreUtils.booleanValue(BaseConstants.IS_LOGIN),
            loginUser = userInfo?.userInfo ?: LoginUserEntity.empty(),
            level = userInfo?.coinInfo?.level?.toString().orEmpty(),
            ranking = userInfo?.coinInfo?.rank.orEmpty(),
            login = {
                navController.navigate(Routes.Login)
                scope.launch {
                    drawerState.close()
                }
            }, collect = {
                navController.navigate(Routes.Collect)
                scope.launch {
                    drawerState.close()
                }
            }
        )
    }, scrimColor = Color(0x50000000), content = {
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding(),
            topBar = {
                TopBarNavigation(currentDestination, scope, drawerState)
            },
            bottomBar = {
                BottomBarNavigation(currentDestination, navController)
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
                userCollect(navController)
            }
        }
    })
}

@Composable
private fun BottomBarNavigation(
    currentDestination: NavDestination?, navController: NavHostController
) {
    when (currentDestination?.route) {
        Routes.Home -> BottomNavBarView(navController = navController)
        Routes.Square -> BottomNavBarView(navController = navController)
        Routes.Official -> BottomNavBarView(navController = navController)
        Routes.System -> BottomNavBarView(navController = navController)
        Routes.Project -> BottomNavBarView(navController = navController)
    }
}

@Composable
private fun TopBarNavigation(
    currentDestination: NavDestination?, scope: CoroutineScope, drawerState: DrawerState
) {
    when (currentDestination?.route) {
        Routes.Home -> TopSearchAppBar(label = "首页", navigationClick = {
            scope.launch {
                if (drawerState.isClosed) {
                    drawerState.open()
                }
            }
        }, searchClick = {})

        Routes.Square -> TopSearchAppBar(label = "广场", navigationClick = {
            scope.launch {
                if (drawerState.isClosed) {
                    drawerState.open()
                }
            }
        }, searchClick = {})

        Routes.Official -> TopSearchAppBar(label = "公众号", navigationClick = {
            scope.launch {
                if (drawerState.isClosed) {
                    drawerState.open()
                }
            }
        }, searchClick = {})

        Routes.System -> TopSearchAppBar(label = "体系", navigationClick = {
            scope.launch {
                if (drawerState.isClosed) {
                    drawerState.open()
                }
            }
        }, searchClick = {})

        Routes.Project -> TopSearchAppBar(label = "项目", navigationClick = {
            scope.launch {
                if (drawerState.isClosed) {
                    drawerState.open()
                }
            }
        }, searchClick = {})
    }
}

@Composable
private fun DrawerContent(
    loginState: Boolean = true,
    loginUser: LoginUserEntity = LoginUserEntity.empty(),
    level: String = "",
    ranking: String = "",
    login: () -> Unit = {},
    collect: () -> Unit = {}
) {
    val width = with(LocalDensity.current) {
        (Resources.getSystem().displayMetrics.widthPixels / 4 * 3) / density
    }
    Column(
        Modifier
            .systemBarsPadding()
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (loginUser.icon.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.login_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(width.div(3).dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colors.primary, CircleShape),
                    contentScale = ContentScale.FillWidth
                )
            } else {
                Image(
                    rememberAsyncImagePainter(model = loginUser.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(width.div(3).dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colors.primary, CircleShape),
                    contentScale = ContentScale.FillWidth
                )
            }
            TextButton(
                onClick = {
                    login.invoke()
                },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onPrimary)
            ) {
                if (loginState) {
                    Text(
                        text = loginUser.nickname, style = MaterialTheme.typography.body1
                    )
                } else {
                    Text(
                        text = "去登录", style = MaterialTheme.typography.body1
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(text = buildAnnotatedString {
                    append("等级：")
                    append(level.ifEmpty { "--" })
                }, style = MaterialTheme.typography.h2, color = MaterialTheme.colors.onPrimary)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = buildAnnotatedString {
                    append("排名：")
                    append(ranking.ifEmpty { "--" })
                }, style = MaterialTheme.typography.h2, color = MaterialTheme.colors.onPrimary)
            }
        }
        ListItem(modifier = Modifier
            .fillMaxWidth()
            .clickable { },
            icon = { Icon(imageVector = Icons.Outlined.Info, contentDescription = null) },
            text = { Text(text = "我的积分") })
        ListItem(modifier = Modifier
            .fillMaxWidth()
            .clickable { collect() },
            icon = { Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null) },
            text = { Text(text = "我的收藏") })
        ListItem(modifier = Modifier.fillMaxWidth(),
            icon = { Icon(imageVector = Icons.Outlined.Share, contentDescription = null) },
            text = { Text(text = "我的分享") })
        ListItem(modifier = Modifier.fillMaxWidth(),
            icon = { Icon(imageVector = Icons.Outlined.Edit, contentDescription = null) },
            text = { Text(text = "TODO") })
    }
}

@Composable
private fun BottomNavBarView(
    navController: NavHostController, bottomNav: List<BottomNav> = listOf(
        BottomNav.Home, BottomNav.Square, BottomNav.Official, BottomNav.System, BottomNav.Project
    )
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(), backgroundColor = MaterialTheme.colors.background
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
fun DrawerContentPreview() {
    WanAndroidTheme {
        DrawerContent {

        }
    }
}