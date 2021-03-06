package com.github.magneticflux.rss.namespaces.itunes.converters

import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.fallbackPersister
import com.github.magneticflux.rss.namespaces.itunes.elements.ICommonITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesImage
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import java.net.URL

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesImageConverter : Converter<ICommonITunesImage> {
    override fun read(node: InputNode): ICommonITunesImage {
        val href = fallbackPersister.read(URL::class.java, node.getAttribute("href"))

        return ITunesImage(href)
    }

    override fun write(node: OutputNode, value: ICommonITunesImage) {
        val writable = value.toWritable()

        node.setAttribute("href", URLTransform.write(writable.href))
    }
}