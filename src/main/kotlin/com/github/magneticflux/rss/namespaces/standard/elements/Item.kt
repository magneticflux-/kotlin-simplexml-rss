package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesBlock
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesDuration
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesExplicitStatus
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.standard.converters.ItemConverter
import org.simpleframework.xml.Root
import org.threeten.bp.ZonedDateTime
import java.net.URL

/**
 * Properties common to all representations of an `<item>` element.
 *
 * @since 1.1.0
 */
interface IItemCommon {
    val title: String?
    val description: String?
    val link: URL?
    val categories: List<Category>
    val comments: URL?
    val pubDate: ZonedDateTime?
    val author: String?
    val guid: Guid?
    val enclosure: Enclosure?
    val source: Source?
    val iTunesCategories: List<ITunesTopLevelCategory>
    val iTunesSubtitle: String?
    val iTunesSummary: String?
    val iTunesAuthor: String?
    val iTunesDuration: ITunesDuration?
    val iTunesImage: ITunesImage?
    val iTunesBlock: ITunesBlock?
}

/**
 * The final RSS view of an `<item>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface IItem : IItemCommon {
    fun toWritableItem(): IWritableItem
    val iTunesExplicit: ITunesExplicitStatus
}

/**
 * The literal contents of an `<item>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableItem : IItemCommon {
    fun toItem(): IItem
    val iTunesExplicitRaw: String?
}

/**
 * This class represents an item in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see ItemConverter
 */
@Root(name = "item", strict = false)
data class Item(
        override val title: String?,
        override val description: String?,
        override val link: URL?,
        override val categories: List<Category>,
        override val comments: URL?,
        override val pubDate: ZonedDateTime?,
        override val author: String?,
        override val guid: Guid?,
        override val enclosure: Enclosure?,
        override val source: Source?,
        override val iTunesCategories: List<ITunesTopLevelCategory>,
        override val iTunesExplicitRaw: String?,
        override val iTunesSubtitle: String?,
        override val iTunesSummary: String?,
        override val iTunesAuthor: String?,
        override val iTunesDuration: ITunesDuration?,
        override val iTunesImage: ITunesImage?,
        override val iTunesBlock: ITunesBlock?
) : IItem, IWritableItem {
    override fun toWritableItem(): IWritableItem = this

    override fun toItem(): IItem = this

    override val iTunesExplicit = when (iTunesExplicitRaw?.toLowerCase()) {
        "yes" -> ITunesExplicitStatus.YES
        "clean" -> ITunesExplicitStatus.CLEAN
        else -> ITunesExplicitStatus.NONE
    }
}