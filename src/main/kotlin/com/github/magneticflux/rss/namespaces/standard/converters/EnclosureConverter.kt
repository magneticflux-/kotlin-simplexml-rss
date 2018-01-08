package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.fallbackPersister
import com.github.magneticflux.rss.namespaces.standard.elements.Enclosure
import com.github.magneticflux.rss.namespaces.standard.elements.ICommonEnclosure
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import java.net.URL

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object EnclosureConverter : Converter<ICommonEnclosure> {
    override fun read(node: InputNode): ICommonEnclosure {
        val url = fallbackPersister.read(URL::class.java, node.getAttribute("url"))
        val length = node.getAttribute("length").value.toLong()
        val type = node.getAttribute("type").value

        return Enclosure(url, length, type)
    }

    override fun write(node: OutputNode, value: ICommonEnclosure) {
        val writable = value.toWritable()

        node.setAttribute("url", URLTransform.write(writable.url))
        node.setAttribute("length", writable.length.toString())
        node.setAttribute("type", writable.type)
    }
}