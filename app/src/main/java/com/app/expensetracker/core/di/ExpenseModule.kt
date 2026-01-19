package com.app.expensetracker.core.di

import com.app.expensetracker.feature.expense.data.repository.ExpenseRepositoryImpl
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository
import com.app.expensetracker.feature.expense.domain.usecase.AddExpenseUseCase
import com.app.expensetracker.feature.expense.domain.usecase.GetExpensesByMonthUseCase
import com.app.expensetracker.feature.expense.domain.usecase.GetMonthlySummaryUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExpenseModule {

    @Provides
    @Singleton
    fun provideExpenseRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): ExpenseRepository =
        ExpenseRepositoryImpl(firestore, auth)

    @Provides
    @Singleton
    fun provideGetExpensesByMonthUseCase(
        repository: ExpenseRepository
    ) = GetExpensesByMonthUseCase(repository)

    @Provides
    @Singleton
    fun provideAddExpenseUseCase(
        repository: ExpenseRepository
    ): AddExpenseUseCase =
        AddExpenseUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMonthlySummaryUseCase(
        repository: ExpenseRepository
    ) = GetMonthlySummaryUseCase(repository)

}
