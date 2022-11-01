@file:OptIn(ExperimentalAnimationApi::class)

package com.postliu.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.postliu.wanandroid.common.Routes
import com.postliu.wanandroid.ui.login.login
import com.postliu.wanandroid.ui.main.main
import com.postliu.wanandroid.ui.theme.WanAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WanAndroidTheme(dynamicColor = false) {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()
                val statusBarColor = MaterialTheme.colorScheme.background
                DisposableEffect(key1 = systemUiController, key2 = useDarkIcons) {
                    systemUiController.setSystemBarsColor(color = statusBarColor, useDarkIcons)
                    onDispose { }
                }
                val navController = rememberAnimatedNavController()
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Routes.Home,
                    modifier = Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                ) {
                    main(navController)
                    login(navController)
                }
            }
        }
    }
}