package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.DayOfWeekTransform
import com.github.magneticflux.rss.LocaleLanguageTransform
import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.ZonedDateTimeTransform
import com.github.magneticflux.rss.children
import com.github.magneticflux.rss.createChild
import com.github.magneticflux.rss.fallbackPersister
import com.github.magneticflux.rss.namespace
import com.github.magneticflux.rss.namespaces.Namespace
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.standard.elements.Category
import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import com.github.magneticflux.rss.namespaces.standard.elements.Cloud
import com.github.magneticflux.rss.namespaces.standard.elements.ICommonChannel
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
object ChannelConverter : Converter<ICommonChannel> {
    override fun read(node: InputNode): ICommonChannel {
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
        var iTunesExplicitRaw: String? = null
        var iTunesSubtitle: String? = null
        var iTunesSummary: String? = null
        var iTunesAuthor: String? = null
        var iTunesImage: ITunesImage? = null
        var iTunesBlockRaw: String? = null
        var iTunesCompleteRaw: String? = null

        node.children.forEach {
            when (it.namespace) {
                Namespace.DEFAULT -> {
                    when (it.name) {
                        "title" -> title = it.value.orEmpty()
                        "description" -> description = it.value.orEmpty()
                        "link" -> link = fallbackPersister.read(URL::class.java, it)
                        "category" -> categories += fallbackPersister.read(Category::class.java, it)
                        "copyright" -> copyright = it.value
                        "docs" -> docs = fallbackPersister.read(URL::class.java, it)
                        "language" -> language = fallbackPersister.read(Locale::class.java, it)
                        "webMaster" -> webMaster = it.value
                        "managingEditor" -> managingEditor = it.value
                        "generator" -> generator = it.value
                        "image" -> image = fallbackPersister.read(Image::class.java, it)
                        "lastBuildDate" -> lastBuildDate =
                                fallbackPersister.read(ZonedDateTime::class.java, it)
                        "pubDate" -> pubDate = fallbackPersister.read(ZonedDateTime::class.java, it)
                        "ttl" -> ttl = it.value.toInt()
                        "day" -> skipDays += fallbackPersister.read(DayOfWeek::class.java, it)
                        "hour" -> skipHours += it.value.toInt()
                        "cloud" -> cloud = fallbackPersister.read(Cloud::class.java, it)
                        "textInput" -> textInput = fallbackPersister.read(TextInput::class.java, it)
                        "item" -> items += fallbackPersister.read(Item::class.java, it)
                    }
                }
                Namespace.ITUNES -> {
                    when (it.name) {
                        "category" -> iTunesCategories += fallbackPersister.read(
                            ITunesTopLevelCategory::class.java,
                            it
                        )
                        "explicit" -> iTunesExplicitRaw = it.value
                        "subtitle" -> iTunesSubtitle = it.value
                        "summary" -> iTunesSummary = it.value
                        "author" -> iTunesAuthor = it.value
                        "image" -> iTunesImage = fallbackPersister.read(ITunesImage::class.java, it)
                        "block" -> iTunesBlockRaw = it.value
                        "complete" -> iTunesCompleteRaw = it.value
                    }
                }
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
            iTunesExplicitRaw,
            iTunesSubtitle,
            iTunesSummary,
            iTunesAuthor,
            iTunesImage,
            iTunesBlockRaw,
            iTunesCompleteRaw
        )
    }

    override fun write(node: OutputNode, value: ICommonChannel) {
        val writable = value.toWritable()

        node.getChild("title").value = writable.title
        node.getChild("description").value = writable.description
        node.getChild("link").value = URLTransform.write(writable.link)
        writable.categories.forEach { fallbackPersister.write(it, node) }
        writable.copyright?.let { node.getChild("copyright").value = it }
        writable.docs?.let { node.getChild("docs").value = URLTransform.write(it) }
        writable.language?.let {
            node.getChild("language").value = LocaleLanguageTransform.write(it)
        }
        writable.webMaster?.let { node.getChild("webMaster").value = it }
        writable.managingEditor?.let { node.getChild("managingEditor").value = it }
        writable.generator?.let { node.getChild("generator").value = it }
        writable.image?.let { fallbackPersister.write(it, node) }
        writable.lastBuildDate?.let {
            node.getChild("lastBuildDate").value = ZonedDateTimeTransform.write(it)
        }
        writable.pubDate?.let { node.getChild("pubDate").value = ZonedDateTimeTransform.write(it) }
        writable.ttl?.let { node.getChild("ttl").value = it.toString() }
        if (writable.skipDays.isNotEmpty())
            node.getChild("skipDays").also { skipDaysNode ->
                writable.skipDays.forEach {
                    skipDaysNode.getChild("day").value = DayOfWeekTransform.write(it)
                }
            }
        if (writable.skipHours.isNotEmpty())
            node.getChild("skipHours").also { skipHoursNode ->
                writable.skipHours.forEach {
                    skipHoursNode.getChild("hour").value = it.toString()
                }
            }
        writable.cloud?.let { fallbackPersister.write(it, node) }
        writable.textInput?.let { fallbackPersister.write(it, node) }
        writable.items.forEach {
            fallbackPersister.write(it, node)
        }
        writable.iTunesCategories.forEach {
            fallbackPersister.write(it, node)
        }
        writable.iTunesExplicitRaw?.let {
            node.createChild(
                Namespace.ITUNES.reference,
                "explicit",
                it
            )
        }
        writable.iTunesSubtitle?.let {
            node.createChild(
                Namespace.ITUNES.reference,
                "subtitle",
                it
            )
        }
        writable.iTunesSummary?.let { node.createChild(Namespace.ITUNES.reference, "summary", it) }
        writable.iTunesAuthor?.let { node.createChild(Namespace.ITUNES.reference, "author", it) }
        writable.iTunesImage?.let { fallbackPersister.write(it, node) }
        writable.iTunesBlockRaw?.let { node.createChild(Namespace.ITUNES.reference, "block", it) }
        writable.iTunesCompleteRaw?.let {
            node.createChild(
                Namespace.ITUNES.reference,
                "complete",
                it
            )
        }
    }
}
