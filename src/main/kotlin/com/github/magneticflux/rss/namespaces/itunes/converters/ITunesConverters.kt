package com.github.magneticflux.rss.namespaces.itunes.converters

import com.github.magneticflux.rss.DurationTransform
import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.namespaces.default.converters.children
import com.github.magneticflux.rss.namespaces.default.converters.fallbackPersister
import com.github.magneticflux.rss.namespaces.default.converters.fullName
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesBlock
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesComplete
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesDuration
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesSubCategory
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesTopLevelCategory
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import java.net.URL

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

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesSubCategoryConverter : Converter<ITunesSubCategory> {
    override fun read(node: InputNode): ITunesSubCategory {
        val text = node.getAttribute("text").value

        return ITunesSubCategory(text)
    }

    override fun write(node: OutputNode, value: ITunesSubCategory) {
        node.setAttribute("text", value.text)
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesDurationConverter : Converter<ITunesDuration> {
    override fun read(node: InputNode): ITunesDuration {
        val duration = DurationTransform.read(node.value)

        return ITunesDuration(duration)
    }

    override fun write(node: OutputNode, value: ITunesDuration) {
        node.value = DurationTransform.write(value.duration)
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesImageConverter : Converter<ITunesImage> {
    override fun read(node: InputNode): ITunesImage {
        val href = fallbackPersister.read(URL::class.java, node.getAttribute("href"))

        return ITunesImage(href)
    }

    override fun write(node: OutputNode, value: ITunesImage) {
        node.setAttribute("href", URLTransform.write(value.href))
    }
}

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