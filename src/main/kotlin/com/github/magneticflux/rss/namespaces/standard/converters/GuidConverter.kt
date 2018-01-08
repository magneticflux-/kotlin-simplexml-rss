package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.namespaces.standard.elements.Guid
import com.github.magneticflux.rss.namespaces.standard.elements.ICommonGuid
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object GuidConverter : Converter<ICommonGuid> {
    override fun read(node: InputNode): ICommonGuid {
        val isPermaLink = node.getAttribute("isPermaLink")?.value
        val text = node.value

        return Guid(isPermaLink, text)
    }

    override fun write(node: OutputNode, value: ICommonGuid) {
        val writable = value.toWritable()

        writable.isPermaLinkRaw?.let { node.setAttribute("isPermaLink", it) }
        node.value = writable.text
    }
}