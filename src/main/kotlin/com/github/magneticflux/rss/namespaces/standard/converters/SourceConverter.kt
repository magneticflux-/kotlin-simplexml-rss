package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.namespaces.standard.elements.Source
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import java.net.URL

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