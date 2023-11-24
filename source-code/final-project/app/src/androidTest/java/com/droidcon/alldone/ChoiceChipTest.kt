package com.droidcon.alldone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.droidcon.alldone.ui.component.ChoiceChip
import com.droidcon.alldone.ui.theme.AllDoneTheme
import org.junit.Rule
import org.junit.Test

class ChoiceChipTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createTestScenario(initialCheckedState: Boolean = false) {
        composeTestRule.setContent {
            AllDoneTheme {
                var checked by remember { mutableStateOf(initialCheckedState) }
                Column(modifier = Modifier.fillMaxSize()) {
                    ChoiceChip(text = "Test chip",
                        checked = checked,
                        onValueChange = {checked = !checked})
                }
            }
        }
    }

    @Test
    fun testChoiceChipSize() {
        createTestScenario()

        with(composeTestRule.onNodeWithText("Test chip")) {
            assertIsDisplayed()

            assertHeightIsAtLeast(36.dp)
            assertWidthIsAtLeast(36.dp)
        }
    }

    @Test
    fun testChoiceChipToggleable() {
        createTestScenario()

        with(composeTestRule.onNodeWithText("Test chip")) {
            assertIsDisplayed()
            assertHasRole(Role.Switch)
            assertIsToggleable()

            assertIsOff()
            performClick()
            assertIsOn()
        }
    }
}
