package de.reinhardy.tastytask.util

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.containsStringIgnoringCase


@Suppress("UNCHECKED_CAST")
infix fun <T, U : T> T.shouldBe(expected: U?): T {
    assertThat(this, `is`(expected))
    return this
}

@Suppress("UNCHECKED_CAST")
infix fun String?.shouldContain(substr: String): String? {
    assertThat(this, containsStringIgnoringCase(substr))
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
fun <T, C: Collection<T>> C?.shouldContainInAnyOrder(vararg expected: T): C? {
    expected.forEach {
        assertThat(this, containsInAnyOrder(it))
    }
    return this
}

@Suppress("UNCHECKED_CAST")
infix fun <T, C: Collection<T>> C?.shouldContain(expected: T): C? {
        assert(this?.contains(expected) == true) { "collection $this does not contain expected $expected" }
    return this
}

@Suppress("UNCHECKED_CAST")
infix fun Collection<Any>?.shouldHaveMinSize(size: Int): Collection<Any>? {
    assert((this?.size ?: 0) >= size) { "collection should have min size $size, but was ${this?.size}" }
    return this
}