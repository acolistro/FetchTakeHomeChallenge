package com.example.fetchtakehomechallenge

import com.example.fetchtakehomechallenge.di.NetworkModule
import com.example.fetchtakehomechallenge.repository.FakeItemRepository
import com.example.fetchtakehomechallenge.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideFakeItemRepository(): ItemRepository {
        return FakeItemRepository()
    }
}
