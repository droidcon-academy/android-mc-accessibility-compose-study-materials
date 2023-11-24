package com.droidcon.alldone.repository

import arrow.core.Either
import com.droidcon.alldone.model.ToDoItem

interface Repository {
    suspend fun fetchToDoList(): Either<RepositoryErrorType, List<ToDoItem>>
    suspend fun saveToDoList(list: List<ToDoItem>): Either<RepositoryErrorType, Boolean>
    suspend fun updateItem(item: ToDoItem): Either<RepositoryErrorType, List<ToDoItem>>
    suspend fun deleteItem(item: ToDoItem): Either<RepositoryErrorType, ToDoItem>
}