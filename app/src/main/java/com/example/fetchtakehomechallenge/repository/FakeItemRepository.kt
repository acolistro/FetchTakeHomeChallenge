package com.example.fetchtakehomechallenge.repository

import com.example.fetchtakehomechallenge.data.Item
import kotlinx.coroutines.delay
import java.io.IOException

class FakeItemRepository :ItemRepository {
    var itemsToReturn = listOf<Item>()
    var shouldThrowError = false
    var errorMessage = "Test error"
    var loadingDelay = 0L

    override suspend fun getItems(): List<Item> {
        if (loadingDelay > 0) {
            delay(loadingDelay)
        }

        if (shouldThrowError) {
            throw IOException(errorMessage)
        }

        return itemsToReturn
    }
}
