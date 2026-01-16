package com.app.expensetracker.core.di

import android.content.Context
import androidx.room.Room
import com.app.expensetracker.data.local.AppDatabase
import com.app.expensetracker.data.local.dao.UserDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "expense_db"
        ).build()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao =
        db.userDao()
}
