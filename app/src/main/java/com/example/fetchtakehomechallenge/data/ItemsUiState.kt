package com.example.fetchtakehomechallenge.data

data class ItemsUiState(
    val groupedItems: Map<Int, List<Item>> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? =null
)
