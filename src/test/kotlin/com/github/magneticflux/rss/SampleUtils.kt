package com.github.magneticflux.rss

import java.io.InputStream

/**
 * Created by Mitchell Skaggs on 12/18/2017.
 */
object SampleUtils {
    fun getSample(name: String): InputStream {
        return this::class.java.getResourceAsStream(name)
    }
}