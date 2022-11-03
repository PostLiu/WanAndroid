package com.postliu.wanandroid.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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