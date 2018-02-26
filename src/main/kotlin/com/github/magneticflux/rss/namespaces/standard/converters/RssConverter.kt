package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.children
import com.github.magneticflux.rss.fallbackPersister
import com.github.magneticflux.rss.namespace
import com.github.magneticflux.rss.namespaces.Namespace
import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import com.github.magneticflux.rss.namespaces.standard.elements.ICommonRss
import com.github.magneticflux.rss.namespaces.standard.elements.Rss
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object RssConverter : Converter<ICommonRss> {
    override fun read(node: InputNode): ICommonRss {
        val version = node.getAttribute("version").value

        lateinit var channel: Channel

        node.children.forEach {
            when (it.namespace) {
                Namespace.DEFAULT -> {
                    when (it.name) {
                        "channel" -> {
                            channel = fallbackPersister.read(Channel::class.java, it)
                        }
                    }
                }
            }
        }

        return Rss(
            version,
            channel
        )
    }

    override fun write(node: OutputNode, value: ICommonRss) {
        val writable = value.toWritable()

        node.reference = null
        node.setAttribute("version", writable.version)
        fallbackPersister.write(writable.channel, node)
    }
}
