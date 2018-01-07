package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import com.github.magneticflux.rss.namespaces.standard.elements.Rss
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object RssFeedConverter : Converter<Rss> {
    override fun read(node: InputNode): Rss {
        val version = node.getAttribute("version").value

        lateinit var channel: Channel

        node.children.forEach {
            when (it.fullName) {
                "channel" -> {
                    channel = fallbackPersister.read(Channel::class.java, it)
                }
            }
        }

        return Rss(
                version,
                channel)
    }

    override fun write(node: OutputNode, value: Rss) {
        node.reference = null
        node.setAttribute("version", value.version)
        fallbackPersister.write(value.channel, node)
    }
}