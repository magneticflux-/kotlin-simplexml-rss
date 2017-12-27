package com.github.magneticflux.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import java.net.URL
import java.util.Locale

const val ITUNES_REFERENCE = "http://www.itunes.com/DTDs/Podcast-1.0.dtd"

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
        @param:Attribute(name = "version")
        @get:Attribute(name = "version")
        val version: String,
        @param:Element(name = "channel")
        @get:Element(name = "channel")
        val channel: Channel
)

/**
 * This class represents a channel in an RSS feed.
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see ChannelConverter
 */
@Root(name = "channel", strict = false)
data class Channel(
        @param:Element(name = "title")
        @get:Element(name = "title")
        val title: String,
        @param:Element(name = "description", required = false)
        @get:Element(name = "description", required = false)
        val description: String,
        @param:Element(name = "link")
        @get:Element(name = "link")
        val link: URL,
        @param:ElementList(inline = true)
        @get:ElementList(inline = true)
        val categories: List<Category>,
        @param:Element(name = "copyright", required = false)
        @get:Element(name = "copyright", required = false)
        val copyright: String? = null,
        @param:Element(name = "docs", required = false)
        @get:Element(name = "docs", required = false)
        val docs: URL? = null,
        @param:Element(name = "language", required = false)
        @get:Element(name = "language", required = false)
        val language: Locale? = null,
        @param:Element(name = "webMaster", required = false)
        @get:Element(name = "webMaster", required = false)
        val webMaster: String? = null,
        @param:Element(name = "managingEditor", required = false)
        @get:Element(name = "managingEditor", required = false)
        val managingEditor: String? = null,
        @param:Element(name = "generator", required = false)
        @get:Element(name = "generator", required = false)
        val generator: String? = null,
        @param:Element(name = "image", required = false)
        @get:Element(name = "image", required = false)
        val image: Image? = null,
        @param:Element(name = "lastBuildDate", required = false)
        @get:Element(name = "lastBuildDate", required = false)
        val lastBuildDate: ZonedDateTime? = null,
        @param:Element(name = "pubDate", required = false)
        @get:Element(name = "pubDate", required = false)
        val pubDate: ZonedDateTime? = null,
        @param:Element(name = "ttl", required = false)
        @get:Element(name = "ttl", required = false)
        val ttl: Int? = null,
        @param:ElementList(name = "skipDays", entry = "day", required = false)
        @get:ElementList(name = "skipDays", entry = "day", required = false)
        val skipDays: List<DayOfWeek> = emptyList(),
        @param:ElementList(name = "skipHours", entry = "hour", required = false)
        @get:ElementList(name = "skipHours", entry = "hour", required = false)
        val skipHours: List<Int> = emptyList(),
        @param:Element(name = "cloud", required = false)
        @get:Element(name = "cloud", required = false)
        val cloud: Cloud? = null,
        @param:Element(name = "textInput", required = false)
        @get:Element(name = "textInput", required = false)
        val textInput: TextInput? = null,
        @param:ElementList(inline = true)
        @get:ElementList(inline = true)
        val items: List<Item>,
        val itunesCategories: List<ITunesTopLevelCategory>,
        val itunesExplicit: Explicit
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
        @param:Attribute(name = "domain")
        @get:Attribute(name = "domain")
        val domain: String? = null,
        @param:Text
        @get:Text
        val text: String
)

/**
 * This class represents an image in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see ImageConverter
 */
