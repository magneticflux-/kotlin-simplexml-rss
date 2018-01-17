package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.SourceConverter
import org.simpleframework.xml.Root
import java.net.URL

/**
 * Properties common to all representations of a `<source>` element.
 *
 * @since 1.1.0
 */
interface ICommonSource : HasReadWrite<ISource, IWritableSource> {
    val url: URL
    val text: String?
}

/**
 * The final RSS view of a `<source>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface ISource : ICommonSource {
    override fun toReadOnly(): ISource = this
}

/**
 * The literal contents of a `<source>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableSource : ICommonSource {
    override fun toWritable(): IWritableSource = this
}

/**
 * This class represents a source in an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see SourceConverter
 */
@Root(name = "source", strict = false)
data class Source(
    override val url: URL,
    override val text: String? = null
) : ISource, IWritableSource