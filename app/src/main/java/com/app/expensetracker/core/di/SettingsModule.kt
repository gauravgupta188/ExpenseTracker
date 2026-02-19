package com.app.expensetracker.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.app.expensetracker.feature.expense.data.repository.ExpenseRepositoryImpl
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository
import com.app.expensetracker.feature.settings.data.datastore.settingsDataStore
import com.app.expensetracker.feature.settings.data.repository.SettingsRepositoryImpl
import com.app.expensetracker.feature.settings.domain.repository.SettingsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.settingsDataStore
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        dataStore: DataStore<Preferences>
    ): SettingsRepository {
        return SettingsRepositoryImpl(dataStore)
    }
}
