package com.github.magneticflux.rss.namespaces.itunes.elements

import com.github.magneticflux.rss.namespaces.standard.elements.Channel
import com.github.magneticflux.rss.namespaces.standard.elements.Item

/**
 * This class represents the value of an itunes:explicit element in a [Channel] or an [Item]
 */
enum class ITunesExplicitStatus {
    YES, NONE, CLEAN
}