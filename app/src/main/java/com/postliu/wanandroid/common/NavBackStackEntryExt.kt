package com.postliu.wanandroid.common

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun NavBackStackEntry.rememberParentEntry(navController: NavController): NavBackStackEntry {
    val parentId = destination.parent!!.id
    return remember {
        navController.getBackStackEntry(parentId)
    }
}

@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): VM {
    return viewModel(viewModelStoreOwner = rememberParentEntry(navController = navController))
}

@Composable
inline fun <reified VM : ViewModel> NavController.getSharedViewModel(): VM {
    requireNotNull(currentBackStackEntry) {
        "currentBackStackEntry is null"
    }
    return currentBackStackEntry!!.sharedViewModel(this)
}