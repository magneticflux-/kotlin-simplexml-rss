package com.github.magneticflux.rss

import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * A Persister that is used by various [Converter] objects.
 */
internal val fallbackPersister = createRssPersister()

/**
 * Gets the prefix + name or just name if prefix is null.
 */
internal val InputNode.fullName: String
    get() {
        val prefix = this.prefix
        val name = this.name
        return if (prefix.isNullOrEmpty()) name else "$prefix:$name"
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

private class InputNodeChildIterator(val rootNode: InputNode) : AbstractIterator<InputNode>() {
    override fun computeNext() {
        val next = rootNode.next
        if (next != null)
            setNext(next)
        else
            done()
    }
}