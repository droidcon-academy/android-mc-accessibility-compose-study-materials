package com.droidcon.alldone.repository

import arrow.core.Either

interface Editor {
    fun read(): Either<RepositoryErrorType, String>
    fun writeToFile(data: String): Either<RepositoryErrorType, Boolean>
}