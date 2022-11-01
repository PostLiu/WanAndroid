package com.postliu.wanandroid.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.postliu.wanandroid.common.UIResult
import com.postliu.wanandroid.model.entity.LoginUserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    fun dispatch(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> {
                login(userName = action.userName, password = action.password)
            }
        }
    }

    private val mLoginState = MutableSharedFlow<UIResult<LoginUserEntity>>()
    val loginState = mLoginState.asSharedFlow()

    private fun login(userName: String, password: String) = viewModelScope.launch {
        repository.login(userName, password).onStart {
            mLoginState.emit(UIResult.Loading)
        }.catch {
            mLoginState.emit(UIResult.Throwable(it))
        }.collectLatest {
            mLoginState.emit(it)
        }
    }
}

sealed class LoginAction {
    data class Login(val userName: String, val password: String) : LoginAction()
}