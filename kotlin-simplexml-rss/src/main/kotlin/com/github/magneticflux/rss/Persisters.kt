package com.github.magneticflux.rss

import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.convert.Registry
import org.simpleframework.xml.convert.RegistryStrategy
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.transform.RegistryMatcher
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import java.net.URL
import java.util.Locale

fun createDefaultPersister(): Persister = Persister(
        RegistryStrategy(Registry().apply {
        }, AnnotationStrategy()),
        RegistryMatcher().apply {
            this.bind(DayOfWeek::class.java, DayOfWeekTransform)
            this.bind(ZonedDateTime::class.java, ZonedDateTimeTransform)
            this.bind(Locale::class.java, LocaleLanguageTransform)
            this.bind(URL::class.java, URLTransform)
        })