package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.URLTransform
import com.github.magneticflux.rss.children
import com.github.magneticflux.rss.fallbackPersister
import com.github.magneticflux.rss.namespace
import com.github.magneticflux.rss.namespaces.Namespace
import com.github.magneticflux.rss.namespaces.standard.elements.ICommonTextInput
import com.github.magneticflux.rss.namespaces.standard.elements.TextInput
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import java.net.URL

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object TextInputConverter : Converter<ICommonTextInput> {
    override fun read(node: InputNode): ICommonTextInput {
        lateinit var title: String
        lateinit var description: String
        lateinit var name: String
        lateinit var link: URL

        node.children.forEach {
            when (it.namespace) {
                Namespace.DEFAULT -> {
                    when (it.name) {
                        "title" -> title = fallbackPersister.read(String::class.java, it)
                        "description" -> description =
                                fallbackPersister.read(String::class.java, it)
                        "name" -> name = fallbackPersister.read(String::class.java, it)
                        "link" -> link = fallbackPersister.read(URL::class.java, it)
                    }
                }
            }
        }

        return TextInput(title, description, name, link)
    }

    override fun write(node: OutputNode, value: ICommonTextInput) {
        val writable = value.toWritable()

        node.getChild("title").value = writable.title
        node.getChild("description").value = writable.description
        node.getChild("name").value = writable.name
        node.getChild("link").value = URLTransform.write(writable.link)
    }
}
