package com.github.magneticflux.rss

import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import java.net.URL
import java.util.Locale

/**
 * This converts a string that forms a URL to a [URL] object.
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 */
class URLConverter : Converter<URL> {
    override fun read(node: InputNode): URL {
        return URL(node.value)
    }

    override fun write(node: OutputNode, value: URL) {
        node.value = value.toExternalForm()
    }
}

/**
 * A Persister that is used by various [Converter]s
 */
internal val fallbackPersister = createDefaultPersister()

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
class RssFeedConverter : Converter<RssFeed> {
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
class ChannelConverter : Converter<Channel> {
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
                items
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
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
class ItemConverter : Converter<Item> {
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
                source)
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
    }
}

private val InputNode.fullName: String
    get() {
        val prefix = this.prefix
        val name = this.name
        return if (prefix.isNullOrEmpty()) name else "$prefix:$name"
    }

private val InputNode.children: Iterator<InputNode>
    get() {
        return object : Iterator<InputNode> {
            private var lastRead: InputNode? = null

            override fun hasNext(): Boolean {
                if (lastRead == null)
                    lastRead = this@children.next

                return lastRead != null
            }

            override fun next(): InputNode {
                try {
                    return lastRead ?: throw NoSuchElementException()
                } finally {
                    lastRead = null
                }
            }
        }
    }

