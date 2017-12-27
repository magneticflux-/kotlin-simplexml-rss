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