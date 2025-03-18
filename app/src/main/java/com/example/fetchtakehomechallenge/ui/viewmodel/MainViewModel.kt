package com.example.fetchtakehomechallenge.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchtakehomechallenge.data.ItemsUiState
import com.example.fetchtakehomechallenge.network.RetrofitClient
import com.example.fetchtakehomechallenge.network.RetrofitClient.apiService
import com.example.fetchtakehomechallenge.repository.ItemRepository
import com.example.fetchtakehomechallenge.repository.ItemRepositoryImpl
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ItemRepository = ItemRepositoryImpl(RetrofitClient.apiService)
) : ViewModel() {
    private val _uiState = mutableStateOf(ItemsUiState())
    val uiState: State<ItemsUiState> = _uiState

    init {
        fetchItems()
    }

    fun fetchItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val items = repository.getItems()

                //filter null or blank
                val filteredItems = items.filter { !it.name.isNullOrBlank() }
                //group by listId, sort by name within group
                val groupedItems = filteredItems
                    .groupBy { it.listId }
                    .mapValues { (_, items) ->
                        items.sortedBy { it.name }
                    }

                _uiState.value = ItemsUiState(
                    groupedItems = groupedItems,
                    isLoading = false,
                    error = null
                )
            }   catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error fetching data: ${e.message}"
                )
            }
        }
    }
}
