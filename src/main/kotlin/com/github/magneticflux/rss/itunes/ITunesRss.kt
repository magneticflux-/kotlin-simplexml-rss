package com.github.magneticflux.rss.itunes

import com.github.magneticflux.rss.Channel
import com.github.magneticflux.rss.ITUNES_REFERENCE
import com.github.magneticflux.rss.Item
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root
import org.threeten.bp.Duration
import java.net.URL

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
 * This class represents an itunes:explicit in a [Channel] or an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesExplicitConverter
 */
@Root(name = "explicit")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesExplicit(
        val explicitStatus: ExplicitStatus
) {
    companion object {
        val YES = ITunesExplicit(ExplicitStatus.YES)
        val NO = ITunesExplicit(ExplicitStatus.NO)
        val CLEAN = ITunesExplicit(ExplicitStatus.CLEAN)
    }

    enum class ExplicitStatus {
        YES, NO, CLEAN
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
 * This class represents an itunes:subtitle in a [Channel] or an [Item].
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
 * This class represents an itunes:summary in a [Channel] or an [Item].
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
 * This class represents an itunes:author in a [Channel] or an [Item].
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
 * This class represents an itunes:duration in an [Item].
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
        val href: URL
)

/**
 * This class represents an itunes:block in a [Channel] or an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesBlockConverter
 */
@Root(name = "block")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesBlock(
        val shouldBlock: Boolean
)

/**
 * This class represents an itunes:complete in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesCompleteConverter
 */
@Root(name = "complete")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesComplete(
        val isComplete: Boolean
)