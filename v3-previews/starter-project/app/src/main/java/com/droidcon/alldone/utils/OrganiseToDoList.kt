package com.droidcon.alldone.utils

import com.droidcon.alldone.model.ToDoItem

object OrganiseToDoList {
    operator fun invoke(list: List<ToDoItem>): List<ToDoItem> = list.sortedBy { it.category.name }
}