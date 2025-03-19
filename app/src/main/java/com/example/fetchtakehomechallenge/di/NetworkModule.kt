package com.example.fetchtakehomechallenge.di

import com.example.fetchtakehomechallenge.network.ApiService
import com.example.fetchtakehomechallenge.repository.ItemRepository
import com.example.fetchtakehomechallenge.repository.ItemRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideItemRepository(apiService: ApiService): ItemRepository {
        return ItemRepositoryImpl(apiService)
    }
}
