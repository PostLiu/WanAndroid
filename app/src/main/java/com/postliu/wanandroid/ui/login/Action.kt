package com.postliu.wanandroid.ui.login

sealed class LoginAction {
    data class Login(val userName: String, val password: String) : LoginAction()
}

sealed class RegisterAction {
    data class Register(val userName: String, val password: String, val rePassword: String) :
        RegisterAction()
}