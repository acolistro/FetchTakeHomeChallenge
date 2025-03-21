package com.example.fetchtakehomechallenge.repository

import com.example.fetchtakehomechallenge.data.Item
import com.example.fetchtakehomechallenge.network.ApiService
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ItemRepository {
    override suspend fun getItems(): List<Item> {
        return apiService.getItems()
    }
}
