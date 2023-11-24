package com.droidcon.alldone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.alldone.model.ToDoItem
import com.droidcon.alldone.repository.Repository
import com.droidcon.alldone.utils.OrganiseToDoList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    private val _uiState = MutableStateFlow<ToDoListUiState>(ToDoListUiState.Loading)
    val uiState: StateFlow<ToDoListUiState> = _uiState.asStateFlow()

    init {
        fetchToDoItems()
    }

    fun fetchToDoItems(recentlyDeleted: ToDoItem? = null) {
        viewModelScope.launch {
            showLoading()
            repository.fetchToDoList().fold(
                { _ ->
                    _uiState.update { ToDoListUiState.ErrorState(ToDoListUiError.FETCH_ERROR) }
                },
                { toDoList ->
                    _uiState.update {
                        if (toDoList.isEmpty()) {
                            ToDoListUiState.EmptyList
                        } else {
                            ToDoListUiState.ToDoList(OrganiseToDoList(toDoList), recentlyDeleted)
                        }
                    }
                }
            )
        }
    }

    fun editItem(toDoItem: ToDoItem) {
        viewModelScope.launch {
            repository.updateItem(toDoItem).fold({ _ ->
                _uiState.update { ToDoListUiState.ErrorState(ToDoListUiError.SAVE_ERROR) }
            }, { success ->
                _uiState.update { ToDoListUiState.ToDoList(OrganiseToDoList(success)) }
            })
        }
    }

    fun deleteItem(toDoItem: ToDoItem) {
        viewModelScope.launch {
            repository.deleteItem(toDoItem).fold({ _ ->
                ToDoListUiState.ErrorState(ToDoListUiError.SAVE_ERROR)
            }, { })
        }
    }

    private fun showLoading() {
        _uiState.update { ToDoListUiState.Loading }
    }

    sealed class ToDoListUiState {
        object Loading : ToDoListUiState()
        object EmptyList : ToDoListUiState()
        data class ErrorState(val error: ToDoListUiError) : ToDoListUiState()
        data class ToDoList(
            val list: List<ToDoItem>,
            val recentlyDeleted: ToDoItem? = null
        ) : ToDoListUiState()
    }

    enum class ToDoListUiError {
        FETCH_ERROR, SAVE_ERROR
    }
}