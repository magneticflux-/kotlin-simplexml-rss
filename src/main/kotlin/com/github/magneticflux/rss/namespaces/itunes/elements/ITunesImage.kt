package com.github.magneticflux.rss.namespaces.itunes.elements

import com.github.magneticflux.rss.namespaces.itunes.converters.ITunesImageConverter
import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import com.github.magneticflux.rss.namespaces.standard.elements.Item
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root
import java.net.URL

/**
 * This class represents an itunes:image in a [Channel] or an [Item].
 *
 * @author Mitchell Skaggs
 * @since 1.0.5
 * @see ITunesImageConverter
 */
@Root(name = "image")
@Namespace(reference = ITUNES_REFERENCE)
data class ITunesImage(
        val href: URL
)