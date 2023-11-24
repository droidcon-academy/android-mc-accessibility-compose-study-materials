package com.droidcon.alldone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droidcon.alldone.model.ToDoCategory
import com.droidcon.alldone.model.ToDoItem
import com.droidcon.alldone.ui.screen.ToDoEditScreen
import com.droidcon.alldone.ui.theme.AllDoneTheme
import com.droidcon.alldone.utils.parcelable
import com.droidcon.alldone.viewmodel.ToDoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDoEditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toDoItem = intent.extractToDoItem()
        setContent {
            AllDoneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val toDoListViewModel: ToDoListViewModel = viewModel()


                    ToDoEditScreen(
                        isEditing = toDoItem.id != -1,
                        title = toDoItem.title,
                        description = toDoItem.description,
                        category = toDoItem.category,
                        onSave = { newTitle, newDescription, newCategory ->
                            toDoListViewModel.editItem(
                                toDoItem.copy(
                                    title = newTitle,
                                    description = newDescription,
                                    category = newCategory
                                )
                            )
                            setResult(RESULT_OK)
                            finish()
                        },
                        onDelete = {
                            toDoListViewModel.deleteItem(toDoItem)
                            setResult(
                                RESULT_OK,
                                Intent().also { it.putExtra(toDoItemDataKey, toDoItem) })
                            finish()
                        })
                }
            }
        }
    }

    companion object {
        fun createIntent(
            context: Context,
            toDoItem: ToDoItem = ToDoItem(
                id = -1,
                title = "",
                description = "",
                category = ToDoCategory.PERSONAL
            )
        ): Intent = Intent(
            context,
            ToDoEditActivity::class.java
        ).also {
            it.putExtra(toDoItemDataKey, toDoItem)
        }

        fun Intent.extractToDoItem(): ToDoItem =
            extras?.parcelable(toDoItemDataKey)
                ?: throw RuntimeException("Cannot pass null data")

        private const val toDoItemDataKey = "toDoItemDataKey"
    }
}