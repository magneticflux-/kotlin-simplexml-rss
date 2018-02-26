package com.github.magneticflux.rss

import com.github.magneticflux.rss.namespaces.Namespace
import com.github.magneticflux.rss.namespaces.Namespace.ATOM
import com.github.magneticflux.rss.namespaces.Namespace.DEFAULT
import com.github.magneticflux.rss.namespaces.Namespace.ITUNES
import com.github.magneticflux.rss.namespaces.Namespace.XML
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * A Persister that is used by various [Converter] objects.
 */
internal val fallbackPersister = createRssPersister()

/**
 * Gets a corresponding [Namespace] or `null` if none are found.
 */
internal val InputNode.namespace: Namespace?
    get() {
        return when {
            reference.equals(DEFAULT.reference, true) -> DEFAULT
            reference.equals(ITUNES.reference, true) -> ITUNES
            reference.equals(ATOM.reference, true) -> ATOM
            reference.equals(XML.reference, true) -> XML
            else -> null
        }
    }

/**
 * Creates an iterator over an InputNode's children.
 */
internal val InputNode.children: Iterator<InputNode>
    get() {
        return InputNodeChildIterator(this)
    }
/**
 * Creates an iterator over an InputNode's attributes.
 */
internal val InputNode.allAttributes: Iterator<InputNode>
    get() {
        return this.attributes.asSequence().map { this.attributes[it] }.iterator()
    }

/**
 * Create a child node containing only the given String with the specified namespace.
 */
internal fun OutputNode.createChild(reference: String = "", name: String, value: String) {
    val child = this.getChild(name)
    child.value = value
    child.reference = reference
}

/**
 * Create a child node containing only the given String with the specified namespace.
 */
internal fun OutputNode.createAttribute(
    reference: String = "",
    name: String,
    value: String,
    prefix: String? = null
) {
    val child = this.setAttribute(name, value)
    child.reference = reference
    if (prefix != null)
        child.namespaces.setReference(reference, prefix)
}

/**
 * An iterator that iterates over children of an [InputNode].
 */
private class InputNodeChildIterator(val rootNode: InputNode) : AbstractIterator<InputNode>() {
    override fun computeNext() {
        val next = rootNode.next
        if (next != null)
            setNext(next)
        else done()
    }
}
