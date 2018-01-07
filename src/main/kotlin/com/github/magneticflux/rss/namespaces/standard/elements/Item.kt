package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.itunes.elements.ICommonITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.ICommonITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.itunes.elements.IITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.IITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesDuration
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesExplicitStatus
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.itunes.elements.IWritableITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.IWritableITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.standard.converters.ItemConverter
import org.simpleframework.xml.Root
import org.threeten.bp.ZonedDateTime
import java.net.URL

/**
 * Properties common to all representations of an `<item>` element.
 *
 * @since 1.1.0
 */
interface ICommonItem : HasReadWrite<IItem, IWritableItem> {
    val title: String?
    val description: String?
    val link: URL?
    val categories: List<ICommonCategory>
    val comments: URL?
    val pubDate: ZonedDateTime?
    val author: String?
    val guid: ICommonGuid?
    val enclosure: ICommonEnclosure?
    val source: ICommonSource?
    val iTunesCategories: List<ICommonITunesTopLevelCategory>
    val iTunesSubtitle: String?
    val iTunesSummary: String?
    val iTunesAuthor: String?
    val iTunesDuration: ITunesDuration?
    val iTunesImage: ICommonITunesImage?
}

/**
 * The final RSS view of an `<item>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface IItem : ICommonItem {
    override fun toReadOnly(): IItem = this

    override val categories: List<ICategory>
    override val guid: IGuid?
    override val enclosure: IEnclosure?
    override val source: ISource?
    override val iTunesCategories: List<IITunesTopLevelCategory>
    override val iTunesImage: IITunesImage?
    val iTunesExplicit: ITunesExplicitStatus
    val iTunesBlock: Boolean
}

/**
 * The literal contents of an `<item>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableItem : ICommonItem {
    override fun toWritable(): IWritableItem = this

    override val categories: List<IWritableCategory>
    override val guid: IWritableGuid?
    override val enclosure: IWritableEnclosure?
    override val source: IWritableSource?
    override val iTunesCategories: List<IWritableITunesTopLevelCategory>
    override val iTunesImage: IWritableITunesImage?
    val iTunesExplicitRaw: String?
    val iTunesBlockRaw: String?
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
        override val iTunesBlockRaw: String?
) : IItem, IWritableItem {
    override val iTunesExplicit = when (iTunesExplicitRaw?.toLowerCase()) {
        "yes" -> ITunesExplicitStatus.YES
        "clean" -> ITunesExplicitStatus.CLEAN
        else -> ITunesExplicitStatus.NONE
    }

    override val iTunesBlock: Boolean = when (iTunesBlockRaw?.toLowerCase()) {
        "yes" -> true
        else -> false
    }
}