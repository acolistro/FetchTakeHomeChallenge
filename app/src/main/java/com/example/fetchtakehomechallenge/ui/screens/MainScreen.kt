package com.example.fetchtakehomechallenge.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fetchtakehomechallenge.data.Item
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.testTag
import com.example.fetchtakehomechallenge.ui.viewmodel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = uiState.isLoading
    )

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.fetchItems() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .testTag("mainBoxContainer")
        ) {
            when {
                uiState.isLoading && uiState.groupedItems.isEmpty() -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.error != null -> {
                    Text(
                        text = "No items found",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                else -> {
                    ItemList(
                        groupedItems = uiState.groupedItems,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

    @Composable
    fun ItemList(
        groupedItems: Map<Int, List<Item>>,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(modifier = modifier) {
            groupedItems.forEach { (listId, items) ->
                item {
                    GroupHeader(listId = listId)
                }

                items(items) { item ->
                    ItemRow(item = item)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    @Composable
    fun GroupHeader(listId: Int) {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "List ID: $listId",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

        }
    }

    @Composable
    fun ItemRow(item: Item) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "ID: ${item.id}")
                Text(text = "Name: ${item.name}")
            }
        }
    }


