package com.github.magneticflux.rss

import org.simpleframework.xml.transform.Transform
import org.threeten.bp.DayOfWeek
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.ChronoField
import java.util.Locale

/**
 * @author Mitchell Skaggs
 * @since 1.0.0
 */
object DayOfWeekTransform : Transform<DayOfWeek> {
    override fun read(value: String): DayOfWeek {
        return DayOfWeek.from(FORMATTER.parse(value))
    }

    override fun write(value: DayOfWeek): String {
        return FORMATTER.format(value)
    }

    private val FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .appendText(ChronoField.DAY_OF_WEEK, TextStyle.FULL)
            .toFormatter(Locale.US)
}