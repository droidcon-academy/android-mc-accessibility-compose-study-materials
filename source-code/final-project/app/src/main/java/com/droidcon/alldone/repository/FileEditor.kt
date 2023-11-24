package com.droidcon.alldone.repository

import android.content.Context
import arrow.core.Either
import java.io.FileNotFoundException
import java.io.IOException
import java.io.OutputStreamWriter

class FileEditor(private val context: Context, private val textFilePath: String) : Editor {
    override fun writeToFile(data: String): Either<RepositoryErrorType, Boolean> =
        try {
            context
                .openFileOutput(textFilePath, Context.MODE_PRIVATE)
                .use { fileOutputStream ->
                    OutputStreamWriter(fileOutputStream)
                        .use { outputStreamWriter ->
                            outputStreamWriter.write(data)
                        }
                }
            Either.Right(true)
        } catch (exception: IOException) {
            Either.Left(RepositoryErrorType.DATA_WRITE)
        }

    override fun read(): Either<RepositoryErrorType, String> =
        try {
            context
                .getFileStreamPath(textFilePath)
                .apply {
                    if (!exists()) {
                        createNewFile()
                    }
                }
            val fileAsString = context
                .openFileInput(textFilePath)
                .bufferedReader()
                .use {
                    it.readText()
                }

            Either.Right(fileAsString)
        } catch (exception: FileNotFoundException) {
            Either.Left(RepositoryErrorType.DATA_SOURCE_UNAVAILABLE)
        } catch (exception: IOException) {
            Either.Left(RepositoryErrorType.DATA_PARSE)
        }
}