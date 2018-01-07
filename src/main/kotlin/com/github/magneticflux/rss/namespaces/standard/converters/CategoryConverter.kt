package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.namespaces.standard.elements.Category
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

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