package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.GuidConverter
import org.simpleframework.xml.Root

/**
 * The final RSS view of a `<guid>` element. Defaults are used if applicable.
 */
interface IGuid {
    val isPermaLink: Boolean
    val text: String
}

abstract class AbstractGuid(
        override val isPermaLink: Boolean,
        override val text: String
) : IGuid

/**
 * This class represents a guid in an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see GuidConverter
 */
@Root(name = "guid", strict = false)
data class Guid(
        internal val _isPermaLink: String?,
        override val text: String
) : AbstractGuid(
        when (_isPermaLink?.toLowerCase()) {
            "true" -> true
            else -> false
        },
        text
)