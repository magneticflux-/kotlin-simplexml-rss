package com.github.magneticflux.rss.namespaces.atom.elements

import com.github.magneticflux.rss.namespaces.Namespace.ATOM
import com.github.magneticflux.rss.namespaces.atom.converters.AtomAuthorConverter
import com.github.magneticflux.rss.namespaces.standard.elements.HasReadWrite
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

/**
 * Properties common to all representations of an `<author>` element.
 *
 * @since 1.2.0
 */
interface ICommonAtomAuthor : HasReadWrite<IAtomAuthor, IWritableAtomAuthor>, AtomPersonConstruct

/**
 * The final RSS view of an `<author>` element. Defaults are used if applicable.
 *
 * @since 1.2.0
 */
interface IAtomAuthor : ICommonAtomAuthor {
    override fun toReadOnly(): IAtomAuthor = this
}

/**
 * The literal contents of an `<author>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.2.0
 */
interface IWritableAtomAuthor : ICommonAtomAuthor {
    override fun toWritable(): IWritableAtomAuthor = this
}

/**
 * This class represents an Author object in a [AtomFeed].
 *
 * @author Mitchell Skaggs
 * @since 1.2.0
 * @see AtomAuthorConverter
 */
@Root(name = "author")
@Namespace(reference = ATOM.reference)
data class AtomAuthor(
    override val base: String?,
    override val lang: String?,
    override val name: String,
    override val uri: String?,
    override val email: String?
) : IAtomAuthor, IWritableAtomAuthor
