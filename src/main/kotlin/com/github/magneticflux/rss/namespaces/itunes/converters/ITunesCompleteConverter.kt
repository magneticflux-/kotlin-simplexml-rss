package com.github.magneticflux.rss.namespaces.itunes.converters

import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesComplete
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesCompleteConverter : Converter<ITunesComplete> {
    override fun read(node: InputNode): ITunesComplete {
        val text: String? = node.value
        return when (text) {
            "yes" -> ITunesComplete(true)
            else -> ITunesComplete(false)
        }
    }

    override fun write(node: OutputNode, value: ITunesComplete) {
        node.value = if (value.isComplete) "yes" else "no"
    }
}