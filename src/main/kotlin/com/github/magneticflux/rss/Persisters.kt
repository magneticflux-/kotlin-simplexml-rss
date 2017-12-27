package com.github.magneticflux.rss

import com.github.magneticflux.rss.itunes.ITunesExplicit
import com.github.magneticflux.rss.itunes.ITunesExplicitConverter
import com.github.magneticflux.rss.itunes.ITunesSubCategory
import com.github.magneticflux.rss.itunes.ITunesSubCategoryConverter
import com.github.magneticflux.rss.itunes.ITunesSubtitle
import com.github.magneticflux.rss.itunes.ITunesSubtitleConverter
import com.github.magneticflux.rss.itunes.ITunesTopLevelCategory
import com.github.magneticflux.rss.itunes.ITunesTopLevelCategoryConverter
import org.simpleframework.xml.convert.Registry
import org.simpleframework.xml.convert.RegistryStrategy
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.strategy.Strategy
import org.simpleframework.xml.transform.Matcher
import org.simpleframework.xml.transform.RegistryMatcher
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import java.net.URL
import java.util.Locale

/**
 * Creates a new [Persister] that can read and write RSS documents.
 *
 * @since 1.0.0
 */
fun createRssPersister(): Persister = Persister(createRssStrategy(), createRssMatcher())

/**
 * Creates a new [Strategy] that has RSS object converters bound to it.
 *
 * @since 1.0.4
 */
fun createRssStrategy(): Strategy {
    return RegistryStrategy(
            Registry().apply {
                this.bind(RssFeed::class.java, RssFeedConverter)
                this.bind(Channel::class.java, ChannelConverter)
                this.bind(Item::class.java, ItemConverter)
                this.bind(Category::class.java, CategoryConverter)
                this.bind(Image::class.java, ImageConverter)
                this.bind(Cloud::class.java, CloudConverter)
                this.bind(TextInput::class.java, TextInputConverter)
                this.bind(Enclosure::class.java, EnclosureConverter)
                this.bind(Guid::class.java, GuidConverter)
                this.bind(Source::class.java, SourceConverter)
                this.bind(ITunesTopLevelCategory::class.java, ITunesTopLevelCategoryConverter)
                this.bind(ITunesSubCategory::class.java, ITunesSubCategoryConverter)
                this.bind(ITunesExplicit::class.java, ITunesExplicitConverter)
                this.bind(ITunesSubtitle::class.java, ITunesSubtitleConverter)
            })
}

/**
 * Creates a new [Matcher] that has RSS primitive converters bound to it.
 *
 * @since 1.0.4
 */
fun createRssMatcher(): Matcher {
    return RegistryMatcher().apply {
        this.bind(DayOfWeek::class.java, DayOfWeekTransform)
        this.bind(ZonedDateTime::class.java, ZonedDateTimeTransform)
        this.bind(Locale::class.java, LocaleLanguageTransform)
        this.bind(URL::class.java, URLTransform)
    }
}