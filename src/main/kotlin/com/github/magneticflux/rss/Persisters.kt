package com.github.magneticflux.rss

import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesCompleteConverter
import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesDurationConverter
import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesImageConverter
import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesSubCategoryConverter
import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesTopLevelCategoryConverter
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesComplete
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesDuration
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesImage
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesSubCategory
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesTopLevelCategory
import com.github.magneticflux.rss.namespaces.standard.converters.CategoryConverter
import com.github.magneticflux.rss.namespaces.standard.converters.ChannelConverter
import com.github.magneticflux.rss.namespaces.standard.converters.CloudConverter
import com.github.magneticflux.rss.namespaces.standard.converters.EnclosureConverter
import com.github.magneticflux.rss.namespaces.standard.converters.GuidConverter
import com.github.magneticflux.rss.namespaces.standard.converters.ImageConverter
import com.github.magneticflux.rss.namespaces.standard.converters.ItemConverter
import com.github.magneticflux.rss.namespaces.standard.converters.RssFeedConverter
import com.github.magneticflux.rss.namespaces.standard.converters.SourceConverter
import com.github.magneticflux.rss.namespaces.standard.converters.TextInputConverter
import com.github.magneticflux.rss.namespaces.standard.elements.Category
import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import com.github.magneticflux.rss.namespaces.standard.elements.Cloud
import com.github.magneticflux.rss.namespaces.standard.elements.Enclosure
import com.github.magneticflux.rss.namespaces.standard.elements.Guid
import com.github.magneticflux.rss.namespaces.standard.elements.Image
import com.github.magneticflux.rss.namespaces.standard.elements.Item
import com.github.magneticflux.rss.namespaces.standard.elements.Rss
import com.github.magneticflux.rss.namespaces.standard.elements.Source
import com.github.magneticflux.rss.namespaces.standard.elements.TextInput
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
                this.bind(Rss::class.java, RssFeedConverter)
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
                this.bind(ITunesDuration::class.java, ITunesDurationConverter)
                this.bind(ITunesImage::class.java, ITunesImageConverter)
                this.bind(ITunesComplete::class.java, ITunesCompleteConverter)
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