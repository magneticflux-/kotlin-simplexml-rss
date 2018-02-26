package com.github.magneticflux.rss.namespaces.atom.converters

import com.github.magneticflux.rss.allAttributes
import com.github.magneticflux.rss.children
import com.github.magneticflux.rss.createAttribute
import com.github.magneticflux.rss.fallbackPersister
import com.github.magneticflux.rss.namespace
import com.github.magneticflux.rss.namespaces.Namespace
import com.github.magneticflux.rss.namespaces.atom.elements.AtomAuthor
import com.github.magneticflux.rss.namespaces.atom.elements.AtomFeed
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.2.0
 */
object AtomFeedConverter : Converter<AtomFeed> {
    override fun read(node: InputNode): AtomFeed {
        var xmlBase: String? = null
        var xmlLang: String? = null

        val author: MutableList<AtomAuthor> = mutableListOf()

        node.allAttributes.forEach {
            when (it.namespace) {
                Namespace.XML -> {
                    when (it.name) {
                        "base" -> xmlBase = it.value
                        "lang" -> xmlLang = it.value
                    }
                }
            }
        }

        node.children.forEach {
            when (it.namespace) {
                Namespace.ATOM -> {
                    when (it.name) {
                        "author" -> author += fallbackPersister.read(AtomAuthor::class.java, it)
                    }
                }
            }
        }

        return AtomFeed(xmlBase, xmlLang, author)
    }

    override fun write(node: OutputNode, value: AtomFeed) {
        val writable = value.toWritable()

        writable.base?.let { node.createAttribute(Namespace.XML.reference, "base", it, "xml") }
        writable.lang?.let { node.createAttribute(Namespace.XML.reference, "lang", it, "xml") }

        writable.author.forEach { fallbackPersister.write(it, node) }
    }
}
