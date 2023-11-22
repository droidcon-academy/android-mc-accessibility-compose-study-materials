package com.droidcon.alldone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droidcon.alldone.ToDoEditActivity.Companion.extractToDoItem
import com.droidcon.alldone.ui.screen.ToDoListScreen
import com.droidcon.alldone.ui.theme.AllDoneTheme
import com.droidcon.alldone.utils.EditMode
import com.droidcon.alldone.utils.isScrollingUp
import com.droidcon.alldone.viewmodel.ToDoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToDoListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllDoneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val toDoListViewModel: ToDoListViewModel = viewModel()
                    val uiState by toDoListViewModel.uiState.collectAsStateWithLifecycle()
                    val listState = rememberLazyListState()
                    val context = LocalContext.current
                    val toDoCreateEditLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartActivityForResult(),
                        onResult = {
                            toDoListViewModel.fetchToDoItems(it.data?.extractToDoItem())
                        }
                    )
                    val snackbarHostState = remember { SnackbarHostState() }
                    val scope = rememberCoroutineScope()
                    Scaffold(
                        snackbarHost = { SnackbarHost(snackbarHostState) },

                        floatingActionButton = {
                            ExtendedFloatingActionButton(
                                text = { Text(text = stringResource(id = R.string.compose_todo)) },
                                icon = { Icon(Icons.Filled.Edit, stringResource(id = R.string.compose_todo)) },
                                expanded = listState.isScrollingUp(),
                                onClick = {
                                    toDoCreateEditLauncher.launch(
                                        ToDoEditActivity.createIntent(context)
                                    )
                                })
                        }
                    ) { contentPadding ->
                        Box(
                            modifier = Modifier
                                .padding(contentPadding)
                                .fillMaxWidth()
                        ) {
                            ToDoListScreen(
                                uiState = uiState,
                                editItem = { newItem, editMode ->
                                    if (editMode == EditMode.IN_PLACE) {
                                        toDoListViewModel.editItem(newItem)
                                    } else {
                                        toDoCreateEditLauncher.launch(
                                            ToDoEditActivity.createIntent(context, newItem)
                                        )
                                    }
                                },
                                listState = listState
                            ) { message, label, action ->
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = message,
                                        actionLabel = label,
                                        withDismissAction = true
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        action.invoke()
                                    }
                                }
                            }
                        }
                    }
                    LaunchedEffect(key1 = 0) {
                        toDoListViewModel.fetchToDoItems()
                    }
                }
            }
        }
    }
}