@Root(name = "image", strict = false)
data class Image(
        @param:Element(name = "url")
        @get:Element(name = "url")
        val url: URL,
        @param:Element(name = "title")
        @get:Element(name = "title")
        val title: String,
        @param:Element(name = "link")
        @get:Element(name = "link")
        val link: URL,
        @param:Element(name = "description", required = false)
        @get:Element(name = "description", required = false)
        val description: String? = null,
        @param:Element(name = "width", required = false)
        @get:Element(name = "width", required = false)
        val width: Int = 88,
        @param:Element(name = "height", required = false)
        @get:Element(name = "height", required = false)
        val height: Int = 31
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
        @param:Attribute(name = "domain")
        @get:Attribute(name = "domain")
        val domain: String,
        @param:Attribute(name = "path")
        @get:Attribute(name = "path")
        val path: String,
        @param:Attribute(name = "port")
        @get:Attribute(name = "port")
        val port: Int,
        @param:Attribute(name = "protocol")
        @get:Attribute(name = "protocol")
        val protocol: String,
        @param:Attribute(name = "registerProcedure")
        @get:Attribute(name = "registerProcedure")
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
        @param:Element(name = "title")
        @get:Element(name = "title")
        val title: String,
        @param:Element(name = "description")
        @get:Element(name = "description")
        val description: String,
        @param:Element(name = "name")
        @get:Element(name = "name")
        val name: String,
        @param:Element(name = "link")
        @get:Element(name = "link")
        val link: URL
)

/**
 * This class represents an item in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see ItemConverter
 */
@Root(name = "item", strict = false)
data class Item(
        @param:Element(name = "title", required = false)
        @get:Element(name = "title", required = false)
        val title: String? = null,
        @param:Element(name = "description", required = false)
        @get:Element(name = "description", required = false)
        val description: String? = null,
        @param:Element(name = "link", required = false)
        @get:Element(name = "link", required = false)
        val link: URL? = null,
        @param:ElementList(inline = true)
        @get:ElementList(inline = true)
        val categories: List<Category> = emptyList(),
        @param:Element(name = "comments", required = false)
        @get:Element(name = "comments", required = false)
        val comments: URL? = null,
        @param:Element(name = "pubDate", required = false)
        @get:Element(name = "pubDate", required = false)
        val pubDate: ZonedDateTime? = null,
        @param:Element(name = "author", required = false)
        @get:Element(name = "author", required = false)
        val author: String? = null,
        @param:Element(name = "guid", required = false)
        @get:Element(name = "guid", required = false)
        val guid: Guid? = null,
        @param:Element(name = "enclosure", required = false)
        @get:Element(name = "enclosure", required = false)
        val enclosure: Enclosure? = null,
        @param:Element(name = "source", required = false)
        @get:Element(name = "source", required = false)
        val source: Source? = null,
        val itunesCategories: List<ITunesTopLevelCategory>,
        val itunesExplicit: Explicit
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
        @param:Attribute(name = "url")
        @get:Attribute(name = "url")
        val url: URL,
        @param:Attribute(name = "length")
        @get:Attribute(name = "length")
        val length: Long,
        @param:Attribute(name = "type")
        @get:Attribute(name = "type")
        val type: String
)

/**
 * This class represents a guid in an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see GuidConverter
 */
@Root(name = "guid", strict = false)
data class Guid @JvmOverloads constructor(
        @param:Attribute(name = "isPermaLink", required = false)
        @get:Attribute(name = "isPermaLink", required = false)
        val isPermaLink: Boolean = false,
        @param:Text
        @get:Text
        val text: String
)

/**
 * This class represents a source in an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see SourceConverter
 */
@Root(name = "source", strict = false)
data class Source @JvmOverloads constructor(
        @param:Attribute(name = "url")
        @get:Attribute(name = "url")
        val url: URL,
        @param:Text(required = false)
        @get:Text(required = false)
        val text: String? = null
)

/**
 * This class represents an itunes:category in a [Channel] or an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesTopLevelCategoryConverter
 */
@Root(name = "category")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesTopLevelCategory(
        val text: String,
        val subCategories: List<ITunesSubCategory>
)

/**
 * This class represents an itunes:category in a [ITunesTopLevelCategory].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesSubCategoryConverter
 */
@Root(name = "category")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesSubCategory(
        val text: String
)

/**
 * This class represents an itunes:explicit in a [Channel] or an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ExplicitConverter
 */
@Root(name = "explicit")
@Namespace(reference = ITUNES_REFERENCE)
data class Explicit(
        val isExplicit: Boolean
) {
    companion object {
        val YES = Explicit(true)
        val NO = Explicit(false)
    }
}