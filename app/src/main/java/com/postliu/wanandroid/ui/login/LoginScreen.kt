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
import androidx.compose.material.TextButton
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

fun NavGraphBuilder.login(navController: NavController) {
    composable(Routes.Login) {
        val context = LocalContext.current
        val viewModel: LoginViewModel = hiltViewModel()
        val loginState by viewModel.loginState.collectAsStateWithLifecycle(initialValue = null)
        LoginPage(popBackStack = {
            navController.popBackStack()
        }, login = { userName, password ->
            viewModel.dispatch(LoginAction.Login(userName, password))
        }, toRegister = { navController.navigate(Routes.Register) })
        LaunchedEffect(key1 = loginState, block = {
            loginState?.let {
                when (val data = it) {
                    is UIResult.Loading -> {
                        println("Loading")
                    }

                    is UIResult.Throwable -> {
                        println("Throwable")
                        context.toast(data.throwable.message)
                    }

                    is UIResult.Failed -> {
                        println("Failed")
                        context.toast(data.message)
                    }

                    is UIResult.Success -> {
                        println("登录成功")
                        context.toast("登录成功")
                        navController.popBackStack()
                    }
                }
            }
        })
    }
}

@Composable
fun LoginPage(
    popBackStack: () -> Unit = {},
    login: (String, String) -> Unit = { _, _ -> },
    toRegister: () -> Unit = {}
) {
    println("登录页面")
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "登录") }, navigationIcon = {
            IconButton(onClick = popBackStack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = null
                )
            }
        }, backgroundColor = MaterialTheme.colors.primary)
    }) { paddingValues ->
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
                label = { Text(text = "登录用户名") },
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
                    login.invoke(userName, password)
                })
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    login.invoke(userName, password)
                }, modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "登录")
            }
            TextButton(
                onClick = { toRegister.invoke() },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .align(Alignment.Start),
            ) {
                Text(text = "没有账号？点击注册")
            }
        }
    }
}

@Preview
@Composable
fun LoginPagePreview() {
    WanAndroidTheme {
        LoginPage()
    }
}