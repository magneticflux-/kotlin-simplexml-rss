package com.github.magneticflux.rss

import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.net.URL
import java.util.*

/**
 * Created by Mitchell Skaggs on 12/18/2017.
 */

class LocaleLanguageConverter : Converter<Locale> {
    override fun read(node: InputNode): Locale {
        return Locale.forLanguageTag(node.value)
    }

    override fun write(node: OutputNode, value: Locale) {
        node.value = value.toLanguageTag()
    }
}

class URLConverter : Converter<URL> {
    override fun read(node: InputNode): URL {
        return URL(node.value)
    }

    override fun write(node: OutputNode, value: URL) {
        node.value = value.toExternalForm()
    }
}

class ZonedDateTimeConverter : Converter<ZonedDateTime> {
    override fun read(node: InputNode): ZonedDateTime {
        return ZonedDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(node.value))
    }

    override fun write(node: OutputNode, value: ZonedDateTime) {
        node.value = DateTimeFormatter.RFC_1123_DATE_TIME.format(value)
    }
}