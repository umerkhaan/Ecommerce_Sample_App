package com.task.airlift_ecommerce_task.di.modules

import android.content.Context
import androidx.room.Room
import com.task.airlift_ecommerce_task.data.db.AppDatabase
import com.task.airlift_ecommerce_task.utils.misc.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, Constants.DATABASE_NAME).build()
}