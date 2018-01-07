package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.itunes.elements.ITUNES_REFERENCE
import com.github.magneticflux.rss.namespaces.standard.converters.RssFeedConverter
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

/**
 * Properties common to all representations of an `<rss>` element.
 *
 * @since 1.1.0
 */
interface IRssCommon {
    val version: String
    val channel: IChannelCommon
}

/**
 * The final RSS view of an `<rss>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface IRss : IRssCommon {
    fun toWritableRssFeed(): IWritableRss
    override val channel: IChannel
}

/**
 * The literal contents of an `<rss>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableRss : IRssCommon {
    fun toRssFeed(): IRss
    override val channel: IWritableChannel
}

/**
 * This class represents an RSS feed.
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see RssFeedConverter
 */
@Root(name = "rss", strict = false)
@Namespace(prefix = "itunes", reference = ITUNES_REFERENCE)
data class Rss(
        override val version: String,
        override val channel: Channel
) : IRss, IWritableRss {
    override fun toRssFeed(): IRss = this

    override fun toWritableRssFeed(): IWritableRss = this
}