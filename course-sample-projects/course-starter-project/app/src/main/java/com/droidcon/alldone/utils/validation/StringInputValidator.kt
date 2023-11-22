package com.droidcon.alldone.utils.validation

import arrow.core.Either

abstract class StringInputValidator() : Validator<String> {

    abstract val minimumLength: Int
    override fun invoke(input: String): Either<ValidationError, Unit> =
        if (input.length < minimumLength) {
            Either.Left(ValidationError.TOO_SHORT)
        } else {
            Either.Right(Unit)
        }
}