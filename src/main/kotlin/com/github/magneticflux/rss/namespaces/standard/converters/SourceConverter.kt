package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.fallbackPersister
import com.github.magneticflux.rss.namespaces.standard.elements.ICommonSource
import com.github.magneticflux.rss.namespaces.standard.elements.Source
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import java.net.URL

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object SourceConverter : Converter<ICommonSource> {
    override fun read(node: InputNode): ICommonSource {
        val url = fallbackPersister.read(URL::class.java, node.getAttribute("url"))
        val text = node.value

        return Source(url, text)
    }

    override fun write(node: OutputNode, value: ICommonSource) {
        val writable = value.toWritable()

        node.setAttribute("url", URLTransform.write(writable.url))
        node.value = writable.text
    }
}