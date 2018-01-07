package com.github.magneticflux.rss.namespaces.itunes.elements

import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesTopLevelCategoryConverter
import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import com.github.magneticflux.rss.namespaces.standard.elements.Item
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