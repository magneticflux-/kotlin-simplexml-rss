package com.github.magneticflux.rss.namespaces.atom.converters

import com.github.magneticflux.rss.children
import com.github.magneticflux.rss.createChild
import com.github.magneticflux.rss.namespace
import com.github.magneticflux.rss.namespaces.Namespace
import com.github.magneticflux.rss.namespaces.atom.elements.AtomAuthor
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.2.0
 */
object AtomAuthorConverter : Converter<AtomAuthor> {
    override fun read(node: InputNode): AtomAuthor {
        lateinit var name: String
        var uri: String? = null
        var email: String? = null

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

        return AtomAuthor(name, uri, email)
    }

    override fun write(node: OutputNode, value: AtomAuthor) {
        val writable = value.toWritable()

        node.createChild(Namespace.ATOM.reference, "name", writable.name)
        writable.uri?.let { node.createChild(Namespace.ATOM.reference, "uri", it) }
        writable.email?.let { node.createChild(Namespace.ATOM.reference, "email", it) }
    }
}
