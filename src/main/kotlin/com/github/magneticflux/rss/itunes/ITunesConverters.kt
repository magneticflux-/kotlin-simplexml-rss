package com.github.magneticflux.rss.itunes

import com.github.magneticflux.rss.children
import com.github.magneticflux.rss.fallbackPersister
import com.github.magneticflux.rss.fullName
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
object ITunesExplicitConverter : Converter<ITunesExplicit> {
    override fun read(node: InputNode): ITunesExplicit {
        val value: String? = node.value
        return when {
            value.equals("yes", true) -> ITunesExplicit.YES
            value.equals("no", true) -> ITunesExplicit.NO
            else -> throw IllegalStateException("Incorrect 'itunes:explicit' text $value is not 'yes' or 'no' ignoring case")
        }
    }

    override fun write(node: OutputNode, value: ITunesExplicit) {
        node.value = when (value.isExplicit) {
            true -> "yes"
            false -> "no"
        }
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesSubtitleConverter : Converter<ITunesSubtitle> {
    override fun read(node: InputNode): ITunesSubtitle {
        val text: String? = node.value

        return ITunesSubtitle(text.orEmpty())
    }

    override fun write(node: OutputNode, value: ITunesSubtitle) {
        node.value = value.text
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesSummaryConverter : Converter<ITunesSummary> {
    override fun read(node: InputNode): ITunesSummary {
        val text: String? = node.value

        return ITunesSummary(text.orEmpty())
    }

    override fun write(node: OutputNode, value: ITunesSummary) {
        node.value = value.text
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesAuthorConverter : Converter<ITunesAuthor> {
    override fun read(node: InputNode): ITunesAuthor {
        val text: String? = node.value

        return ITunesAuthor(text.orEmpty())
    }

    override fun write(node: OutputNode, value: ITunesAuthor) {
        node.value = value.text
    }
}