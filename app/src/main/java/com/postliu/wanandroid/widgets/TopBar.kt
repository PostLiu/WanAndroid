package com.postliu.wanandroid.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun TopSearchAppBar(label: String = "", navigationClick: () -> Unit = {}, searchClick: () -> Unit) {
    TopAppBar(title = {
        Text(text = label)
    }, navigationIcon = {
        IconButton(onClick = navigationClick) {
            Icon(
                imageVector = Icons.Default.Menu, contentDescription = null
            )
        }
    }, modifier = Modifier.fillMaxWidth(), actions = {
        IconButton(onClick = searchClick) {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = null
            )
        }
    }, backgroundColor = MaterialTheme.colors.primary)
}

@Composable
fun TopDefaultAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation
) = TopAppBar(
    modifier = modifier,
    title = title,
    navigationIcon = navigationIcon,
    actions = actions,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    elevation = elevation
)