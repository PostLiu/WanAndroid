package com.postliu.wanandroid.ui.details

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.postliu.wanandroid.common.Routes

fun NavGraphBuilder.articleDetails(navController: NavController) {
    composable(Routes.ArticleDetails) {
        ArticleDetailsPage(popBackStack = {
            navController.popBackStack()
        })
    }
}

@Composable
fun ArticleDetailsPage(popBackStack: () -> Unit = {}) {

}