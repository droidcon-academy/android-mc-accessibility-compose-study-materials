package com.droidcon.alldone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.droidcon.alldone.model.ToDoItem
import com.droidcon.alldone.ui.component.ToDoListItem
import com.droidcon.alldone.ui.theme.AllDoneTheme
import org.junit.Rule
import org.junit.Test

class ToDoListItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testToDoListItem() {
        val testItem = ToDoItem(-1, "Test Item 1", "Test Item Description")
        composeTestRule.setContent {
            AllDoneTheme {
                var item by remember { mutableStateOf(testItem) }
                Column(modifier = Modifier.fillMaxSize()) {
                    ToDoListItem(
                        toDoItem = item,
                        updateItem = { _, _ ->
                            item = item.copy(
                                complete = !item.complete
                            )
                        })
                }
            }
        }

        val editDescription = "Edit ${testItem.title}"
        val shareDescription = "Share ${testItem.title}"
        val calendarDescription = "Add ${testItem.title} to calendar"

        with(composeTestRule) {

            onNodeWithText("Test Item 1").assertIsDisplayed()

            // Find a node by it's content description
            // Check is displayed
            // Check minimum height and width
            // Check is available for user interaction
            onNodeWithContentDescription(
                label = editDescription,
                useUnmergedTree = true
            ).onParent().apply {
                assertIsDisplayed()
                assertHeightIsAtLeast(48.dp)
                assertWidthIsAtLeast(48.dp)
                assertHasClickAction()
            }

            onNodeWithContentDescription(
                label = shareDescription,
                useUnmergedTree = true
            ).onParent().apply {
                assertIsDisplayed()
                assertHeightIsAtLeast(48.dp)
                assertWidthIsAtLeast(48.dp)
                assertHasClickAction()
            }

            onNodeWithContentDescription(
                label = calendarDescription,
                useUnmergedTree = true
            ).onParent().apply {
                assertIsDisplayed()
                assertHeightIsAtLeast(48.dp)
                assertWidthIsAtLeast(48.dp)
                assertHasClickAction()
            }

            // Find a node by it's test tag
            // assert it has no click action
            onNodeWithTag(
                "OutlinedCard_ToDoListItem_Checkbox",
                useUnmergedTree = true
            ).apply {
                    assertIsDisplayed()
                    assertHasNoClickAction()
                }

            // assert that the element has an action
            // assert the element is toggleable
            // assert the element state is off
            // assert the element has a Checkbox role
            onNodeWithTag(
                testTag = "OutlinedCard_ToDoListItem",
                useUnmergedTree = true
            ).apply {
                assertIsDisplayed()
                assertHasClickAction()
                assertIsToggleable()
                assertIsOff()
                assertHasRole(Role.Checkbox)

                assertHasAccessibilityAction(editDescription)
                assertHasAccessibilityAction(shareDescription)
                assertHasAccessibilityAction(calendarDescription)
            }

            // perform a click action

            onNodeWithTag(
                testTag = "OutlinedCard_ToDoListItem_Checkbox",
                useUnmergedTree = true
            ).performClick()

            // assert the element state on
            onNodeWithTag(
                testTag = "OutlinedCard_ToDoListItem",
                useUnmergedTree = true
            ).assertIsOn()

        }
    }
}
