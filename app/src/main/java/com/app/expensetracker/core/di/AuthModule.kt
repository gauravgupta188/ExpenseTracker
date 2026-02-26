package com.app.expensetracker.core.di

import com.app.expensetracker.feature.auth.domain.repository.AuthRepository
import com.app.expensetracker.feature.auth.domain.usecases.LoginUserUseCase
import com.app.expensetracker.feature.auth.domain.usecases.RegisterUserUseCase
import com.app.expensetracker.feature.auth.data.AuthRepositoryImpl
import com.app.expensetracker.feature.auth.domain.usecases.CheckUserLoggedInUseCase
import com.app.expensetracker.feature.auth.domain.usecases.LoginWithGoogleUseCase
import com.app.expensetracker.feature.auth.domain.usecases.ResetPasswordUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepository =
        AuthRepositoryImpl(firebaseAuth)

    @Provides
    fun provideRegisterUserUseCase(
        repository: AuthRepository
    ) = RegisterUserUseCase(repository)

    @Provides
    fun provideLoginUserUseCase(
        repository: AuthRepository
    ) = LoginUserUseCase(repository)

    @Provides
    fun provideLoginWithGoogleUseCase(
        repository: AuthRepository
    ): LoginWithGoogleUseCase =
        LoginWithGoogleUseCase(repository)

    @Provides
    fun provideCheckUserLoggedInUseCase(
        repository: AuthRepository
    ) = CheckUserLoggedInUseCase(repository)

    @Provides
    fun provideResetPasswordUseCase(
        repository: AuthRepository
    ) = ResetPasswordUseCase(repository)

}
