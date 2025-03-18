package com.example.fetchtakehomechallenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fetchtakehomechallenge.data.Item
import androidx.lifecycle.viewModelScope
import com.example.fetchtakehomechallenge.network.RetrofitClient
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _itemsLiveData = MutableLiveData<Map<Int, List<Item>>>()
    val itemsLiveData: LiveData<Map<Int, List<Item>>> = _itemsLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchItems() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val items = RetrofitClient.apiService.getItems()

                //filter null or blank
                val filteredItems = items.filter { !it.name.isNullOrBlank() }
                //group by listId, sort by name within group
                val groupedItems = filteredItems
                    .groupBy { it.listId }
                    .mapValues { (_, items) ->
                        items.sortedBy { it.name }
                    }

                _itemsLiveData.value = groupedItems
            }   catch (e: Exception) {
                _error.value = "Error fetching data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
