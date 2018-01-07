package com.github.magneticflux.rss.namespaces.itunes.converters

import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesBlock
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesBlockConverter : Converter<ITunesBlock> {
    override fun read(node: InputNode): ITunesBlock {
        val text: String? = node.value
        return when (text) {
            "yes" -> ITunesBlock(true)
            else -> ITunesBlock(false)
        }
    }

    override fun write(node: OutputNode, value: ITunesBlock) {
        node.value = if (value.shouldBlock) "yes" else "no"
    }
}