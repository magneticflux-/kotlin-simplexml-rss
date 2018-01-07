package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.ImageConverter
import org.simpleframework.xml.Root
import java.net.URL

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
