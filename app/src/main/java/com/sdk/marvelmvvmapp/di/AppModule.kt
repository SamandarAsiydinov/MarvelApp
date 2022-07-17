package com.sdk.marvelmvvmapp.di

import com.sdk.marvelmvvmapp.data.data_source.MarvelApi
import com.sdk.marvelmvvmapp.data.repository.MarvelRepositoryImpl
import com.sdk.marvelmvvmapp.domain.repository.MarvelRepository
import com.sdk.marvelmvvmapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideGsonConvertor(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
    @Provides
    @Singleton
    fun provideMarvelApi(retrofit: Retrofit): MarvelApi = retrofit.create(MarvelApi::class.java)

    @Provides
    @Singleton
    fun provideMarvelRepository(api: MarvelApi): MarvelRepository {
        return MarvelRepositoryImpl(api)
    }
}