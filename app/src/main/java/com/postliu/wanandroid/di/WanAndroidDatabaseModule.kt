package com.postliu.wanandroid.di

import android.content.Context
import androidx.room.Room
import com.postliu.wanandroid.dao.WanAndroidDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WanAndroidDatabaseModule {

    @Singleton
    @Provides
    fun providesWanAndroidDatabase(@ApplicationContext context: Context): WanAndroidDatabase {
        return Room.databaseBuilder(context, WanAndroidDatabase::class.java, "wanandroid.db")
            .build()
    }
}