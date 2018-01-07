package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.namespaces.standard.elements.Enclosure
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import java.net.URL

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