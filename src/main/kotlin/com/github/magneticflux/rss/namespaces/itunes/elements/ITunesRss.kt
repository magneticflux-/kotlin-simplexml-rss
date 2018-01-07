package com.github.magneticflux.rss.namespaces.itunes.elements

import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesBlockConverter
import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesCompleteConverter
import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesDurationConverter
import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesImageConverter
import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesSubCategoryConverter
import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesTopLevelCategoryConverter
import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import com.github.magneticflux.rss.namespaces.standard.elements.Item
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root
import org.threeten.bp.Duration
import java.net.URL

const val ITUNES_REFERENCE = "http://www.itunes.com/DTDs/Podcast-1.0.dtd"

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
 * This class represents the value of an itunes:explicit element in a [Channel] or an [Item]
 */
enum class ITunesExplicitStatus {
    YES, NONE, CLEAN
}

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