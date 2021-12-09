package uz.gita.bank2.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bank2.domain.repository.*
import uz.gita.bank2.domain.repository.impl.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun getAppRepository(repository: AppRepositoryImpl): AppRepository

    @Binds
    @Singleton
    abstract fun getAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun getCardRepository(impl: CardRepositoryImpl): CardRepository

    @Binds
    @Singleton
    abstract fun getTransferRepository(impl: TransferRepositoryImpl): TransferRepository

    @Binds
    @Singleton
    abstract fun getProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

}


