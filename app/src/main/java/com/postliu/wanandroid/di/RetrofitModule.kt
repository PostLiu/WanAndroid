package com.postliu.wanandroid.di

import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.postliu.wanandroid.BuildConfig
import com.postliu.wanandroid.network.ReLoginInterceptor
import com.postliu.wanandroid.network.ReceivedCookiesInterceptor
import com.postliu.wanandroid.network.SetCookiesInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun providerOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor.Builder().setLevel(Level.BODY).build())
            .addInterceptor(ReceivedCookiesInterceptor())
            .addInterceptor(SetCookiesInterceptor())
            .addInterceptor(ReLoginInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun providerRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_IP).addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setObjectToNumberStrategy(
                    ToNumberPolicy.LAZILY_PARSED_NUMBER
                ).create()
            )
        ).client(okHttpClient).build()
    }
}