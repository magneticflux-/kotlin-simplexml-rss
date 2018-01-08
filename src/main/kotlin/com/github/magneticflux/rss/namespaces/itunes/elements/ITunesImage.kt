package com.github.magneticflux.rss.namespaces.itunes.elements

import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesImageConverter
import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import com.github.magneticflux.rss.namespaces.standard.elements.HasReadWrite
import com.github.magneticflux.rss.namespaces.standard.elements.Item
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root
import java.net.URL

/**
 * Properties common to all representations of a `<image>` element.
 *
 * @since 1.1.0
 */
interface ICommonITunesImage : HasReadWrite<IITunesImage, IWritableITunesImage> {
    val href: URL
}

/**
 * The final RSS view of a `<image>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface IITunesImage : ICommonITunesImage {
    override fun toReadOnly(): IITunesImage = this
}

/**
 * The literal contents of a `<image>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableITunesImage : ICommonITunesImage {
    override fun toWritable(): IWritableITunesImage = this
}

/**
 * This class represents an itunes:image in a [Channel] or an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesImageConverter
 */
@Root(name = "image")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesImage(
        override val href: URL
) : IITunesImage, IWritableITunesImage