package com.github.magneticflux.rss.namespaces.itunes.converters

import com.github.magneticflux.rss.children
import com.github.magneticflux.rss.fallbackPersister
import com.github.magneticflux.rss.fullName
import com.github.magneticflux.rss.namespaces.itunes.elements.ICommonITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesSubCategory
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesTopLevelCategory
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesTopLevelCategoryConverter : Converter<ICommonITunesTopLevelCategory> {
    override fun read(node: InputNode): ICommonITunesTopLevelCategory {
        val text = node.getAttribute("text").value
        val subCategories = mutableListOf<ITunesSubCategory>()

        node.children.forEach {
            when (it.fullName) {
                "itunes:category" -> subCategories += fallbackPersister.read(
                    ITunesSubCategory::class.java,
                    it
                )
            }
        }

        return ITunesTopLevelCategory(text, subCategories)
    }

    override fun write(node: OutputNode, value: ICommonITunesTopLevelCategory) {
        val writable = value.toWritable()

        node.setAttribute("text", writable.text)
        writable.iTunesSubCategories.forEach {
            fallbackPersister.write(it, node)
        }
    }
}