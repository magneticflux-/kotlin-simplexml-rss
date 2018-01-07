package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.CloudConverter
import org.simpleframework.xml.Root

/**
 * This class represents a cloud object in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see CloudConverter
 */
@Root(name = "cloud", strict = false)
data class Cloud(
        val domain: String,
        val path: String,
        val port: Int,
        val protocol: String,
        val registerProcedure: String
)