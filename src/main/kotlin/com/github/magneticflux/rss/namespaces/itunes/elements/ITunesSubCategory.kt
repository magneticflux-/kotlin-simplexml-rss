package com.github.magneticflux.rss.namespaces.itunes.elements

import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesSubCategoryConverter
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

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