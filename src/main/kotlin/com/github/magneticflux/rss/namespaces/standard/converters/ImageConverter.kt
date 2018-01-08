package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.children
import com.github.magneticflux.rss.createChild
import com.github.magneticflux.rss.fallbackPersister
import com.github.magneticflux.rss.fullName
import com.github.magneticflux.rss.namespaces.standard.elements.ICommonImage
import com.github.magneticflux.rss.namespaces.standard.elements.Image
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import java.net.URL

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object ImageConverter : Converter<ICommonImage> {
    override fun read(node: InputNode): ICommonImage {
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

    override fun write(node: OutputNode, value: ICommonImage) {
        val writable = value.toWritable()

        node.getChild("url").value = URLTransform.write(writable.url)
        node.getChild("title").value = writable.title
        node.getChild("link").value = URLTransform.write(writable.link)
        writable.description?.let { node.createChild(name = "description", value = it) }
        writable.widthRaw?.let { node.createChild(name = "width", value = it) }
        writable.heightRaw?.let { node.createChild(name = "height", value = it) }
    }
}