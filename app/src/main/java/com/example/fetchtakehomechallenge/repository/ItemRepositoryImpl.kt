package com.example.fetchtakehomechallenge.repository

import com.example.fetchtakehomechallenge.data.Item
import com.example.fetchtakehomechallenge.network.ApiService

class ItemRepositoryImpl(private val apiService: ApiService) : ItemRepository {
    override suspend fun getItems(): List<Item> {
        return apiService.getItems()
    }
}
