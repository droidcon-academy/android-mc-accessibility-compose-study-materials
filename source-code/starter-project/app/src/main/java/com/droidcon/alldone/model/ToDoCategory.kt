package com.droidcon.alldone.model

enum class ToDoCategory {
    PERSONAL, SOCIAL, TRAVEL, PROFESSIONAL;
    companion object {
        fun fromString(name: String): ToDoCategory =
            ToDoCategory.values().firstOrNull {
                it.name.lowercase() == name.lowercase()
            } ?: throw RuntimeException("ToDoCategory does not exist: $name")
    }
}