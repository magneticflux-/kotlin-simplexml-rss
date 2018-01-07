package com.github.magneticflux.rss.namespaces.default.converters

import com.github.magneticflux.rss.namespaces.default.elements.Channel
import com.github.magneticflux.rss.namespaces.default.elements.RssFeed
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object RssFeedConverter : Converter<RssFeed> {
    override fun read(node: InputNode): RssFeed {
        val version = node.getAttribute("version").value

        lateinit var channel: Channel

        node.children.forEach {
            when (it.fullName) {
                "channel" -> {
                    channel = fallbackPersister.read(Channel::class.java, it)
                }
            }
        }

        return RssFeed(
                version,
                channel)
    }

    override fun write(node: OutputNode, value: RssFeed) {
        node.reference = null
        node.setAttribute("version", value.version)
        fallbackPersister.write(value.channel, node)
    }
}