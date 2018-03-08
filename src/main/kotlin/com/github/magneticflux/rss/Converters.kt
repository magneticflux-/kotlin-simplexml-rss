package com.github.magneticflux.rss

import com.github.magneticflux.rss.namespaces.Namespace
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
        return when (reference.toLowerCase()) {
            Namespace.DEFAULT.reference -> Namespace.DEFAULT
            Namespace.ITUNES.reference -> Namespace.ITUNES
            Namespace.ATOM.reference -> Namespace.ATOM
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
 * Create a child node containing only the given String with the specified namespace.
 */
internal fun OutputNode.createChild(reference: String = "", name: String, value: String) {
    val child = this.getChild(name)
    child.value = value
    child.reference = reference
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
