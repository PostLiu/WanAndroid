package com.postliu.wanandroid.model

import com.postliu.wanandroid.common.DataResult
import com.postliu.wanandroid.model.entity.LoginUserEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 登录密码
     * @return
     */
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
    ): DataResult<LoginUserEntity>

    /**
     * 注册账号
     *
     * @param userName 用户名
     * @param password 密码
     * @param rePassword 确认密码
     * @return
     */
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String,
    ): DataResult<LoginUserEntity>
}