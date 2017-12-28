package com.github.magneticflux.rss

import org.jetbrains.spek.api.dsl.SpecBody
import org.jetbrains.spek.api.dsl.given

fun <T> SpecBody.given(value: T, description: (T) -> String, body: SpecBody.(T) -> Unit) {
    given(description(value), { body(value) })
}