package com.github.magneticflux.rss

import java.io.InputStream

object SampleUtils {
    fun getSample(name: String): InputStream {
        return this::class.java.getResourceAsStream(name)
    }
}