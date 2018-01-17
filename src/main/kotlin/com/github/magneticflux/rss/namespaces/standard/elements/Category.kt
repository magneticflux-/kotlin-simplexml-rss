package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.CategoryConverter
import org.simpleframework.xml.Root

/**
 * Properties common to all representations of a `<category>` element.
 *
 * @since 1.1.0
 */
interface ICommonCategory : HasReadWrite<ICategory, IWritableCategory> {
    val domain: String?
    val text: String
}

/**
 * The final RSS view of a `<category>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface ICategory : ICommonCategory {
    override fun toReadOnly(): ICategory = this
}

/**
 * The literal contents of a `<category>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableCategory : ICommonCategory {
    override fun toWritable(): IWritableCategory = this
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
    override val domain: String? = null,
    override val text: String
) : ICategory, IWritableCategory