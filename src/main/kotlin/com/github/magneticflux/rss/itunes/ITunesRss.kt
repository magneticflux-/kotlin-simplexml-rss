package com.github.magneticflux.rss.itunes

import com.github.magneticflux.rss.ITUNES_REFERENCE
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root
import org.threeten.bp.Duration

/**
 * This class represents an itunes:category in a [com.github.magneticflux.rss.Channel] or an [com.github.magneticflux.rss.Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesTopLevelCategoryConverter
 */
@Root(name = "category")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesTopLevelCategory(
        val text: String,
        val itunesSubCategories: List<ITunesSubCategory>
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
 * This class represents an itunes:explicit in a [com.github.magneticflux.rss.Channel] or an [com.github.magneticflux.rss.Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesExplicitConverter
 */
@Root(name = "explicit")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesExplicit(
        val isExplicit: Boolean
) {
    companion object {
        val YES = ITunesExplicit(true)
        val NO = ITunesExplicit(false)
    }
}

/**
 * This class helps with implementing [String] elements with namespaces because they require user-defined classes instead of just a [String]
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
abstract class AbstractString internal constructor(open val text: String) : Comparable<String> by text, CharSequence by text {
    override fun toString(): String = text
    override fun hashCode(): Int = text.hashCode()
    override fun equals(other: Any?): Boolean {
        return if (other is AbstractString)
            text == other.text
        else
            text == other
    }
}

/**
 * This class represents an itunes:subtitle in a [com.github.magneticflux.rss.Channel] or an [com.github.magneticflux.rss.Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesSubtitleConverter
 */
@Root(name = "subtitle")
@Namespace(reference = ITUNES_REFERENCE)
class ITunesSubtitle(
        override val text: String
) : AbstractString(text)

/**
 * This class represents an itunes:summary in a [com.github.magneticflux.rss.Channel] or an [com.github.magneticflux.rss.Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesSummaryConverter
 */
@Root(name = "summary")
@Namespace(reference = ITUNES_REFERENCE)
class ITunesSummary(
        override val text: String
) : AbstractString(text)

/**
 * This class represents an itunes:author in a [com.github.magneticflux.rss.Channel] or an [com.github.magneticflux.rss.Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesAuthorConverter
 */
@Root(name = "author")
@Namespace(reference = ITUNES_REFERENCE)
class ITunesAuthor(
        override val text: String
) : AbstractString(text)

/**
 * This class represents an itunes:duration in an [com.github.magneticflux.rss.Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesDurationConverter
 */
@Root(name = "duration")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesDuration(
        val duration: Duration
)