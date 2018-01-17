package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.TextInputConverter
import org.simpleframework.xml.Root
import java.net.URL

/**
 * Properties common to all representations of a `<textInput>` element.
 *
 * @since 1.1.0
 */
interface ICommonTextInput : HasReadWrite<ITextInput, IWritableTextInput> {
    val title: String
    val description: String
    val name: String
    val link: URL
}

/**
 * The final RSS view of a `<textInput>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface ITextInput : ICommonTextInput {
    override fun toReadOnly(): ITextInput = this
}

/**
 * The literal contents of a `<textInput>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableTextInput : ICommonTextInput {
    override fun toWritable(): IWritableTextInput = this
}

/**
 * This class represents a text input object in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see TextInputConverter
 */
@Root(name = "textInput", strict = false)
data class TextInput(
    override val title: String,
    override val description: String,
    override val name: String,
    override val link: URL
) : ITextInput, IWritableTextInput