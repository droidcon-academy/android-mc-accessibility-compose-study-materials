package com.droidcon.alldone

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import com.droidcon.alldone.model.ToDoCategory
import com.droidcon.alldone.model.ToDoItem
import com.droidcon.alldone.ui.component.ToDoList
import com.droidcon.alldone.ui.theme.AllDoneTheme
import com.droidcon.alldone.utils.OrganiseToDoList
import org.junit.Rule
import org.junit.Test

class ToDoListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHeadings() {
        composeTestRule.setContent {
            AllDoneTheme {
                Surface {
                    ToDoList(
                        toDoItems = OrganiseToDoList(testData.filter { it.id <= 6 }),
                        listState = rememberLazyListState(),
                        updateItem = { _, _ -> }
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("To do list:")
            .assertIsHeading()

        composeTestRule.onAllNodesWithTag("Heading")
            .assertCountEquals(2)
            .assertAll(isHeading())
    }

    companion object {
        val testData = listOf(
            // Personal
            ToDoItem(
                id = 1,
                title = "Finish laundry",
                description = "Fold and put away all of the laundry.",
                category = ToDoCategory.PERSONAL
            ),
            ToDoItem(
                id = 2,
                title = "Clean the house",
                description = "Sweep, mop, and dust all of the rooms.",
                category = ToDoCategory.PERSONAL
            ),
            ToDoItem(
                id = 3,
                title = "Go for a walk",
                description = "Get some exercise and fresh air.",
                category = ToDoCategory.PERSONAL
            ),
            ToDoItem(
                id = 4,
                title = "Read a book",
                description = "Relax and enjoy a good book.",
                category = ToDoCategory.PERSONAL
            ),
            ToDoItem(
                id = 5,
                title = "Call mom",
                description = "Check in and see how she's doing.",
                category = ToDoCategory.PERSONAL
            ),

            // Social
            ToDoItem(
                id = 6,
                title = "Meet up with friends",
                description = "Get together for coffee or dinner to catch up.",
                category = ToDoCategory.SOCIAL
            ),
            ToDoItem(
                id = 7,
                title = "Go to a movie",
                description = "See a new movie in theaters.",
                category = ToDoCategory.SOCIAL
            ),
            ToDoItem(
                id = 8,
                title = "Have a party",
                description = "Throw a party for your friends and family.",
                category = ToDoCategory.SOCIAL
            ),
            ToDoItem(
                id = 9,
                title = "Volunteer",
                description = "Give back to your community by volunteering your time.",
                category = ToDoCategory.SOCIAL
            ),
            ToDoItem(
                id = 10,
                title = "Go to a concert",
                description = "See your favorite band or artist live.",
                category = ToDoCategory.SOCIAL
            ),

            // Travel
            ToDoItem(
                id = 11,
                title = "Book a flight",
                description = "Book a flight to your next vacation destination.",
                category = ToDoCategory.TRAVEL
            ),
            ToDoItem(
                id = 12,
                title = "Find a hotel",
                description = "Find a hotel to stay in at your destination.",
                category = ToDoCategory.TRAVEL
            ),
            ToDoItem(
                id = 13,
                title = "Plan your itinerary",
                description = "Decide what you want to see and do on your trip.",
                category = ToDoCategory.TRAVEL
            ),
            ToDoItem(
                id = 14,
                title = "Pack your bags",
                description = "Don't forget to pack everything you need for your trip.",
                category = ToDoCategory.TRAVEL
            ),
            ToDoItem(
                id = 15,
                title = "Take lots of photos",
                description = "Capture the memories of your trip with lots of photos.",
                category = ToDoCategory.TRAVEL
            ),

            // Professional
            ToDoItem(
                id = 16,
                title = "Finish the report",
                description = "Finish the report that you're working on.",
                category = ToDoCategory.PROFESSIONAL
            ),
            ToDoItem(
                id = 17,
                title = "Prepare for the presentation",
                description = "Prepare for your presentation next week.",
                category = ToDoCategory.PROFESSIONAL
            ),
            ToDoItem(
                id = 18,
                title = "Network with colleagues",
                description = "Attend networking events to meet new people and build relationships.",
                category = ToDoCategory.PROFESSIONAL
            ),
            ToDoItem(
                id = 19,
                title = "Update your resume",
                description = "Make sure your resume is up-to-date and ready for your next job search.",
                category = ToDoCategory.PROFESSIONAL
            ),
            ToDoItem(
                id = 20,
                title = "Learn a new skill",
                description = "Take a class or read a book to learn a new skill that will benefit you in your career.",
                category = ToDoCategory.PROFESSIONAL
            )
        )
    }
}