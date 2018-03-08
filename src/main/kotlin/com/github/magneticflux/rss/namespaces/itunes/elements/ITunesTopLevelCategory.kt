package com.github.magneticflux.rss.namespaces.itunes.elements

import com.github.magneticflux.rss.namespaces.Namespace.ITUNES
import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesTopLevelCategoryConverter
import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import com.github.magneticflux.rss.namespaces.standard.elements.HasReadWrite
import com.github.magneticflux.rss.namespaces.standard.elements.Item
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

/**
 * Properties common to all representations of a `<category>` element.
 *
 * @since 1.1.0
 */
interface ICommonITunesTopLevelCategory :
    HasReadWrite<IITunesTopLevelCategory, IWritableITunesTopLevelCategory> {
    val text: String
    val iTunesSubCategories: List<ICommonITunesSubCategory>
}

/**
 * The final RSS view of a `<category>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface IITunesTopLevelCategory : ICommonITunesTopLevelCategory {
    override fun toReadOnly(): IITunesTopLevelCategory = this

    override val iTunesSubCategories: List<IITunesSubCategory>
}

/**
 * The literal contents of a `<category>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableITunesTopLevelCategory : ICommonITunesTopLevelCategory {
    override fun toWritable(): IWritableITunesTopLevelCategory = this

    override val iTunesSubCategories: List<IWritableITunesSubCategory>
}

/**
 * This class represents an itunes:category in a [Channel] or an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesTopLevelCategoryConverter
 */
@Root(name = "category")
@Namespace(reference = ITUNES.reference)
data class ITunesTopLevelCategory(
    override val text: String,
    override val iTunesSubCategories: List<ITunesSubCategory>
) : IITunesTopLevelCategory, IWritableITunesTopLevelCategory
