package de.reinhardy.tastytask.util

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat


@Suppress("UNCHECKED_CAST")
infix fun <T, U : T> T.shouldBe(expected: U?): T {
    assertThat(this, `is`(expected))
    return this
}