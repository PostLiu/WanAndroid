@file:OptIn(ExperimentalLifecycleComposeApi::class)

package com.postliu.wanandroid.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.postliu.wanandroid.R
import com.postliu.wanandroid.common.Routes
import com.postliu.wanandroid.common.UIResult
import com.postliu.wanandroid.common.toast
import com.postliu.wanandroid.ui.theme.WanAndroidTheme

fun NavGraphBuilder.register(navController: NavController) {
    composable(Routes.Register) {
        val context = LocalContext.current
        val viewModel: RegisterViewModel = hiltViewModel()
        val registerState by viewModel.register.collectAsStateWithLifecycle(initialValue = null)
        // 添加页面
        RegisterPage(popBackStack = {
            navController.popBackStack()
        }, register = { userName, password, rePassword ->
            viewModel.dispatch(RegisterAction.Register(userName, password, rePassword))
        })
        // 监听状态
        LaunchedEffect(key1 = registerState, block = {
            registerState?.let {
                when (it) {
                    is UIResult.Loading -> {

                    }

                    is UIResult.Throwable -> {
                        context.toast(it.throwable)
                    }

                    is UIResult.Failed -> {
                        context.toast(it.message)
                    }

                    is UIResult.Success -> {
                        context.toast("注册成功")
                        navController.popBackStack(Routes.Home, false)
                    }
                }
            }
        })
    }
}

@Composable
fun RegisterPage(
    popBackStack: () -> Unit = {},
    register: (String, String, String) -> Unit = { _, _, _ -> }
) {
    println("进入注册页面")
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "注册登录") }, navigationIcon = {
            IconButton(onClick = popBackStack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = null
                )
            }
        }, backgroundColor = MaterialTheme.colors.primary)
    }, backgroundColor = Color.White) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.onPrimary,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
            )
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text(text = "注册用户名") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next
                ),
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "登录密码") },
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(onGo = {
                    register.invoke(userName, password, rePassword)
                })
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = rePassword,
                onValueChange = { rePassword = it },
                label = { Text(text = "确认登录密码") },
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(onGo = {
                    register.invoke(userName, password, rePassword)
                })
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    register.invoke(userName, password, rePassword)
                }, modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "注册")
            }
        }
    }
}

@Preview
@Composable
fun RegisterPagePreview() {
    WanAndroidTheme {
        RegisterPage()
    }
}