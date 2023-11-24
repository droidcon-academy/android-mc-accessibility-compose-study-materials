package com.droidcon.alldone.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcon.alldone.R
import com.droidcon.alldone.model.ToDoCategory
import com.droidcon.alldone.ui.theme.AllDoneTheme

@Composable
fun ToDoCategoryFilter(
    selectedCategories: List<ToDoCategory>,
    modifier: Modifier = Modifier,
    onValueChange: (List<ToDoCategory>) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(ToDoCategory.values()) { category ->
                ChoiceChip(
                    text = stringResource(
                        id = when (category) {
                            ToDoCategory.PERSONAL -> R.string.category_personal
                            ToDoCategory.PROFESSIONAL -> R.string.category_professional
                            ToDoCategory.SOCIAL -> R.string.category_social
                            ToDoCategory.TRAVEL -> R.string.category_travel
                        }
                    ),
                    checked = category in selectedCategories,
                    onValueChange = {
                        onValueChange(
                            selectedCategories.toMutableList().apply {
                                if (it) {
                                    add(category)
                                } else {
                                    remove(category)
                                }
                            }
                        )
                    }
                )
            }
        }
    )
}

@Preview
@Composable
private fun ToDoCategoryFilterPreview() {
    Surface {
        AllDoneTheme {
            Row {
                ToDoCategoryFilter(selectedCategories = ToDoCategory.values().toList()) {}
            }
        }
    }
}