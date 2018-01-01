package com.github.magneticflux.rss

import com.github.magneticflux.rss.itunes.ITUNES_REFERENCE
import com.github.magneticflux.rss.itunes.ITunesBlock
import com.github.magneticflux.rss.itunes.ITunesDuration
import com.github.magneticflux.rss.itunes.ITunesExplicitStatus
import com.github.magneticflux.rss.itunes.ITunesImage
import com.github.magneticflux.rss.itunes.ITunesTopLevelCategory
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import java.net.URL
import java.util.Locale

/**
 * This class represents an RSS feed.
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see RssFeedConverter
 */
@Root(name = "rss", strict = false)
@Namespace(prefix = "itunes", reference = ITUNES_REFERENCE)
data class RssFeed(
        val version: String,
        val channel: Channel
)

/**
 * The final RSS view of a `<channel>` element. Defaults are used if applicable.
 */
interface IChannel {
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
    val iTunesExplicit: ITunesExplicitStatus
    val iTunesSubtitle: String?
    val iTunesSummary: String?
    val iTunesAuthor: String?
    val iTunesImage: ITunesImage?
    val iTunesBlock: ITunesBlock?
    val iTunesComplete: Boolean
}

abstract class AbstractChannel(
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
        override val iTunesExplicit: ITunesExplicitStatus,
        override val iTunesSubtitle: String?,
        override val iTunesSummary: String?,
        override val iTunesAuthor: String?,
        override val iTunesImage: ITunesImage?,
        override val iTunesBlock: ITunesBlock?,
        override val iTunesComplete: Boolean
) : IChannel

/**
 * This class represents a channel in an RSS feed.
 *
 * @author Mitchell Skaggs
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
        internal val _iTunesExplicit: String?,
        override val iTunesSubtitle: String?,
        override val iTunesSummary: String?,
        override val iTunesAuthor: String?,
        override val iTunesImage: ITunesImage?,
        override val iTunesBlock: ITunesBlock?,
        internal val _iTunesComplete: String?
) : AbstractChannel(
        title,
        description,
        link,
        categories,
        copyright,
        docs,
        language,
        webMaster,
        managingEditor,
        generator,
        image,
        lastBuildDate,
        pubDate,
        ttl,
        skipDays,
        skipHours,
        cloud,
        textInput,
        items,
        iTunesCategories,
        when (_iTunesExplicit?.toLowerCase()) {
            "yes" -> ITunesExplicitStatus.YES
            "clean" -> ITunesExplicitStatus.CLEAN
            else -> ITunesExplicitStatus.NONE
        },
        iTunesSubtitle,
        iTunesSummary,
        iTunesAuthor,
        iTunesImage,
        iTunesBlock,
        when (_iTunesComplete?.toLowerCase()) {
            "yes" -> true
            else -> false
        }
)

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
 * The final RSS view of an `<item>` element. Defaults are used if applicable.
 */
interface IItem {
    val title: String?
    val description: String?
    val link: URL?
    val categories: List<Category>
    val comments: URL?
    val pubDate: ZonedDateTime?
    val author: String?
    val guid: Guid?
    val enclosure: Enclosure?
    val source: Source?
    val iTunesCategories: List<ITunesTopLevelCategory>
    val iTunesExplicit: ITunesExplicitStatus
    val iTunesSubtitle: String?
    val iTunesSummary: String?
    val iTunesAuthor: String?
    val iTunesDuration: ITunesDuration?
    val iTunesImage: ITunesImage?
    val iTunesBlock: ITunesBlock?
}

abstract class AbstractItem(
        override val title: String?,
        override val description: String?,
        override val link: URL?,
        override val categories: List<Category>,
        override val comments: URL?,
        override val pubDate: ZonedDateTime?,
        override val author: String?,
        override val guid: Guid?,
        override val enclosure: Enclosure?,
        override val source: Source?,
        override val iTunesCategories: List<ITunesTopLevelCategory>,
        override val iTunesExplicit: ITunesExplicitStatus,
        override val iTunesSubtitle: String?,
        override val iTunesSummary: String?,
        override val iTunesAuthor: String?,
        override val iTunesDuration: ITunesDuration?,
        override val iTunesImage: ITunesImage?,
        override val iTunesBlock: ITunesBlock?
) : IItem

/**
 * This class represents an item in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see ItemConverter
 */
@Root(name = "item", strict = false)
data class Item(
        override val title: String?,
        override val description: String?,
        override val link: URL?,
        override val categories: List<Category>,
        override val comments: URL?,
        override val pubDate: ZonedDateTime?,
        override val author: String?,
        override val guid: Guid?,
        override val enclosure: Enclosure?,
        override val source: Source?,
        override val iTunesCategories: List<ITunesTopLevelCategory>,
        internal val _iTunesExplicit: String?,
        override val iTunesSubtitle: String?,
        override val iTunesSummary: String?,
        override val iTunesAuthor: String?,
        override val iTunesDuration: ITunesDuration?,
        override val iTunesImage: ITunesImage?,
        override val iTunesBlock: ITunesBlock?
) : AbstractItem(title,
        description,
        link,
        categories,
        comments,
        pubDate,
        author,
        guid,
        enclosure,
        source,
        iTunesCategories,
        when (_iTunesExplicit?.toLowerCase()) {
            "yes" -> ITunesExplicitStatus.YES
            "clean" -> ITunesExplicitStatus.CLEAN
            else -> ITunesExplicitStatus.NONE
        },
        iTunesSubtitle,
        iTunesSummary,
        iTunesAuthor,
        iTunesDuration,
        iTunesImage,
        iTunesBlock
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