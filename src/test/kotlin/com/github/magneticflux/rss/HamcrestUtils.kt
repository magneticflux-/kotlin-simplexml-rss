package com.github.magneticflux.rss

import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeDiagnosingMatcher

internal inline fun <reified T : Throwable> throws(): Matcher<() -> Any?> {
    return throws(CoreMatchers.isA(T::class.java))
}

internal fun throws(matcher: Matcher<out Throwable>): Matcher<() -> Any?> {
    return object : TypeSafeDiagnosingMatcher<() -> Any?>() {
        override fun matchesSafely(item: (() -> Any?), mismatchDescription: Description): Boolean {
            return try {
                item()
                mismatchDescription.appendText("did not throw an exception")
                false
            } catch (e: Throwable) {
                val success = matcher.matches(e)

                if (!success) {
                    mismatchDescription.appendText("was ")
                    matcher.describeMismatch(e, mismatchDescription)
                }

                success
            }
        }

        override fun describeTo(description: Description) {
            description.appendText("a function that throws")
                .appendDescriptionOf(matcher)
        }
    }
}

internal operator fun <T> Matcher<T>.not(): Matcher<T> {
    return Matchers.not(this)
}