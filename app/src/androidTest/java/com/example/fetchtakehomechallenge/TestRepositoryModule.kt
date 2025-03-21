package com.example.fetchtakehomechallenge

import dagger.Module
import dagger.Provides
import com.example.fetchtakehomechallenge.di.NetworkModule
import com.example.fetchtakehomechallenge.repository.FakeItemRepository
import com.example.fetchtakehomechallenge.repository.ItemRepository
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestRepositoryModule {

    @Provides
    @Singleton
    @Named("test_repository")
    fun provideFakeItemRepository(): ItemRepository {
        return FakeItemRepository()
    }
}
