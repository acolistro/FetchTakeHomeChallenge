package com.example.fetchtakehomechallenge

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fetchtakehomechallenge.data.Item
import com.example.fetchtakehomechallenge.repository.FakeItemRepository
import com.example.fetchtakehomechallenge.repository.ItemRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var repository: ItemRepository

    @Before
    fun setUp() {
        hiltRule.inject()

        if (repository is FakeItemRepository) {
            (repository as FakeItemRepository).itemsToReturn = listOf(
                Item(1, 1, "Item A"),
                Item(2, 1, "Item B"),
                Item(3, 2, "Item C")
            )
        }
    }

    //test fails if there are issues with activity creation
    @Test
    fun activity_launches_successfully() {}

    //indirect test, verifies theme is applied
    @Test
    fun activity_displays_theme_and_surfaces() {
        composeRule.waitForIdle()
        composeRule.onNodeWithContentDescription("Swipe down to refresh items list").assertExists()
    }
}
