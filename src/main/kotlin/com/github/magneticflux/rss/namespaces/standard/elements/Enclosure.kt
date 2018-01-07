package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.EnclosureConverter
import org.simpleframework.xml.Root
import java.net.URL

/**
 * Properties common to all representations of an `<enclosure>` element.
 *
 * @since 1.1.0
 */
interface ICommonEnclosure : HasReadWrite<IEnclosure, IWritableEnclosure> {
    val url: URL
    val length: Long
    val type: String
}

/**
 * The final RSS view of an `<enclosure>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface IEnclosure : ICommonEnclosure {
    override fun toReadOnly(): IEnclosure = this
}

/**
 * The literal contents of an `<enclosure>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableEnclosure : ICommonEnclosure {
    override fun toWritable(): IWritableEnclosure = this
}

/**
 * This class represents an enclosure in an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see EnclosureConverter
 */
@Root(name = "enclosure", strict = false)
data class Enclosure(
        override val url: URL,
        override val length: Long,
        override val type: String
) : IEnclosure, IWritableEnclosure