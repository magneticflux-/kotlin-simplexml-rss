package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesBlock
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesExplicitStatus
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.standard.converters.ChannelConverter
import org.simpleframework.xml.Root
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import java.net.URL
import java.util.Locale

/**
 * Properties common to all representations of a `<channel>` element.
 *
 * @since 1.1.0
 */
interface IChannelCommon {
    val title: String
    val description: String
    val link: URL
    val categories: List<Category>
    val copyright: String?
    val docs: URL?
    val language: Locale?
    val webMaster: String?
    val managingEditor: String?
    val generator: String?
    val image: Image?
    val lastBuildDate: ZonedDateTime?
    val pubDate: ZonedDateTime?
    val ttl: Int?
    val skipDays: List<DayOfWeek>
    val skipHours: List<Int>
    val cloud: Cloud?
    val textInput: TextInput?
    val items: List<Item>
    val iTunesCategories: List<ITunesTopLevelCategory>
    val iTunesSubtitle: String?
    val iTunesSummary: String?
    val iTunesAuthor: String?
    val iTunesImage: ITunesImage?
    val iTunesBlock: ITunesBlock?
}

/**
 * The final RSS view of a `<channel>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface IChannel : IChannelCommon {
    fun toWritableChannel(): IWritableChannel
    val iTunesExplicit: ITunesExplicitStatus
    val iTunesComplete: Boolean
}

/**
 * The literal contents of a `<channel>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableChannel : IChannelCommon {
    fun toChannel(): IChannel
    val iTunesExplicitRaw: String?
    val iTunesCompleteRaw: String?
}

/**
 * This class represents a channel parsed from an RSS feed.
 *
 * @since 1.0.0
 * @see ChannelConverter
 */
@Root(name = "channel", strict = false)
data class Channel(
        override val title: String,
        override val description: String,
        override val link: URL,
        override val categories: List<Category>,
        override val copyright: String?,
        override val docs: URL?,
        override val language: Locale?,
        override val webMaster: String?,
        override val managingEditor: String?,
        override val generator: String?,
        override val image: Image?,
        override val lastBuildDate: ZonedDateTime?,
        override val pubDate: ZonedDateTime?,
        override val ttl: Int?,
        override val skipDays: List<DayOfWeek>,
        override val skipHours: List<Int>,
        override val cloud: Cloud?,
        override val textInput: TextInput?,
        override val items: List<Item>,
        override val iTunesCategories: List<ITunesTopLevelCategory>,
        override val iTunesExplicitRaw: String?,
        override val iTunesSubtitle: String?,
        override val iTunesSummary: String?,
        override val iTunesAuthor: String?,
        override val iTunesImage: ITunesImage?,
        override val iTunesBlock: ITunesBlock?,
        override val iTunesCompleteRaw: String?
) : IChannel, IWritableChannel {
    override fun toChannel(): IChannel = this

    override fun toWritableChannel(): IWritableChannel = this

    override val iTunesExplicit: ITunesExplicitStatus = when (iTunesExplicitRaw?.toLowerCase()) {
        "yes" -> ITunesExplicitStatus.YES
        "clean" -> ITunesExplicitStatus.CLEAN
        else -> ITunesExplicitStatus.NONE
    }
    override val iTunesComplete: Boolean = when (iTunesCompleteRaw?.toLowerCase()) {
        "yes" -> true
        else -> false
    }
}