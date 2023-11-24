package com.droidcon.alldone.utils

import com.droidcon.alldone.model.ToDoCategory
import com.droidcon.alldone.model.ToDoItem

object OrganiseToDoList {
    operator fun invoke(list: List<ToDoItem>): Map<ToDoCategory, List<ToDoItem>> =
        list.groupBy { it.category }
}