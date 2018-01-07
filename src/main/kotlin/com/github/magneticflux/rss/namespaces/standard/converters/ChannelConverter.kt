package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.DayOfWeekTransform
import com.github.magneticflux.rss.LocaleLanguageTransform
import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.ZonedDateTimeTransform
import com.github.magneticflux.rss.namespaces.itunes.elements.ITUNES_REFERENCE
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesBlock
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.standard.elements.Category
import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import com.github.magneticflux.rss.namespaces.standard.elements.Cloud
import com.github.magneticflux.rss.namespaces.standard.elements.Image
import com.github.magneticflux.rss.namespaces.standard.elements.Item
import com.github.magneticflux.rss.namespaces.standard.elements.TextInput
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import java.net.URL
import java.util.Locale

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object ChannelConverter : Converter<Channel> {
    override fun read(node: InputNode): Channel {
        lateinit var title: String
        lateinit var description: String
        lateinit var link: URL
        val categories = mutableListOf<Category>()
        var copyright: String? = null
        var docs: URL? = null
        var language: Locale? = null
        var webMaster: String? = null
        var managingEditor: String? = null
        var generator: String? = null
        var image: Image? = null
        var lastBuildDate: ZonedDateTime? = null
        var pubDate: ZonedDateTime? = null
        var ttl: Int? = null
        val skipDays = mutableListOf<DayOfWeek>()
        val skipHours = mutableListOf<Int>()
        var cloud: Cloud? = null
        var textInput: TextInput? = null
        val items = mutableListOf<Item>()
        val iTunesCategories = mutableListOf<ITunesTopLevelCategory>()
        var iTunesExplicit: String? = null
        var iTunesSubtitle: String? = null
        var iTunesSummary: String? = null
        var iTunesAuthor: String? = null
        var iTunesImage: ITunesImage? = null
        var iTunesBlock: ITunesBlock? = null
        var iTunesComplete: String? = null

        node.children.forEach {
            when (it.fullName) {
                "title" -> title = it.value.orEmpty() // Element is there, but an empty String
                "description" -> description = it.value.orEmpty() // Element is there, but an empty String
                "link" -> link = fallbackPersister.read(URL::class.java, it)
                "category" -> categories += fallbackPersister.read(Category::class.java, it)
                "copyright" -> copyright = it.value
                "docs" -> docs = fallbackPersister.read(URL::class.java, it)
                "language" -> language = fallbackPersister.read(Locale::class.java, it)
                "webMaster" -> webMaster = it.value
                "managingEditor" -> managingEditor = it.value
                "generator" -> generator = it.value
                "image" -> image = fallbackPersister.read(Image::class.java, it)
                "lastBuildDate" -> lastBuildDate = fallbackPersister.read(ZonedDateTime::class.java, it)
                "pubDate" -> pubDate = fallbackPersister.read(ZonedDateTime::class.java, it)
                "ttl" -> ttl = it.value.toInt()
                "day" -> skipDays += fallbackPersister.read(DayOfWeek::class.java, it)
                "hour" -> skipHours += it.value.toInt()
                "cloud" -> cloud = fallbackPersister.read(Cloud::class.java, it)
                "textInput" -> textInput = fallbackPersister.read(TextInput::class.java, it)
                "item" -> items += fallbackPersister.read(Item::class.java, it)
                "itunes:category" -> iTunesCategories += fallbackPersister.read(ITunesTopLevelCategory::class.java, it)
                "itunes:explicit" -> iTunesExplicit = it.value
                "itunes:subtitle" -> iTunesSubtitle = it.value
                "itunes:summary" -> iTunesSummary = it.value
                "itunes:author" -> iTunesAuthor = it.value
                "itunes:image" -> iTunesImage = fallbackPersister.read(ITunesImage::class.java, it)
                "itunes:block" -> iTunesBlock = fallbackPersister.read(ITunesBlock::class.java, it)
                "itunes:complete" -> iTunesComplete = it.value
            }
        }

        return Channel(
                title,
                description,
                link,
                categories,
                copyright,
                docs,
                language,
                webMaster,
                managingEditor,
                generator,
                image,
                lastBuildDate,
                pubDate,
                ttl,
                skipDays,
                skipHours,
                cloud,
                textInput,
                items,
                iTunesCategories,
                iTunesExplicit,
                iTunesSubtitle,
                iTunesSummary,
                iTunesAuthor,
                iTunesImage,
                iTunesBlock,
                iTunesComplete
        )
    }

    override fun write(node: OutputNode, value: Channel) {
        node.getChild("title").value = value.title
        node.getChild("description").value = value.description
        node.getChild("link").value = URLTransform.write(value.link)
        value.categories.forEach { fallbackPersister.write(it, node) }
        if (value.copyright != null) node.getChild("copyright").value = value.copyright
        if (value.docs != null) node.getChild("docs").value = URLTransform.write(value.docs)
        if (value.language != null) node.getChild("language").value = LocaleLanguageTransform.write(value.language)
        if (value.webMaster != null) node.getChild("webMaster").value = value.webMaster
        if (value.managingEditor != null) node.getChild("managingEditor").value = value.managingEditor
        if (value.generator != null) node.getChild("generator").value = value.generator
        if (value.image != null) fallbackPersister.write(value.image, node)
        if (value.lastBuildDate != null) node.getChild("lastBuildDate").value = ZonedDateTimeTransform.write(value.lastBuildDate)
        if (value.pubDate != null) node.getChild("pubDate").value = ZonedDateTimeTransform.write(value.pubDate)
        if (value.ttl != null) node.getChild("ttl").value = value.ttl.toString()
        if (value.skipDays.isNotEmpty()) node.getChild("skipDays").also { skipDaysNode ->
            value.skipDays.forEach {
                skipDaysNode.getChild("day").value = DayOfWeekTransform.write(it)
            }
        }
        if (value.skipHours.isNotEmpty()) node.getChild("skipHours").also { skipHoursNode ->
            value.skipHours.forEach {
                skipHoursNode.getChild("hour").value = it.toString()
            }
        }
        if (value.cloud != null) fallbackPersister.write(value.cloud, node)
        if (value.textInput != null) fallbackPersister.write(value.textInput, node)
        value.items.forEach {
            fallbackPersister.write(it, node)
        }
        value.iTunesCategories.forEach {
            fallbackPersister.write(it, node)
        }
        if (value.iTunesExplicitRaw != null) node.createChild(ITUNES_REFERENCE, "explicit", value.iTunesExplicitRaw)
        if (value.iTunesSubtitle != null) node.createChild(ITUNES_REFERENCE, "subtitle", value.iTunesSubtitle)
        if (value.iTunesSummary != null) node.createChild(ITUNES_REFERENCE, "summary", value.iTunesSummary)
        if (value.iTunesAuthor != null) node.createChild(ITUNES_REFERENCE, "author", value.iTunesAuthor)
        if (value.iTunesImage != null) fallbackPersister.write(value.iTunesImage, node)
        if (value.iTunesBlock != null) fallbackPersister.write(value.iTunesBlock, node)
        if (value.iTunesCompleteRaw != null) node.createChild(ITUNES_REFERENCE, "complete", value.iTunesCompleteRaw)
    }
}