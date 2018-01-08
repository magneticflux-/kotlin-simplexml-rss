package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.ZonedDateTimeTransform
import com.github.magneticflux.rss.children
import com.github.magneticflux.rss.createChild
import com.github.magneticflux.rss.fallbackPersister
import com.github.magneticflux.rss.fullName
import com.github.magneticflux.rss.namespaces.itunes.elements.ITUNES_REFERENCE
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.standard.elements.Category
import com.github.magneticflux.rss.namespaces.standard.elements.Enclosure
import com.github.magneticflux.rss.namespaces.standard.elements.Guid
import com.github.magneticflux.rss.namespaces.standard.elements.ICommonItem
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
object ItemConverter : Converter<ICommonItem> {
    override fun read(node: InputNode): ICommonItem {
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
        var iTunesExplicitRaw: String? = null
        var iTunesSubtitle: String? = null
        var iTunesSummary: String? = null
        var iTunesAuthor: String? = null
        var iTunesDurationRaw: String? = null
        var iTunesImage: ITunesImage? = null
        var iTunesBlockRaw: String? = null

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
                "itunes:explicit" -> iTunesExplicitRaw = it.value
                "itunes:subtitle" -> iTunesSubtitle = it.value
                "itunes:summary" -> iTunesSummary = it.value
                "itunes:author" -> iTunesAuthor = it.value
                "itunes:duration" -> iTunesDurationRaw = it.value
                "itunes:image" -> iTunesImage = fallbackPersister.read(ITunesImage::class.java, it)
                "itunes:block" -> iTunesBlockRaw = it.value
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
                iTunesExplicitRaw,
                iTunesSubtitle,
                iTunesSummary,
                iTunesAuthor,
                iTunesDurationRaw,
                iTunesImage,
                iTunesBlockRaw
        )
    }

    override fun write(node: OutputNode, value: ICommonItem) {
        val writable = value.toWritable()

        writable.title?.let { node.createChild(name = "title", value = it) }
        writable.description?.let { node.createChild(name = "description", value = it) }
        writable.link?.let { node.createChild(name = "link", value = URLTransform.write(it)) }
        writable.categories.forEach { fallbackPersister.write(it, node) }
        writable.comments?.let { node.createChild(name = "comments", value = URLTransform.write(it)) }
        writable.pubDate?.let { node.createChild(name = "pubDate", value = ZonedDateTimeTransform.write(it)) }
        writable.author?.let { node.createChild(name = "author", value = it) }
        writable.guid?.let { fallbackPersister.write(it, node) }
        writable.enclosure?.let { fallbackPersister.write(it, node) }
        writable.source?.let { fallbackPersister.write(it, node) }
        writable.iTunesCategories.forEach { fallbackPersister.write(it, node) }
        writable.iTunesExplicitRaw?.let { node.createChild(ITUNES_REFERENCE, "explicit", it) }
        writable.iTunesSubtitle?.let { node.createChild(ITUNES_REFERENCE, "subtitle", it) }
        writable.iTunesSummary?.let { node.createChild(ITUNES_REFERENCE, "summary", it) }
        writable.iTunesAuthor?.let { node.createChild(ITUNES_REFERENCE, "author", it) }
        writable.iTunesDurationRaw?.let { node.createChild(ITUNES_REFERENCE, "duration", it) }
        writable.iTunesImage?.let { fallbackPersister.write(it, node) }
        writable.iTunesBlockRaw?.let { node.createChild(ITUNES_REFERENCE, "block", it) }
    }
}