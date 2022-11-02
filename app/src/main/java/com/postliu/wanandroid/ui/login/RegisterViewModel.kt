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
class RegisterViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    fun dispatch(action: RegisterAction) = when (action) {
        is RegisterAction.Register -> {
            register(action.userName, action.password, action.rePassword)
        }
    }

    private val mRegister = MutableSharedFlow<UIResult<LoginUserEntity>>()
    val register = mRegister.asSharedFlow()

    private fun register(
        userName: String,
        password: String,
        rePassword: String
    ) = viewModelScope.launch {
        repository.register(userName, password, rePassword).onStart {
            mRegister.emit(UIResult.Loading)
        }.catch {
            mRegister.emit(UIResult.Throwable(it))
        }.collectLatest {
            mRegister.emit(it)
        }
    }
}