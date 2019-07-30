package com.github.magneticflux.rss.namespaces.atom.elements

import com.github.magneticflux.rss.namespaces.Namespace.ATOM
import com.github.magneticflux.rss.namespaces.atom.converters.AtomFeedConverter
import com.github.magneticflux.rss.namespaces.standard.elements.HasReadWrite
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

/**
 * Properties common to all representations of a `<feed>` element.
 *
 * @since 1.2.0
 */
interface ICommonAtomFeed : HasReadWrite<IAtomFeed, IWritableAtomFeed>, AtomCommonAttributes {
    val author: List<AtomAuthor>
}

/**
 * The final RSS view of a `<feed>` element. Defaults are used if applicable.
 *
 * @since 1.2.0
 */
interface IAtomFeed : ICommonAtomFeed {
    override fun toReadOnly(): IAtomFeed = this
}

/**
 * The literal contents of a `<feed>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.2.0
 */
interface IWritableAtomFeed : ICommonAtomFeed {
    override fun toWritable(): IWritableAtomFeed = this
}

/**
 * This class represents an atom:feed.
 *
 * @author Mitchell Skaggs
 * @since 1.2.0
 * @see AtomFeedConverter
 */
@Root(name = "feed")
@Namespace(reference = ATOM.reference)
data class AtomFeed(
    override val base: String?,
    override val lang: String?,
    override val author: List<AtomAuthor>
) : IAtomFeed, IWritableAtomFeed
