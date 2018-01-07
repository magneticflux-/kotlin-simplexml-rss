package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.namespaces.standard.elements.Guid
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object GuidConverter : Converter<Guid> {
    override fun read(node: InputNode): Guid {
        val isPermaLink = node.getAttribute("isPermaLink")?.value
        val text = node.value

        return Guid(isPermaLink, text)
    }

    override fun write(node: OutputNode, value: Guid) {
        if (value._isPermaLink != null) node.setAttribute("isPermaLink", value._isPermaLink)
        node.value = value.text
    }
}