package com.postliu.wanandroid.ui.login

import com.postliu.wanandroid.common.UIResult
import com.postliu.wanandroid.model.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiService: ApiService
) {

    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 登录密码
     */
    fun login(userName: String, password: String) = flow {
        val result = apiService.login(userName, password)
        if (result.success) {
            emit(UIResult.Success(result.result))
        } else {
            emit(UIResult.Failed(result.errorCode, result.errorMsg))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * 注册
     *
     * @param userName 用户名
     * @param password 登录密码
     * @param rePassword 确认密码
     */
    fun register(userName: String, password: String, rePassword: String) = flow {
        val result = apiService.register(userName, password, rePassword)
        if (result.success) {
            emit(UIResult.Success(result.result))
        } else {
            emit(UIResult.Failed(result.errorCode, result.errorMsg))
        }
    }.flowOn(Dispatchers.IO)
}
