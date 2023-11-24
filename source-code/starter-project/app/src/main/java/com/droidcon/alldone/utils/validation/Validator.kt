package com.droidcon.alldone.utils.validation

import arrow.core.Either

/**
 * A validator pattern implemented with arrow
 */
interface Validator<T> {
    /**
     * @param input: the type to be validated
     * @returns: An [Either] object that can be folded into distinct user flows
     */
    operator fun invoke(input: T): Either<ValidationError, Unit>
}