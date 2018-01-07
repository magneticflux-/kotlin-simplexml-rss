package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.SourceConverter
import org.simpleframework.xml.Root
import java.net.URL

/**
 * This class represents a source in an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see SourceConverter
 */
@Root(name = "source", strict = false)
data class Source(
        val url: URL,
        val text: String? = null
)