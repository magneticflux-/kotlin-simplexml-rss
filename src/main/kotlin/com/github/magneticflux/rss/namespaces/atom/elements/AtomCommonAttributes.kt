package com.github.magneticflux.rss.namespaces.atom.elements

/**
 * Corresponds to `atomCommonAttributes` in RFC4287.
 *
 * @author Mitchell Skaggs
 * @since 1.2.0
 */
interface AtomCommonAttributes {
    val base: String?
    val lang: String?
}

/**
 * Corresponds to `atomTextConstruct` in RFC4287.
 *
 * @author Mitchell Skaggs
 * @since 1.2.0
 */
interface AtomTextConstruct : AtomCommonAttributes {
    val type: AtomTextType?
}

/**
 * Corresponds to `atomPlainTextConstruct` in RFC4287.
 *
 * @author Mitchell Skaggs
 * @since 1.2.0
 */
interface AtomPlainTextConstruct : AtomTextConstruct {
    override val type: AtomPlainTextType?
    val text: String
}

/**
 * Corresponds to `atomXHTMLTextConstruct` in RFC4287.
 *
 * @author Mitchell Skaggs
 * @since 1.2.0
 */
interface AtomXHTMLTextConstruct : AtomTextConstruct {
    override val type: AtomXHTMLTextType
    val text: String
}

sealed class AtomTextType

sealed class AtomPlainTextType : AtomTextType() {
    object TEXT : AtomPlainTextType()
    object HTML : AtomPlainTextType()
}

sealed class AtomXHTMLTextType : AtomTextType() {
    object XHTML : AtomXHTMLTextType()
}
