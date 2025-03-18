package com.example.fetchtakehomechallenge.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fetchtakehomechallenge.data.Item
import com.example.fetchtakehomechallenge.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = mock(ApiService::class.java)
        viewModel = MainViewModel(ItemRepositoryImpl(apiService))
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

        whenever(apiService.getItems()).thenReturn(mockItems)
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
