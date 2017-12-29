package com.github.magneticflux.rss

import org.simpleframework.xml.transform.Transform
import org.threeten.bp.DayOfWeek
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.ChronoField
import java.net.MalformedURLException
import java.net.URL
import java.util.Locale

/**
 * @author Mitchell Skaggs
 * @since 1.0.0
 */
object DayOfWeekTransform : Transform<DayOfWeek> {
    override fun read(value: String): DayOfWeek {
        return when (value.toLowerCase()) {
            "monday" -> DayOfWeek.MONDAY
            "tuesday" -> DayOfWeek.TUESDAY
            "wednesday" -> DayOfWeek.WEDNESDAY
            "thursday" -> DayOfWeek.THURSDAY
            "friday" -> DayOfWeek.FRIDAY
            "saturday" -> DayOfWeek.SATURDAY
            "sunday" -> DayOfWeek.SUNDAY
            else -> throw IllegalArgumentException("No day of week for input '$value'")
        }
    }

    override fun write(value: DayOfWeek): String {
        return FORMATTER.format(value)
    }

    private val FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .appendText(ChronoField.DAY_OF_WEEK, TextStyle.FULL)
            .toFormatter(Locale.US)
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object ZonedDateTimeTransform : Transform<ZonedDateTime> {
    override fun read(value: String): ZonedDateTime {
        return ZonedDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(value))
    }

    override fun write(value: ZonedDateTime): String {
        return DateTimeFormatter.RFC_1123_DATE_TIME.format(value)
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object LocaleLanguageTransform : Transform<Locale> {
    override fun read(value: String): Locale {
        return Locale.forLanguageTag(value)
    }

    override fun write(value: Locale): String {
        return value.toLanguageTag()
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.1
 */
object URLTransform : Transform<URL> {
    override fun read(value: String): URL {
        return try {
            URL(value)
        } catch (e: MalformedURLException) {
            URL("http", null, -1, value)
        }
    }

    override fun write(value: URL): String {
        return if (value.authority == null)
            value.file
        else
            value.toExternalForm()
    }
}

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object DurationTransform : Transform<Duration> {
    override fun read(value: String): Duration {
        val segments = value.split(':').asReversed()
        val seconds = segments.getOrNull(0)?.toLongOrNull() ?: 0
        val minutes = segments.getOrNull(1)?.toLongOrNull() ?: 0
        val hours = segments.getOrNull(2)?.toLongOrNull() ?: 0

        return Duration.ofSeconds(seconds).plusMinutes(minutes).plusHours(hours)
    }

    override fun write(value: Duration): String {
        var reducedDuration = value

        val hours = reducedDuration.toHours()
        reducedDuration -= Duration.ofHours(hours)

        val minutes = reducedDuration.toMinutes()
        reducedDuration -= Duration.ofMinutes(minutes)

        val seconds = reducedDuration.seconds

        return arrayOf(hours, minutes, seconds).joinToString(separator = ":")
    }
}