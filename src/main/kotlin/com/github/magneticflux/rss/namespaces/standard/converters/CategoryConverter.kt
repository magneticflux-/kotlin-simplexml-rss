package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.namespaces.standard.elements.Category
import com.github.magneticflux.rss.namespaces.standard.elements.ICommonCategory
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object CategoryConverter : Converter<ICommonCategory> {
    override fun read(node: InputNode): ICommonCategory {
        val domain: String? = node.getAttribute("domain")?.value
        val text: String = node.value

        return Category(domain, text)
    }

    override fun write(node: OutputNode, value: ICommonCategory) {
        val writable = value.toWritable()

        writable.domain?.let { node.setAttribute("domain", it) }
        node.value = writable.text
    }
}