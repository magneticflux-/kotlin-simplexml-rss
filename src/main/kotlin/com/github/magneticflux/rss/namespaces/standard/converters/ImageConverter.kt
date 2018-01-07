package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.namespaces.standard.elements.Image
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import java.net.URL

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
        var width: String? = null
        var height: String? = null

        node.children.forEach {
            when (it.fullName) {
                "url" -> url = fallbackPersister.read(URL::class.java, it)
                "title" -> title = fallbackPersister.read(String::class.java, it)
                "link" -> link = fallbackPersister.read(URL::class.java, it)
                "description" -> description = fallbackPersister.read(String::class.java, it)
                "width" -> width = it.value
                "height" -> height = it.value
            }
        }

        return Image(url, title, link, description, width, height)
    }

    override fun write(node: OutputNode, value: Image) {
        node.getChild("url").value = URLTransform.write(value.url)
        node.getChild("title").value = value.title
        node.getChild("link").value = URLTransform.write(value.link)
        if (value.description != null) node.createChild(name = "description", value = value.description)
        if (value._width != null) node.createChild(name = "width", value = value._width)
        if (value._height != null) node.createChild(name = "height", value = value._height)
    }
}