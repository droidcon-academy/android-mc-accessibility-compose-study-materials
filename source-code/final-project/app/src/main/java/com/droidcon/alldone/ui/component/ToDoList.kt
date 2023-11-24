package com.droidcon.alldone.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.alldone.R
import com.droidcon.alldone.model.ToDoCategory
import com.droidcon.alldone.model.ToDoItem
import com.droidcon.alldone.ui.theme.AllDoneTheme
import com.droidcon.alldone.utils.EditMode
import com.droidcon.alldone.utils.OrganiseToDoList

@Composable
fun ToDoList(
    toDoItems: Map<ToDoCategory, List<ToDoItem>>,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    updateItem: (ToDoItem, EditMode) -> Unit
) {
    var categoryFilter by remember { mutableStateOf(ToDoCategory.values().toList()) }

    Column {
        Text(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.app_title),
            modifier = Modifier
                .padding(start = 12.dp)
                .semantics { heading() }
        )
        ToDoCategoryFilter(
            modifier = Modifier
                .padding(horizontal = 12.dp),
            selectedCategories = categoryFilter
        ) {
            categoryFilter = it
        }
        LazyColumn(
            state = listState,
            modifier = modifier,
            content = {
                categoryFilter.forEach { toDoCategory ->
                    if (toDoItems.containsKey(toDoCategory) && toDoItems[toDoCategory]?.isEmpty() == false) {
                        item {
                            Row {
                                Spacer(modifier = Modifier.width(5.dp))
                                Icon(
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                        .height(22.dp)
                                        .width(22.dp)
                                        .align(Alignment.CenterVertically),
                                    painter = painterResource(
                                        id = when (toDoCategory) {
                                            ToDoCategory.PERSONAL -> R.drawable.ic_category_personal
                                            ToDoCategory.PROFESSIONAL -> R.drawable.ic_category_professional
                                            ToDoCategory.SOCIAL -> R.drawable.ic_category_social
                                            ToDoCategory.TRAVEL -> R.drawable.ic_category_travel
                                        }
                                    ), contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    modifier = Modifier
                                        .testTag("Heading")
                                        .semantics { heading() },
                                    text = stringResource(
                                        when (toDoCategory) {
                                            ToDoCategory.PERSONAL -> R.string.category_personal
                                            ToDoCategory.PROFESSIONAL -> R.string.category_professional
                                            ToDoCategory.SOCIAL -> R.string.category_social
                                            ToDoCategory.TRAVEL -> R.string.category_travel
                                        }
                                    ),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        items(toDoItems[toDoCategory]!!) { toDoItem ->
                            ToDoListItem(toDoItem) { updatedItem, editMode ->
                                updateItem(
                                    updatedItem,
                                    editMode
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            })
    }
}

@Preview
@Composable
private fun ToDoListPreview() {
    AllDoneTheme {
        Surface {
            ToDoList(
                toDoItems = OrganiseToDoList(listOf(
                    ToDoItem(-1, "Personal 1", "Personal 1 description",ToDoCategory.PERSONAL),
                    ToDoItem(-1, "Personal 2", "Personal 2 description",ToDoCategory.PERSONAL),
                    ToDoItem(-1, "Travel 1", "Travel 1 description",ToDoCategory.TRAVEL),
                    ToDoItem(-1, "Travel 2", "Travel 2 description",ToDoCategory.TRAVEL),
                    ToDoItem(-1, "Travel 3", "Travel 3 description",ToDoCategory.TRAVEL),
                    ToDoItem(-1, "Travel 4", "Travel 4 description",ToDoCategory.TRAVEL),
                    ToDoItem(-1, "Social 1", "Social 1 description",ToDoCategory.SOCIAL),
                    ToDoItem(-1, "Social 2", "Social 2 description",ToDoCategory.SOCIAL),
                    ToDoItem(-1, "Social 3", "Social 3 description",ToDoCategory.SOCIAL),
                    ToDoItem(-1, "Professional 1", "Professional 1 description",ToDoCategory.PROFESSIONAL),
                    ToDoItem(-1, "Professional 2", "Professional 2 description",ToDoCategory.PROFESSIONAL),
                    ToDoItem(-1, "Professional 3", "Professional 3 description",ToDoCategory.PROFESSIONAL),
                    ToDoItem(-1, "Professional 4", "Professional 4 description",ToDoCategory.PROFESSIONAL),
                    ToDoItem(-1, "Professional 5", "Professional 5 description",ToDoCategory.PROFESSIONAL),
                )),
                listState = rememberLazyListState(),
                updateItem = { _, _ -> }
            )
        }
    }
}