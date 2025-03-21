package com.example.fetchtakehomechallenge.network

import com.example.fetchtakehomechallenge.data.Item
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}
