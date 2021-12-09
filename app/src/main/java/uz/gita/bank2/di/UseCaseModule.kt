package uz.gita.bank2.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bank2.domain.usecase.*
import uz.gita.bank2.domain.usecase.impl.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun getSplashUseCase(impl: SplashUseCaseImpl): SplashUseCase

    @Binds
    @Singleton
    abstract fun getRegisterUseCase(impl: RegisterUseCaseImpl): RegisterUseCase

    @Binds
    @Singleton
    abstract fun getMainUseCase(impl: CardUseCaseImpl): MainUseCase

    @Binds
    @Singleton
    abstract fun getVerifyUseCase(impl: VerifyUseCaseImpl): VerifyUseCase

    @Binds
    @Singleton
    abstract fun getLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase

    @Binds
    @Singleton
    abstract fun getResetUseCase(impl: ResetUseCaseImpl): ResetUseCase

    @Binds
    @Singleton
    abstract fun getAddCardUseCase(impl: AddCardUseCaseImpl): AddCardUseCase

    @Binds
    @Singleton
    abstract fun getTransferUseCase(impl: TransferUseCaseImpl): TransferUseCase

    @Binds
    @Singleton
    abstract fun getProfileUseCase(impl: ProfileUseCaseImpl): ProfileUseCase

    @Binds
    @Singleton
    abstract fun getPinUseCase(impl: SecurityScreenUseCaseImpl): SecurityScreenUseCase

}