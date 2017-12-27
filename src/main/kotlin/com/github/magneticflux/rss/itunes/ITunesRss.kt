package com.github.magneticflux.rss.itunes

import com.github.magneticflux.rss.Channel
import com.github.magneticflux.rss.ITUNES_REFERENCE
import com.github.magneticflux.rss.Item
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

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
