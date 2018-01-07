package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.ZonedDateTimeTransform
import com.github.magneticflux.rss.namespaces.itunes.elements.ITUNES_REFERENCE
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesBlock
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesDuration
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.standard.elements.Category
import com.github.magneticflux.rss.namespaces.standard.elements.Enclosure
import com.github.magneticflux.rss.namespaces.standard.elements.Guid
import com.github.magneticflux.rss.namespaces.standard.elements.Item
import com.github.magneticflux.rss.namespaces.standard.elements.Source
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import org.threeten.bp.ZonedDateTime
import java.net.URL

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object ItemConverter : Converter<Item> {
    override fun read(node: InputNode): Item {
        var title: String? = null
        var description: String? = null
        var link: URL? = null
        val categories = mutableListOf<Category>()
        var comments: URL? = null
        var pubDate: ZonedDateTime? = null
        var author: String? = null
        var guid: Guid? = null
        var enclosure: Enclosure? = null
        var source: Source? = null
        val iTunesCategories = mutableListOf<ITunesTopLevelCategory>()
        var iTunesExplicit: String? = null
        var iTunesSubtitle: String? = null
        var iTunesSummary: String? = null
        var iTunesAuthor: String? = null
        var iTunesDuration: ITunesDuration? = null
        var iTunesImage: ITunesImage? = null
        var iTunesBlock: ITunesBlock? = null

        node.children.forEach {
            when (it.fullName) {
                "title" -> title = it.value
                "description" -> description = it.value
                "link" -> link = fallbackPersister.read(URL::class.java, it)
                "category" -> categories += fallbackPersister.read(Category::class.java, it)
                "comments" -> comments = fallbackPersister.read(URL::class.java, it)
                "pubDate" -> pubDate = fallbackPersister.read(ZonedDateTime::class.java, it)
                "author" -> author = it.value
                "guid" -> guid = fallbackPersister.read(Guid::class.java, it)
                "enclosure" -> enclosure = fallbackPersister.read(Enclosure::class.java, it)
                "source" -> source = fallbackPersister.read(Source::class.java, it)
                "itunes:category" -> iTunesCategories += fallbackPersister.read(ITunesTopLevelCategory::class.java, it)
                "itunes:explicit" -> iTunesExplicit = it.value
                "itunes:subtitle" -> iTunesSubtitle = it.value
                "itunes:summary" -> iTunesSummary = it.value
                "itunes:author" -> iTunesAuthor = it.value
                "itunes:duration" -> iTunesDuration = fallbackPersister.read(ITunesDuration::class.java, it)
                "itunes:image" -> iTunesImage = fallbackPersister.read(ITunesImage::class.java, it)
                "itunes:block" -> iTunesBlock = fallbackPersister.read(ITunesBlock::class.java, it)
            }
        }

        return Item(
                title,
                description,
                link,
                categories,
                comments,
                pubDate,
                author,
                guid,
                enclosure,
                source,
                iTunesCategories,
                iTunesExplicit,
                iTunesSubtitle,
                iTunesSummary,
                iTunesAuthor,
                iTunesDuration,
                iTunesImage,
                iTunesBlock
        )
    }

    override fun write(node: OutputNode, value: Item) {
        if (value.title != null) node.createChild(name = "title", value = value.title)
        if (value.description != null) node.createChild(name = "description", value = value.description)
        if (value.link != null) node.createChild(name = "link", value = URLTransform.write(value.link))
        value.categories.forEach { fallbackPersister.write(it, node) }
        if (value.comments != null) node.createChild(name = "comments", value = URLTransform.write(value.comments))
        if (value.pubDate != null) node.createChild(name = "pubDate", value = ZonedDateTimeTransform.write(value.pubDate))
        if (value.author != null) node.createChild(name = "author", value = value.author)
        if (value.guid != null) fallbackPersister.write(value.guid, node)
        if (value.enclosure != null) fallbackPersister.write(value.enclosure, node)
        if (value.source != null) fallbackPersister.write(value.source, node)
        value.iTunesCategories.forEach { fallbackPersister.write(it, node) }
        if (value.iTunesExplicitRaw != null) node.createChild(ITUNES_REFERENCE, "explicit", value.iTunesExplicitRaw)
        if (value.iTunesSubtitle != null) node.createChild(ITUNES_REFERENCE, "subtitle", value.iTunesSubtitle)
        if (value.iTunesSummary != null) node.createChild(ITUNES_REFERENCE, "summary", value.iTunesSummary)
        if (value.iTunesAuthor != null) node.createChild(ITUNES_REFERENCE, "author", value.iTunesAuthor)
        if (value.iTunesDuration != null) fallbackPersister.write(value.iTunesDuration, node)
        if (value.iTunesImage != null) fallbackPersister.write(value.iTunesImage, node)
        if (value.iTunesBlock != null) fallbackPersister.write(value.iTunesBlock, node)
    }
}