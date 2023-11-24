package com.droidcon.alldone

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.droidcon.alldone.ui.theme.AllDoneTheme
import com.droidcon.alldone.utils.preview.FontScalePreview
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
                        onValueChange = { checked = !checked })
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

@Composable
fun ChoiceChip(text: String, checked: Boolean, onValueChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .toggleable(
                role = Role.Switch,
                value = checked,
                onValueChange = onValueChange
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.primary
            )
            .clip(RoundedCornerShape(20.dp))
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 12.dp)
                .align(Alignment.CenterVertically),
            imageVector = Icons.Outlined.AddCircle,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 6.dp, end = 12.dp),
            text = text
        )
    }
}

@FontScalePreview
@Composable
private fun ChoiceChipPreview() {
    Surface {
        AllDoneTheme {
            Column {
                ChoiceChip("Checked", true) {}
                Spacer(modifier = Modifier.width(5.dp))
                ChoiceChip("Not Checked", false) {}
            }
        }
    }
}