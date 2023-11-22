package com.droidcon.alldone.repository

import arrow.core.Either
import com.droidcon.alldone.model.ToDoItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class FileRepository(private val editor: Editor) : Repository {

    private val moshi: Moshi by lazy {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    private val toDoListType: Type by lazy {
        Types.newParameterizedType(
            List::class.java,
            ToDoItem::class.java
        )
    }

    private val toDoListAdapter: JsonAdapter<List<ToDoItem>> by lazy {
        moshi.adapter(toDoListType)
    }

    override suspend fun fetchToDoList(): Either<RepositoryErrorType, List<ToDoItem>> {
        val readResult = editor.read()
        return readResult.fold(
            { error ->
                Either.Left(error)
            },
            { jsonAsString ->
                if (jsonAsString.isBlank()) {
                    Either.Right(emptyList())
                } else {
                    try {
                        val obj = toDoListAdapter.fromJson(jsonAsString) ?: return Either.Left(
                            RepositoryErrorType.DATA_PARSE
                        )
                        Either.Right(obj)
                    } catch (exception: IOException) {
                        Either.Left(RepositoryErrorType.DATA_PARSE)
                    }
                }
            }
        )
    }

    override suspend fun saveToDoList(list: List<ToDoItem>): Either<RepositoryErrorType, Boolean> {
        val jsonString = toDoListAdapter.toJson(list)
        return editor.writeToFile(jsonString).fold(
            { error ->
                Either.Left(error)
            },
            { success ->
                if (success) {
                    Either.Right(true)
                } else {
                    Either.Left(RepositoryErrorType.DATA_WRITE)
                }
            }
        )
    }

    override suspend fun updateItem(item: ToDoItem): Either<RepositoryErrorType, List<ToDoItem>> =
        fetchToDoList().fold(
            { error ->
                Either.Left(error)
            }, { successFetchList ->
                if (item.id == -1 || item.id !in successFetchList.map { it.id }) {
                    val updatedList = successFetchList
                        .toMutableList()
                        .also {
                            it.add(
                                if (item.id == -1) {
                                    val newId = if (successFetchList.isEmpty()) {
                                        1
                                    } else {
                                        1 + successFetchList.maxOf { idItemIterator -> idItemIterator.id }
                                    }
                                    item.copy(id = newId)
                                } else {
                                    item
                                }
                            )
                        }.sortedBy { it.id }
                    saveToDoList(updatedList).fold(
                        { error -> Either.Left(error) },
                        { _ -> Either.Right(updatedList) }
                    )
                } else {
                    val itemIndex =
                        successFetchList.indexOfFirst { currentItem -> currentItem.id == item.id }
                    if (itemIndex < 0) {
                        Either.Left(RepositoryErrorType.UNKNOWN_ELEMENT)
                    } else {
                        val updatedList = successFetchList
                            .toMutableList()
                            .also {
                                it.removeAt(itemIndex)
                                it.add(itemIndex, item)
                            }
                        saveToDoList(updatedList).fold(
                            { error -> Either.Left(error) },
                            { _ -> Either.Right(updatedList) }
                        )
                    }
                }
            }
        )

    override suspend fun deleteItem(item: ToDoItem): Either<RepositoryErrorType, ToDoItem> =
        fetchToDoList().fold(
            { error ->
                Either.Left(error)
            }, { successFetchList ->
                val index =
                    successFetchList.indexOfFirst { currentItem -> currentItem.id == item.id }
                if (index < 0) {
                    Either.Left(RepositoryErrorType.UNKNOWN_ELEMENT)
                } else {
                    val updatedList = successFetchList
                        .toMutableList()
                        .apply { removeAt(index) }
                        .toList()
                    saveToDoList(updatedList).fold(
                        { error -> Either.Left(error) },
                        { _ -> Either.Right(item) }
                    )
                }
            }
        )
}