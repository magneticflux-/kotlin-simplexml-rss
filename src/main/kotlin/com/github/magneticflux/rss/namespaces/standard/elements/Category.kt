package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.CategoryConverter
import org.simpleframework.xml.Root

/**
 * This class represents a category in either a [Channel] or an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see CategoryConverter
 */
@Root(name = "category", strict = false)
data class Category(
        val domain: String? = null,
        val text: String
)