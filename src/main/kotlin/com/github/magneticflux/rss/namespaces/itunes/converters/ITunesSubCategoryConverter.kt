package com.github.magneticflux.rss.namespaces.itunes.converters

import com.github.magneticflux.rss.namespaces.itunes.elements.ICommonITunesSubCategory
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesSubCategory
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesSubCategoryConverter : Converter<ICommonITunesSubCategory> {
    override fun read(node: InputNode): ICommonITunesSubCategory {
        val text = node.getAttribute("text").value

        return ITunesSubCategory(text)
    }

    override fun write(node: OutputNode, value: ICommonITunesSubCategory) {
        val writable = value.toWritable()

        node.setAttribute("text", writable.text)
    }
}