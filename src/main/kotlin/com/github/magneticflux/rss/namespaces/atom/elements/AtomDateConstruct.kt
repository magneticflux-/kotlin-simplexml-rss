package com.github.magneticflux.rss.namespaces.atom.elements

import org.threeten.bp.Instant

/**
 * Corresponds to `atomPersonConstruct` in RFC4287.
 *
 * @author Mitchell Skaggs
 * @since 1.2.0
 */
interface AtomDateConstruct : AtomCommonAttributes {
    val dateTime: Instant
}
