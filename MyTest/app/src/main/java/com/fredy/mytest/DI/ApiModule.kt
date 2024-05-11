package com.fredy.mytest.DI

import com.fredy.mysavings.Feature.Data.APIs.CurrencyModels.TabScannerAPI
import com.fredy.mytest.Api.ApiCredentials
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
    ).readTimeout(
        15, TimeUnit.SECONDS
    ).connectTimeout(15, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideTabScannerApi(okHttpClient: OkHttpClient): TabScannerAPI = Retrofit.Builder().baseUrl(
            ApiCredentials.TabScanner.BASE_URL
        ).addConverterFactory(GsonConverterFactory.create()).client(
            okHttpClient
        ).build().create()

}