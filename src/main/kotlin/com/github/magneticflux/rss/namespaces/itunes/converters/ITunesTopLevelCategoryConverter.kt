package com.github.magneticflux.rss.namespaces.itunes.converters

import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesSubCategory
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.standard.converters.children
import com.github.magneticflux.rss.namespaces.standard.converters.fallbackPersister
import com.github.magneticflux.rss.namespaces.standard.converters.fullName
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesTopLevelCategoryConverter : Converter<ITunesTopLevelCategory> {
    override fun read(node: InputNode): ITunesTopLevelCategory {
        val text = node.getAttribute("text").value
        val subCategories = mutableListOf<ITunesSubCategory>()

        node.children.forEach {
            when (it.fullName) {
                "itunes:category" -> subCategories += fallbackPersister.read(ITunesSubCategory::class.java, it)
            }
        }

        return ITunesTopLevelCategory(text, subCategories)
    }

    override fun write(node: OutputNode, value: ITunesTopLevelCategory) {
        node.setAttribute("text", value.text)
        value.itunesSubCategories.forEach {
            fallbackPersister.write(it, node)
        }
    }
}