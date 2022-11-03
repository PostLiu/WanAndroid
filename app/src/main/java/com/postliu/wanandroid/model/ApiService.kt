package com.postliu.wanandroid.model

import com.postliu.wanandroid.common.BasePagingEntity
import com.postliu.wanandroid.common.DataResult
import com.postliu.wanandroid.model.entity.ArticleEntity
import com.postliu.wanandroid.model.entity.BannerEntity
import com.postliu.wanandroid.model.entity.CollectArticleEntity
import com.postliu.wanandroid.model.entity.LoginUserEntity
import com.postliu.wanandroid.model.entity.UserEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    /**
     * 首页轮播图
     *
     * @return
     */
    @GET("/banner/json")
    suspend fun bannerList(): DataResult<List<BannerEntity>>

    /**
     * 置顶文章
     *
     */
    @GET("/article/top/json")
    suspend fun stickyPostsArticle(): DataResult<List<ArticleEntity>>

    /**
     * 首页文章分页列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("/article/list/{page}/json")
    suspend fun homeArticle(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int = 10
    ): DataResult<BasePagingEntity<ArticleEntity>>

    /**
     * 登录用户信息
     *
     * @return
     */
    @GET("/user/lg/userinfo/json")
    suspend fun userInfo(): DataResult<UserEntity>

    /**
     * 用户收藏的文章
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("/lg/collect/list/{page}/json")
    suspend fun collectArticle(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int = 10
    ): DataResult<BasePagingEntity<CollectArticleEntity>>
}