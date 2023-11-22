package com.droidcon.alldone.ui.screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.droidcon.alldone.R
import com.droidcon.alldone.model.ToDoCategory
import com.droidcon.alldone.model.ToDoItem
import com.droidcon.alldone.ui.component.EmptyList
import com.droidcon.alldone.ui.component.ErrorMessage
import com.droidcon.alldone.ui.component.LoadingAnimation
import com.droidcon.alldone.ui.component.ToDoList
import com.droidcon.alldone.ui.theme.AllDoneTheme
import com.droidcon.alldone.utils.EditMode
import com.droidcon.alldone.utils.OrganiseToDoList
import com.droidcon.alldone.viewmodel.ToDoListViewModel

@Composable
fun ToDoListScreen(
    uiState: ToDoListViewModel.ToDoListUiState,
    editItem: (ToDoItem, EditMode) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    showSnackbar: (String, String, () -> Unit) -> Unit
) {
    when (uiState) {
        ToDoListViewModel.ToDoListUiState.EmptyList -> {
            EmptyList(modifier)
        }

        is ToDoListViewModel.ToDoListUiState.ErrorState -> {
            ErrorMessage(stringResource(id = R.string.error_message), modifier)
        }

        ToDoListViewModel.ToDoListUiState.Loading -> {
            LoadingAnimation(modifier)
        }

        is ToDoListViewModel.ToDoListUiState.ToDoList -> {
            ToDoList(uiState.list, listState, modifier) { newItem, editMode ->
                editItem(newItem, editMode)
            }
            uiState.recentlyDeleted?.apply {
                showSnackbar(
                    stringResource(id = R.string.label_item_deleted, this.title),
                    stringResource(id = R.string.cta_undo)
                ) {
                    editItem(this, EditMode.IN_PLACE)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ToDoListScreenPreview() {
    AllDoneTheme {
        Surface {
            ToDoListScreen(
                uiState = ToDoListViewModel.ToDoListUiState.ToDoList(
                    list = OrganiseToDoList(listOf(
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
                    ))
                ),
                editItem = {_,_ -> },
                listState = rememberLazyListState(),
                showSnackbar = {_,_,_ -> }
            )
        }
    }
}