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
 * This class represents a channel in an RSS feed.
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see ChannelConverter
 */
@Root(name = "channel", strict = false)
data class Channel(
        val title: String,
        val description: String,
        val link: URL,
        val categories: List<Category>,
        val copyright: String? = null,
        val docs: URL? = null,
        val language: Locale? = null,
        val webMaster: String? = null,
        val managingEditor: String? = null,
        val generator: String? = null,
        val image: Image? = null,
        val lastBuildDate: ZonedDateTime? = null,
        val pubDate: ZonedDateTime? = null,
        val ttl: Int? = null,
        val skipDays: List<DayOfWeek>,
        val skipHours: List<Int>,
        val cloud: Cloud? = null,
        val textInput: TextInput? = null,
        val items: List<Item>,
        val iTunesCategories: List<ITunesTopLevelCategory>,
        internal val _iTunesExplicit: String?,
        val iTunesSubtitle: String?,
        val iTunesSummary: String?,
        val iTunesAuthor: String?,
        val iTunesImage: ITunesImage?,
        val iTunesBlock: ITunesBlock?,
        internal val _iTunesComplete: String?
) {
        val iTunesExplicit: ITunesExplicitStatus = when (_iTunesExplicit?.toLowerCase()) {
                "yes" -> ITunesExplicitStatus.YES
                "clean" -> ITunesExplicitStatus.CLEAN
                else -> ITunesExplicitStatus.NONE
        }

        val iTunesComplete: Boolean = when (_iTunesComplete?.toLowerCase()) {
                "yes" -> true
                else -> false
        }
}

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
 * This class represents an image in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see ImageConverter
 */
@Root(name = "image", strict = false)
data class Image(
        val url: URL,
        val title: String,
        val link: URL,
        val description: String? = null,
        val width: Int = 88,
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
 * This class represents an item in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see ItemConverter
 */
@Root(name = "item", strict = false)
data class Item(
        val title: String? = null,
        val description: String? = null,
        val link: URL? = null,
        val categories: List<Category>,
        val comments: URL? = null,
        val pubDate: ZonedDateTime? = null,
        val author: String? = null,
        val guid: Guid? = null,
        val enclosure: Enclosure? = null,
        val source: Source? = null,
        val iTunesCategories: List<ITunesTopLevelCategory>,
        internal val _iTunesExplicit: String?,
        val iTunesSubtitle: String?,
        val iTunesSummary: String?,
        val iTunesAuthor: String?,
        val iTunesDuration: ITunesDuration?,
        val iTunesImage: ITunesImage?,
        val iTunesBlock: ITunesBlock?
) {
        val iTunesExplicit: ITunesExplicitStatus = when (_iTunesExplicit?.toLowerCase()) {
                "yes" -> ITunesExplicitStatus.YES
                "clean" -> ITunesExplicitStatus.CLEAN
                else -> ITunesExplicitStatus.NONE
        }
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
        val url: URL,
        val length: Long,
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
data class Guid(
        val isPermaLink: Boolean = false,
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
data class Source(
        val url: URL,
        val text: String? = null
)