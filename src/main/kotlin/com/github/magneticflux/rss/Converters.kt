package com.github.magneticflux.rss

import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.net.URL
import java.util.Locale

/**
 * This converts a string such as `en-us` to a [Locale] based on the language code.
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 */
class LocaleLanguageConverter : Converter<Locale> {
    override fun read(node: InputNode): Locale {
        return Locale.forLanguageTag(node.value)
    }

    override fun write(node: OutputNode, value: Locale) {
        node.value = value.toLanguageTag()
    }
}

/**
 * This converts a string that forms a URL to a [URL] object.
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 */
class URLConverter : Converter<URL> {
    override fun read(node: InputNode): URL {
        return URL(node.value)
    }

    override fun write(node: OutputNode, value: URL) {
        node.value = value.toExternalForm()
    }
}

/**
 * This converts an RFC-1123 datetime to a [ZonedDateTime] object.
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 */
class ZonedDateTimeConverter : Converter<ZonedDateTime> {
    override fun read(node: InputNode): ZonedDateTime {
        return ZonedDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(node.value))
    }

    override fun write(node: OutputNode, value: ZonedDateTime) {
        node.value = DateTimeFormatter.RFC_1123_DATE_TIME.format(value)
    }
}