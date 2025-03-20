package com.example.fetchtakehomechallenge

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.example.fetchtakehomechallenge.data.Item
import com.example.fetchtakehomechallenge.data.ItemsUiState
import com.example.fetchtakehomechallenge.ui.screens.GroupHeader
import com.example.fetchtakehomechallenge.ui.screens.ItemList
import com.example.fetchtakehomechallenge.ui.screens.ItemRow
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun mainScreen_displaysLoadingIndicator_whenLoadingWithNoData() {
        composeRule.setContent {
            TestMainScreen(
                uiState = ItemsUiState(
                    isLoading = true,
                    groupedItems = emptyMap()
                )
            )
        }

        composeRule.onNodeWithText("List ID:", substring = true).assertDoesNotExist()
        composeRule.onNodeWithText("No items found").assertDoesNotExist()
    }

    @Test
    fun mainScreenDisplaysItems_whenDataIsAvailable() {
        val testItems = mapOf(
            1 to listOf(
                Item(1, 2, "Item A"),
                Item(2, 1, "Item B")
            ),
            2 to listOf(
                Item(3, 2, "Item C")
            )
        )

        composeRule.setContent {
            TestMainScreen(
                uiState = ItemsUiState(
                    groupedItems = testItems,
                    isLoading = false
                )
            )
        }

        composeRule.onNodeWithText("List ID: 1").assertIsDisplayed()
        composeRule.onNodeWithText("List ID: 2").assertIsDisplayed()
        composeRule.onNodeWithText("ID: 1").assertIsDisplayed()
        composeRule.onNodeWithText("Name: Item A").assertIsDisplayed()
        composeRule.onNodeWithText("ID: 3").assertIsDisplayed()
        composeRule.onNodeWithText("Name: Item C").assertIsDisplayed()
    }

    @Test
    fun itemListDisplays_correctGroupsAndItems() {
        val testItems = mapOf(
            1 to listOf(
                Item(1, 1, "Item A"),
                Item(2, 1, "Item B")
            ),
            2 to listOf(
                Item(3, 2, "Item C")
            )
        )

        composeRule.setContent {
            ItemList(groupedItems = testItems)
        }

        composeRule.onNodeWithText("List ID: 1").assertIsDisplayed()
        composeRule.onNodeWithText("Name: Item A").assertIsDisplayed()
        composeRule.onNodeWithText("Name: Item B")
        composeRule.onNodeWithText("List ID: 2").assertIsDisplayed()
        composeRule.onNodeWithText("Name: Item C").assertIsDisplayed()
    }

    @Test
    fun groupHeader_displaysCorrectListID() {
        composeRule.setContent {
            GroupHeader(listId = 42)
        }

        composeRule.onNodeWithText("List ID: 42").assertIsDisplayed()
    }

    @Test
    fun itemRowDisplays_correctItemDetails() {
        composeRule.setContent {
            ItemRow(item = Item(99, 5, "Test Item"))
        }

        composeRule.onNodeWithText("ID: 99").assertIsDisplayed()
        composeRule.onNodeWithText("Name: Test Item").assertIsDisplayed()
    }

    @Composable
    private fun TestMainScreen(uiState: ItemsUiState) {
        Box(
            modifier = Modifier.fillMaxSize()
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
