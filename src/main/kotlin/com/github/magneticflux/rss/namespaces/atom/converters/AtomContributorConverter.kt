package com.github.magneticflux.rss.namespaces.atom.converters

import com.github.magneticflux.rss.allAttributes
import com.github.magneticflux.rss.children
import com.github.magneticflux.rss.createChild
import com.github.magneticflux.rss.namespace
import com.github.magneticflux.rss.namespaces.Namespace
import com.github.magneticflux.rss.namespaces.atom.elements.AtomContributor
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.2.0
 */
object AtomContributorConverter : Converter<AtomContributor> {
    override fun read(node: InputNode): AtomContributor {
        var base: String? = null
        var lang: String? = null

        lateinit var name: String
        var uri: String? = null
        var email: String? = null

        node.allAttributes.forEach {
            when (it.namespace) {
                Namespace.XML -> {
                    when (it.name) {
                        "base" -> base = it.value
                        "lang" -> lang = it.value
                    }
                }
            }
        }

        node.children.forEach {
            when (it.namespace) {
                Namespace.ATOM -> {
                    when (it.name) {
                        "name" -> name = it.value
                        "uri" -> uri = it.value
                        "email" -> email = it.value
                    }
                }
            }
        }

        return AtomContributor(base, lang, name, uri, email)
    }

    override fun write(node: OutputNode, value: AtomContributor) {
        val writable = value.toWritable()

        node.createChild(Namespace.ATOM.reference, "name", writable.name)
        writable.uri?.let { node.createChild(Namespace.ATOM.reference, "uri", it) }
        writable.email?.let { node.createChild(Namespace.ATOM.reference, "email", it) }
    }
}
