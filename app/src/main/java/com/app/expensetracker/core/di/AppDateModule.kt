package com.app.expensetracker.core.di

import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Qualifier to distinguish the shared selected-month flow from other StateFlows.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SelectedMonthFlow

@Module
@InstallIn(SingletonComponent::class)
object AppDateModule {

    /**
     * Single shared MutableStateFlow for the selected month.
     * Both AppDateViewModel (writes) and DashboardViewModel (reads) use this.
     * Annotated with @SelectedMonthFlow so Hilt knows which StateFlow to inject.
     */
    @Provides
    @Singleton
    @SelectedMonthFlow
    fun provideSelectedMonthFlow(): MutableStateFlow<YearMonthUiModel> =
        MutableStateFlow(YearMonthUiModel.current())

    @Provides
    @Singleton
    @SelectedMonthFlow
    fun provideSelectedMonthStateFlow(
        @SelectedMonthFlow flow: MutableStateFlow<YearMonthUiModel>
    ): StateFlow<YearMonthUiModel> = flow.asStateFlow()
}
