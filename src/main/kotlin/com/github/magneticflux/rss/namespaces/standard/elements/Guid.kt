package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.GuidConverter
import org.simpleframework.xml.Root

/**
 * Properties common to all representations of a `<guid>` element.
 *
 * @since 1.1.0
 */
interface IGuidCommon {
    val text: String
}

/**
 * The final RSS view of a `<guid>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface IGuid : IGuidCommon {
    val isPermaLink: Boolean
}

/**
 * The literal contents of a `<guid>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableGuid : IGuidCommon {
    val isPermaLinkRaw: String?
}

/**
 * This class represents a guid in an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see GuidConverter
 */
@Root(name = "guid", strict = false)
data class Guid(
        override val isPermaLinkRaw: String?,
        override val text: String
) : IGuid, IWritableGuid {
    override val isPermaLink: Boolean = when (isPermaLinkRaw?.toLowerCase()) {
        "true" -> true
        else -> false
    }
}