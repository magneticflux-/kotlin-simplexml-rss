package com.github.magneticflux.rss

import com.github.magneticflux.rss.itunes.ITunesAuthor
import com.github.magneticflux.rss.itunes.ITunesDuration
import com.github.magneticflux.rss.itunes.ITunesExplicit
import com.github.magneticflux.rss.itunes.ITunesImage
import com.github.magneticflux.rss.itunes.ITunesSubtitle
import com.github.magneticflux.rss.itunes.ITunesSummary
import com.github.magneticflux.rss.itunes.ITunesTopLevelCategory
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import java.net.URL
import java.util.Locale

/**
 * A Persister that is used by various [Converter] objects.
 */
internal val fallbackPersister = createRssPersister()

/**
 * Gets the prefix + name or just name if prefix is null.
 */
internal val InputNode.fullName: String
    get() {
        val prefix = this.prefix
        val name = this.name
        return if (prefix.isNullOrEmpty()) name else "$prefix:$name"
    }

/**
 * Creates an iterator over an InputNode's children.
 */
internal val InputNode.children: Iterator<InputNode>
    get() {
        return InputNodeChildIterator(this)
    }

private class InputNodeChildIterator(val rootNode: InputNode) : AbstractIterator<InputNode>() {
    override fun computeNext() {
        val next = rootNode.next
        if (next != null)
            setNext(next)
        else
            done()
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object RssFeedConverter : Converter<RssFeed> {
    override fun read(node: InputNode): RssFeed {
        val version = fallbackPersister.read(String::class.java, node.getAttribute("version"))

        lateinit var channel: Channel

        node.children.forEach {
            when (it.fullName) {
                "channel" -> {
                    channel = fallbackPersister.read(Channel::class.java, it)
                }
            }
        }

        return RssFeed(
                version,
                channel)
    }

    override fun write(node: OutputNode, value: RssFeed) {
        node.setAttribute("version", value.version)
        fallbackPersister.write(value.channel, node)
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object ChannelConverter : Converter<Channel> {
    override fun read(node: InputNode): Channel {
        lateinit var title: String
        var description: String? = null
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
        var iTunesExplicit: ITunesExplicit? = null
        var iTunesSubtitle: ITunesSubtitle? = null
        var iTunesSummary: ITunesSummary? = null
        var iTunesAuthor: ITunesAuthor? = null
        var iTunesImage: ITunesImage? = null

        node.children.forEach {
            when (it.fullName) {
                "title" -> title = fallbackPersister.read(String::class.java, it)
                "description" -> description = fallbackPersister.read(String::class.java, it)
                "link" -> link = fallbackPersister.read(URL::class.java, it)
                "category" -> categories += fallbackPersister.read(Category::class.java, it)
                "copyright" -> copyright = fallbackPersister.read(String::class.java, it)
                "docs" -> docs = fallbackPersister.read(URL::class.java, it)
                "language" -> language = fallbackPersister.read(Locale::class.java, it)
                "webMaster" -> webMaster = fallbackPersister.read(String::class.java, it)
                "managingEditor" -> managingEditor = fallbackPersister.read(String::class.java, it)
                "generator" -> generator = fallbackPersister.read(String::class.java, it)
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
                "itunes:explicit" -> iTunesExplicit = fallbackPersister.read(ITunesExplicit::class.java, it)
                "itunes:subtitle" -> iTunesSubtitle = fallbackPersister.read(ITunesSubtitle::class.java, it)
                "itunes:summary" -> iTunesSummary = fallbackPersister.read(ITunesSummary::class.java, it)
                "itunes:author" -> iTunesAuthor = fallbackPersister.read(ITunesAuthor::class.java, it)
                "itunes:image" -> iTunesImage = fallbackPersister.read(ITunesImage::class.java, it)
            }
        }

        return Channel(
                title,
                description.orEmpty(),
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
                iTunesExplicit ?: ITunesExplicit.NO,
                iTunesSubtitle,
                iTunesSummary,
                iTunesAuthor,
                iTunesImage
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
        fallbackPersister.write(value.iTunesExplicit, node)
        if (value.iTunesSubtitle != null) fallbackPersister.write(value.iTunesSubtitle, node)
        if (value.iTunesSummary != null) fallbackPersister.write(value.iTunesSummary, node)
        if (value.iTunesAuthor != null) fallbackPersister.write(value.iTunesAuthor, node)
        if (value.iTunesImage != null) fallbackPersister.write(value.iTunesImage, node)
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object CategoryConverter : Converter<Category> {
    override fun read(node: InputNode): Category {
        val domain: String? = node.getAttribute("domain")?.value
        val text: String = node.value

        return Category(domain, text)
    }

    override fun write(node: OutputNode, value: Category) {
        if (value.domain != null) node.setAttribute("domain", value.domain)
        node.value = value.text
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object ImageConverter : Converter<Image> {
    override fun read(node: InputNode): Image {
        lateinit var url: URL
        lateinit var title: String
        lateinit var link: URL
        var description: String? = null
        var width = 88
        var height = 31

        node.children.forEach {
            when (it.fullName) {
                "url" -> url = fallbackPersister.read(URL::class.java, it)
                "title" -> title = fallbackPersister.read(String::class.java, it)
                "link" -> link = fallbackPersister.read(URL::class.java, it)
                "description" -> description = fallbackPersister.read(String::class.java, it)
                "width" -> width = it.value.toInt()
                "height" -> height = it.value.toInt()
            }
        }

        return Image(url, title, link, description, width, height)
    }

    override fun write(node: OutputNode, value: Image) {
        node.getChild("url").value = URLTransform.write(value.url)
        node.getChild("title").value = value.title
        node.getChild("link").value = URLTransform.write(value.link)
        if (value.description != null) node.getChild("description").value = value.description
        node.getChild("width").value = value.width.toString()
        node.getChild("height").value = value.height.toString()
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object CloudConverter : Converter<Cloud> {
    override fun read(node: InputNode): Cloud {
        val domain = node.getAttribute("domain").value
        val path = node.getAttribute("path").value
        val port = node.getAttribute("port").value.toInt()
        val protocol = node.getAttribute("protocol").value
        val registerProcedure = node.getAttribute("registerProcedure").value

        return Cloud(domain, path, port, protocol, registerProcedure)
    }

    override fun write(node: OutputNode, value: Cloud) {
        node.setAttribute("domain", value.domain)
        node.setAttribute("path", value.path)
        node.setAttribute("port", value.port.toString())
        node.setAttribute("protocol", value.protocol)
        node.setAttribute("registerProcedure", value.registerProcedure)
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object TextInputConverter : Converter<TextInput> {
    override fun read(node: InputNode): TextInput {
        lateinit var title: String
        lateinit var description: String
        lateinit var name: String
        lateinit var link: URL

        node.children.forEach {
            when (it.fullName) {
                "title" -> title = fallbackPersister.read(String::class.java, it)
                "description" -> description = fallbackPersister.read(String::class.java, it)
                "name" -> name = fallbackPersister.read(String::class.java, it)
                "link" -> link = fallbackPersister.read(URL::class.java, it)
            }
        }

        return TextInput(title, description, name, link)
    }

    override fun write(node: OutputNode, value: TextInput) {
        node.getChild("title").value = value.title
        node.getChild("description").value = value.description
        node.getChild("name").value = value.name
        node.getChild("link").value = URLTransform.write(value.link)
    }
}

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
        var iTunesExplicit: ITunesExplicit? = null
        var iTunesSubtitle: ITunesSubtitle? = null
        var iTunesSummary: ITunesSummary? = null
        var iTunesAuthor: ITunesAuthor? = null
        var iTunesDuration: ITunesDuration? = null
        var iTunesImage: ITunesImage? = null

        node.children.forEach {
            when (it.fullName) {
                "title" -> title = fallbackPersister.read(String::class.java, it)
                "description" -> description = fallbackPersister.read(String::class.java, it)
                "link" -> link = fallbackPersister.read(URL::class.java, it)
                "category" -> categories += fallbackPersister.read(Category::class.java, it)
                "comments" -> comments = fallbackPersister.read(URL::class.java, it)
                "pubDate" -> pubDate = fallbackPersister.read(ZonedDateTime::class.java, it)
                "author" -> author = fallbackPersister.read(String::class.java, it)
                "guid" -> guid = fallbackPersister.read(Guid::class.java, it)
                "enclosure" -> enclosure = fallbackPersister.read(Enclosure::class.java, it)
                "source" -> source = fallbackPersister.read(Source::class.java, it)
                "itunes:category" -> iTunesCategories += fallbackPersister.read(ITunesTopLevelCategory::class.java, it)
                "itunes:explicit" -> iTunesExplicit = fallbackPersister.read(ITunesExplicit::class.java, it)
                "itunes:subtitle" -> iTunesSubtitle = fallbackPersister.read(ITunesSubtitle::class.java, it)
                "itunes:summary" -> iTunesSummary = fallbackPersister.read(ITunesSummary::class.java, it)
                "itunes:author" -> iTunesAuthor = fallbackPersister.read(ITunesAuthor::class.java, it)
                "itunes:duration" -> iTunesDuration = fallbackPersister.read(ITunesDuration::class.java, it)
                "itunes:image" -> iTunesImage = fallbackPersister.read(ITunesImage::class.java, it)
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
                iTunesExplicit ?: ITunesExplicit.NO,
                iTunesSubtitle,
                iTunesSummary,
                iTunesAuthor,
                iTunesDuration,
                iTunesImage
        )
    }

    override fun write(node: OutputNode, value: Item) {
        if (value.title != null) node.getChild("title").value = value.title
        if (value.description != null) node.getChild("description").value = value.description
        if (value.link != null) node.getChild("link").value = URLTransform.write(value.link)
        value.categories.forEach { fallbackPersister.write(it, node) }
        if (value.comments != null) node.getChild("comments").value = URLTransform.write(value.comments)
        if (value.pubDate != null) node.getChild("pubDate").value = ZonedDateTimeTransform.write(value.pubDate)
        if (value.author != null) node.getChild("author").value = value.author
        if (value.guid != null) fallbackPersister.write(value.guid, node)
        if (value.enclosure != null) fallbackPersister.write(value.enclosure, node)
        if (value.source != null) fallbackPersister.write(value.source, node)
        value.iTunesCategories.forEach {
            fallbackPersister.write(it, node)
        }
        fallbackPersister.write(value.iTunesExplicit, node)
        if (value.iTunesSubtitle != null) fallbackPersister.write(value.iTunesSubtitle, node)
        if (value.iTunesSummary != null) fallbackPersister.write(value.iTunesSummary, node)
        if (value.iTunesAuthor != null) fallbackPersister.write(value.iTunesAuthor, node)
        if (value.iTunesDuration != null) fallbackPersister.write(value.iTunesDuration, node)
        if (value.iTunesImage != null) fallbackPersister.write(value.iTunesImage, node)
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object EnclosureConverter : Converter<Enclosure> {
    override fun read(node: InputNode): Enclosure {
        val url = fallbackPersister.read(URL::class.java, node.getAttribute("url"))
        val length = node.getAttribute("length").value.toLong()
        val type = node.getAttribute("type").value

        return Enclosure(url, length, type)
    }

    override fun write(node: OutputNode, value: Enclosure) {
        node.setAttribute("url", URLTransform.write(value.url))
        node.setAttribute("length", value.length.toString())
        node.setAttribute("type", value.type)
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object GuidConverter : Converter<Guid> {
    override fun read(node: InputNode): Guid {
        val isPermaLink = node.getAttribute("isPermaLink")?.value?.toBoolean()
        val text = node.value

        return Guid(isPermaLink ?: false, text)
    }

    override fun write(node: OutputNode, value: Guid) {
        node.setAttribute("isPermaLink", value.isPermaLink.toString())
        node.value = value.text
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object SourceConverter : Converter<Source> {
    override fun read(node: InputNode): Source {
        val url = fallbackPersister.read(URL::class.java, node.getAttribute("url"))
        val text = node.value

        return Source(url, text)
    }

    override fun write(node: OutputNode, value: Source) {
        node.setAttribute("url", URLTransform.write(value.url))
        node.value = value.text
    }
}