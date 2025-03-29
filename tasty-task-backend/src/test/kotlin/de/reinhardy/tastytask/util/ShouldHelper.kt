package de.reinhardy.tastytask.util

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsString


@Suppress("UNCHECKED_CAST")
infix fun <T, U : T> T.shouldBe(expected: U?): T {
    assertThat(this, `is`(expected))
    return this
}

@Suppress("UNCHECKED_CAST")
infix fun String?.shouldContain(substr: String): String? {
    assertThat(this, containsString(substr))
    return this
}

@Suppress("UNCHECKED_CAST")
infix fun String?.shouldContain(substrList: Collection<String>): String? {
    substrList.forEach {
        this shouldContain it
    }
    return this
}

@Suppress("UNCHECKED_CAST")
infix fun Collection<Any>?.shouldHaveMinSize(size: Int): Collection<Any>? {
    assert((this?.size ?: 0) >= size) { "collection should have min size $size, but was ${this?.size}" }
    return this
}