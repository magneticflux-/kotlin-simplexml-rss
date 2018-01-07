package com.github.magneticflux.rss.namespaces.default.converters

import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.namespaces.default.elements.TextInput
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import java.net.URL

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object TextInputConverter : Converter<TextInput> {
    override fun read(node: InputNode): TextInput {
        lateinit var title: String
        lateinit var description: String
        lateinit var name: String
        lateinit var link: URL

        node.children.forEach {
            when (it.fullName) {
                "title" -> title = fallbackPersister.read(String::class.java, it)
                "description" -> description = fallbackPersister.read(String::class.java, it)
                "name" -> name = fallbackPersister.read(String::class.java, it)
                "link" -> link = fallbackPersister.read(URL::class.java, it)
            }
        }

        return TextInput(title, description, name, link)
    }

    override fun write(node: OutputNode, value: TextInput) {
        node.getChild("title").value = value.title
        node.getChild("description").value = value.description
        node.getChild("name").value = value.name
        node.getChild("link").value = URLTransform.write(value.link)
    }
}