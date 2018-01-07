package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.ImageConverter
import org.simpleframework.xml.Root
import java.net.URL

/**
 * Properties common to all representations of an `<image>` element.
 *
 * @since 1.1.0
 */
interface IImageCommon : HasReadWrite<IImage, IWritableImage> {
    val url: URL
    val title: String
    val link: URL
    val description: String?
}

/**
 * The final RSS view of an `<image>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface IImage : IImageCommon {
    override fun toReadOnly(): IImage = this

    val width: Int
    val height: Int
}

/**
 * The literal contents of an `<image>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableImage : IImageCommon {
    override fun toWritable(): IWritableImage = this

    val widthRaw: String?
    val heightRaw: String?
}

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
        override val widthRaw: String?,
        override val heightRaw: String?
) : IImage, IWritableImage {
    override val width: Int = widthRaw?.toInt() ?: 88
    override val height: Int = heightRaw?.toInt() ?: 31
}
