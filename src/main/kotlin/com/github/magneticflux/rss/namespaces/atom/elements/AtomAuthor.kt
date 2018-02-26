package com.github.magneticflux.rss.namespaces.atom.elements

import com.github.magneticflux.rss.namespaces.Namespace.ATOM
import com.github.magneticflux.rss.namespaces.atom.converters.AtomAuthorConverter
import com.github.magneticflux.rss.namespaces.standard.elements.HasReadWrite
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

/**
 * Properties common to all representations of a `<person>` element.
 *
 * @since 1.2.0
 */
interface ICommonAtomPerson : HasReadWrite<IAtomPerson, IWritableAtomPerson> {
    val name: String
    val uri: String?
    val email: String?
}

/**
 * The final RSS view of a `<person>` element. Defaults are used if applicable.
 *
 * @since 1.2.0
 */
interface IAtomPerson : ICommonAtomPerson {
    override fun toReadOnly(): IAtomPerson = this
}

/**
 * The literal contents of a `<person>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.2.0
 */
interface IWritableAtomPerson : ICommonAtomPerson {
    override fun toWritable(): IWritableAtomPerson = this
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
    override val name: String,
    override val uri: String?,
    override val email: String?
) : IAtomPerson, IWritableAtomPerson
