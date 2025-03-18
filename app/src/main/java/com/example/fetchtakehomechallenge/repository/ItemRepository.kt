package com.example.fetchtakehomechallenge.repository

import com.example.fetchtakehomechallenge.data.Item

interface ItemRepository {
    suspend fun getItems(): List<Item>
}
