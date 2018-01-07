package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.CategoryConverter
import com.github.magneticflux.rss.namespaces.standard.converters.CloudConverter
import com.github.magneticflux.rss.namespaces.standard.converters.EnclosureConverter
import com.github.magneticflux.rss.namespaces.standard.converters.GuidConverter
import com.github.magneticflux.rss.namespaces.standard.converters.ImageConverter
import com.github.magneticflux.rss.namespaces.standard.converters.SourceConverter
import com.github.magneticflux.rss.namespaces.standard.converters.TextInputConverter
import org.simpleframework.xml.Root
import java.net.URL

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

/**
 * The final RSS view of an `<image>` element. Defaults are used if applicable.
 */
interface IImage {
    val url: URL
    val title: String
    val link: URL
    val description: String?
    val width: Int
    val height: Int
}

abstract class AbstractImage(
        override val url: URL,
        override val title: String,
        override val link: URL,
        override val description: String?,
        override val width: Int,
        override val height: Int) : IImage

/**
 * This class represents an image in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see ImageConverter
 */
@Root(name = "image", strict = false)
data class Image(
        override val url: URL,
        override val title: String,
        override val link: URL,
        override val description: String?,
        internal val _width: String?,
        internal val _height: String?
) : AbstractImage(
        url,
        title,
        link,
        description,
        _width?.toInt() ?: 88,
        _height?.toInt() ?: 31
)

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

/**
 * This class represents a text input object in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see TextInputConverter
 */
@Root(name = "textInput", strict = false)
data class TextInput(
        val title: String,
        val description: String,
        val name: String,
        val link: URL
)

/**
 * This class represents an enclosure in an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see EnclosureConverter
 */
@Root(name = "enclosure", strict = false)
data class Enclosure(
        val url: URL,
        val length: Long,
        val type: String
)

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