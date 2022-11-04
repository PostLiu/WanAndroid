package com.postliu.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jeremyliao.liveeventbus.LiveEventBus
import com.postliu.wanandroid.common.BaseConstants
import com.postliu.wanandroid.ui.main.MainPage
import com.postliu.wanandroid.ui.theme.WanAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            var reLogin by remember { mutableStateOf(false) }
            LiveEventBus.get<Boolean>(BaseConstants.RE_LOGIN).observe(this) {
                reLogin = it
            }
            WanAndroidTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()
                val statusBarColor = MaterialTheme.colors.primary
                DisposableEffect(key1 = systemUiController, key2 = useDarkIcons) {
                    systemUiController.setSystemBarsColor(color = statusBarColor, useDarkIcons)
                    onDispose { }
                }
                MainPage(reLogin)
            }
        }
    }
}

