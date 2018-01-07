package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.TextInputConverter
import org.simpleframework.xml.Root
import java.net.URL

/**
 * This class represents a text input object in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see TextInputConverter
 */
@Root(name = "textInput", strict = false)
data class TextInput(
        val title: String,
        val description: String,
        val name: String,
        val link: URL
)