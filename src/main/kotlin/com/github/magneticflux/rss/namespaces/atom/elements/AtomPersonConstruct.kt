package com.github.magneticflux.rss.namespaces.atom.elements

/**
 * Corresponds to `atomPersonConstruct` in RFC4287.
 *
 * @author Mitchell Skaggs
 * @since 1.2.0
 */
interface AtomPersonConstruct : AtomCommonAttributes {
    val name: String
    val uri: String?
    val email: String?
}
