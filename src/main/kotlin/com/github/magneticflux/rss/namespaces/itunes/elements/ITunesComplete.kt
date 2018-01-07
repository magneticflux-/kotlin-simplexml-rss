package com.github.magneticflux.rss.namespaces.itunes.elements

import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesCompleteConverter
import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

/**
 * This class represents an itunes:complete in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesCompleteConverter
 */
@Root(name = "complete")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesComplete(
        val isComplete: Boolean
)