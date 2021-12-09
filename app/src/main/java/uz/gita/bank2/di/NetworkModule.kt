package uz.gita.bank2.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.bank2.BuildConfig.BASE_URL
import uz.gita.bank2.data.pref.MyPref
import uz.gita.bank2.data.retrofit.api.AuthApi
import uz.gita.bank2.utils.addHeaderInterceptor
import uz.gita.bank2.utils.addLoggingInterceptor
import uz.gita.bank2.utils.refreshInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @[Provides Singleton]
    fun getAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @[Provides Singleton]
    fun getRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @[Provides Singleton]
    fun getOkHttpClient(pref: MyPref, @ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .addLoggingInterceptor(context)
            .addInterceptor(refreshInterceptor(pref))
            .addInterceptor(addHeaderInterceptor(pref))
            .build()

}