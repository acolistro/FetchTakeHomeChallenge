package com.example.fetchtakehomechallenge.ui.viewmodel

import com.example.fetchtakehomechallenge.data.Item
import com.example.fetchtakehomechallenge.network.ApiService
import com.example.fetchtakehomechallenge.repository.ItemRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @Mock
    private lateinit var repository: ItemRepository

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = mock(ApiService::class.java)
        viewModel = MainViewModel(repository)
        //viewModel = MainViewModel(ItemRepositoryImpl(apiService))
    }

    @Test
    fun `fetchItems returns filtered and sorted data when api call is successful`() = runTest {
        val mockItems = listOf(
            Item(1, 1, "Item A"),
            Item(2, 1, "Item B"),
            Item(3, 1, null),
            Item(4, 2, ""),
            Item(5, 2, "Item C"),
            Item(6, 2, "Item D")
        )

        whenever(repository.getItems()).thenReturn(mockItems)
        viewModel.fetchItems()
        advanceUntilIdle()
        val uiState = viewModel.uiState.value

        assertFalse(uiState.isLoading)
        assertNull(uiState.error)

        val expectedGroupedItems = mapOf(
            1 to listOf(
                Item(1, 1, "Item A"),
                Item(2, 1, "Item B")
            ),
            2 to listOf(
                Item(5, 2, "Item C"),
                Item(6, 2, "Item D")
            )
        )

        assertEquals(expectedGroupedItems, uiState.groupedItems)
    }

    @Test
    fun `fetchItems shows error when API call fails`() = runTest {
        val errorMessage = "Network error"

        whenever(repository.getItems()).thenThrow(RuntimeException(errorMessage))
        viewModel.fetchItems()
        advanceUntilIdle()
        val uiState = viewModel.uiState.value

        assertFalse(uiState.isLoading)
        assertTrue(uiState.error?.contains(errorMessage) == true)
        assertEquals(emptyMap<Int, List<Item>>(), uiState.groupedItems)
    }
}

class TestCoroutineRule : TestWatcher() {
    private val testDispatcher = StandardTestDispatcher()

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }
}
