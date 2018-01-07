package com.github.magneticflux.rss.namespaces.itunes.elements

import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesDurationConverter
import com.github.magneticflux.rss.namespaces.standard.elements.Item
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root
import org.threeten.bp.Duration

/**
 * This class represents an itunes:duration in an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesDurationConverter
 */
@Root(name = "duration")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesDuration(
        val duration: Duration
)