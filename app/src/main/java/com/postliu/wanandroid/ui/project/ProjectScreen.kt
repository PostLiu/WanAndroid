package com.postliu.wanandroid.ui.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.postliu.wanandroid.common.Routes

fun NavGraphBuilder.project(navController: NavController) {
    composable(Routes.Project) {
        val list = Device.default
        val scrollState = rememberScrollState()
        Column(
            content = {
                list.forEachIndexed { index, device ->
                    GroupDeviceItem(
                        index = index,
                        name = device.name,
                        child = device.child,
                    )
                }
            }, modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        )
    }
}

@Composable
fun GroupDeviceItem(
    index: Int,
    name: String,
    child: List<Device.ChildDevice>,
) {
    var groupHeight by remember { mutableStateOf(0) }
    var childVisibility by remember(index) { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {
        TextButton(onClick = {
            childVisibility = !childVisibility
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = name)
        }
        AnimatedVisibility(visible = childVisibility) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(groupHeight.dp)
            ) {
                items(child) {
                    ChildDeviceItem(childDevice = it) { height ->
                        groupHeight = height
                    }
                }
            }
        }
    }
}

@Composable
fun ChildDeviceItem(childDevice: Device.ChildDevice, returnHeight: (Int) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                returnHeight.invoke(it.size.height)
            }
            .padding(horizontal = 12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = childDevice.name)
            Text(text = if (childDevice.online) "??????" else "??????")
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            childDevice.mode.forEach {
                TextButton(
                    onClick = {}, modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(6.dp)
                        .background(Color.Gray)
                ) {
                    Text(text = it)
                }
            }
        }
    }
}

data class Device(
    val name: String, val child: List<ChildDevice>
) {
    data class ChildDevice(
        val name: String,
        val online: Boolean,
        val mode: List<String>,
    )

    companion object {
        val default = listOf(
            Device(
                "??????", listOf(
                    ChildDevice("??????1", false, listOf("?????????", "?????????", "?????????", "??????4")),
                    ChildDevice("??????2", false, listOf("?????????", "?????????", "?????????", "??????4")),
                    ChildDevice("??????3", false, listOf("?????????", "?????????", "?????????", "??????4"))
                )
            ), Device(
                "??????", listOf(
                    ChildDevice("??????1", false, listOf("?????????", "?????????", "?????????", "??????4")),
                    ChildDevice("??????2", false, listOf("?????????", "?????????", "?????????", "??????4")),
                    ChildDevice("??????3", false, listOf("?????????", "?????????", "?????????", "??????4"))
                )
            ), Device(
                "??????", listOf(
                    ChildDevice("??????1", false, listOf("?????????", "?????????", "?????????", "??????4")),
                    ChildDevice("??????2", false, listOf("?????????", "?????????", "?????????", "??????4")),
                    ChildDevice("??????3", false, listOf("?????????", "?????????", "?????????", "??????4"))
                )
            ), Device(
                "??????", listOf(
                    ChildDevice("??????1", false, listOf("?????????", "?????????", "?????????", "??????4")),
                    ChildDevice("??????2", false, listOf("?????????", "?????????", "?????????", "??????4")),
                    ChildDevice("??????3", false, listOf("?????????", "?????????", "?????????", "??????4"))
                )
            )
        )
    }
}