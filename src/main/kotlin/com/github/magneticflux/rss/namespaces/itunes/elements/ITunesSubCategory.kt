package com.github.magneticflux.rss.namespaces.itunes.elements

import com.github.magneticflux.rss.namespaces.Namespace.ITUNES
import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesSubCategoryConverter
import com.github.magneticflux.rss.namespaces.standard.elements.HasReadWrite
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

/**
 * Properties common to all representations of a `<category>` element.
 *
 * @since 1.1.0
 */
interface ICommonITunesSubCategory : HasReadWrite<IITunesSubCategory, IWritableITunesSubCategory> {
    val text: String
}

/**
 * The final RSS view of a `<category>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface IITunesSubCategory : ICommonITunesSubCategory {
    override fun toReadOnly(): IITunesSubCategory = this
}

/**
 * The literal contents of a `<category>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableITunesSubCategory : ICommonITunesSubCategory {
    override fun toWritable(): IWritableITunesSubCategory = this
}

/**
 * This class represents an itunes:category in a [ITunesTopLevelCategory].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesSubCategoryConverter
 */
@Root(name = "category")
@Namespace(reference = ITUNES.reference)
data class ITunesSubCategory(
    override val text: String
) : IITunesSubCategory, IWritableITunesSubCategory
