package uz.gita.bank2.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.gita.bank2.data.retrofit.api.AuthApi
import uz.gita.bank2.data.retrofit.api.AuthCardApi
import uz.gita.bank2.data.retrofit.api.MoneyTransferApi
import uz.gita.bank2.data.retrofit.api.ProfileApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun getCardApi(retrofit: Retrofit): AuthCardApi = retrofit.create(AuthCardApi::class.java)

    @Provides
    @Singleton
    fun getTransferApi(retrofit: Retrofit): MoneyTransferApi = retrofit.create(MoneyTransferApi::class.java)

    @Provides
    @Singleton
    fun getProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)

}