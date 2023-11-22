package com.droidcon.alldone

import com.droidcon.alldone.utils.validation.StringInputValidator
import com.droidcon.alldone.utils.validation.ValidationError
import com.google.common.truth.Truth.assertThat
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StringInputValidatorTest {
    private val testSubject = object: StringInputValidator() {
        override val minimumLength: Int = 3
    }

    @Test
    fun `given minimum valid input returns without error`() {
        testSubject("123").fold(
            {
                Assert.fail("Input was valid")
            },
            {
                assertThat(it).isEqualTo(Unit)
            }
        )
    }

    @Test
    fun `given minimum invalid input returns without error`() {
        testSubject("12").fold(
            {
                assertThat(it).isEqualTo(ValidationError.TOO_SHORT)
            },
            {
                Assert.fail("Input was invalid")
            }
        )
    }

    @Test
    fun `given empty input returns without error`() {
        testSubject("").fold(
            {
                assertThat(it).isEqualTo(ValidationError.TOO_SHORT)
            },
            {
                Assert.fail("Input was empty")
            }
        )
    }
}