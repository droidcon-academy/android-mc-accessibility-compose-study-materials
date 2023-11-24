package com.droidcon.alldone.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcon.alldone.ui.theme.AllDoneTheme

/**
 * @see https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#radiobutton
 */
object RadioButtonGroup {
    @Composable
    operator fun <V, T: Describable<V>> invoke(
        radioOptions: List<T>,
        selectedOptionIndex: Int = 0,
        modifier: Modifier = Modifier,
        onSelectionChanged: (V) -> Unit,
    ) {
        // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
        Column(modifier.selectableGroup()) {
            radioOptions.forEach { option ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = (radioOptions.indexOf(option) == selectedOptionIndex),
                            onClick = {
                                onSelectionChanged(option.value)
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (radioOptions.indexOf(option) == selectedOptionIndex),
                        onClick = null // null recommended for accessibility with screen readers
                    )
                    Text(
                        text = option.description,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }

    interface Describable<T> {
        val value: T
        val description: String
    }
}

@Preview
@Composable
private fun RadioButtonGroupPreview() {
    AllDoneTheme {
        Surface {
            RadioButtonGroup(
                radioOptions = listOf(
                    object: RadioButtonGroup.Describable<String>{
                        override val value: String = "Item 1"
                        override val description: String = "Item 1"


                    },
                    object: RadioButtonGroup.Describable<String>{
                        override val value: String = "Item 2"
                        override val description: String = "Item 2"
                    }
                ),
                onSelectionChanged = {}
            )
        }
    }
}