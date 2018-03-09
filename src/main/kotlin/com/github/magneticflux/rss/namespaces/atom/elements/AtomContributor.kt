package com.github.magneticflux.rss.namespaces.atom.elements

import com.github.magneticflux.rss.namespaces.Namespace.ATOM
import com.github.magneticflux.rss.namespaces.atom.converters.AtomAuthorConverter
import com.github.magneticflux.rss.namespaces.standard.elements.HasReadWrite
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

/**
 * Properties common to all representations of a `<contributor>` element.
 *
 * @since 1.2.0
 */
interface ICommonAtomContributor : HasReadWrite<IAtomContributor, IWritableAtomContributor>,
    AtomPersonConstruct

/**
 * The final RSS view of a `<contributor>` element. Defaults are used if applicable.
 *
 * @since 1.2.0
 */
interface IAtomContributor : ICommonAtomContributor {
    override fun toReadOnly(): IAtomContributor = this
}

/**
 * The literal contents of a `<contributor>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.2.0
 */
interface IWritableAtomContributor : ICommonAtomContributor {
    override fun toWritable(): IWritableAtomContributor = this
}

/**
 * This class represents an Author object in a [AtomFeed].
 *
 * @author Mitchell Skaggs
 * @since 1.2.0
 * @see AtomAuthorConverter
 */
@Root(name = "contributor")
@Namespace(reference = ATOM.reference)
data class AtomContributor(
    override val base: String?,
    override val lang: String?,
    override val name: String,
    override val uri: String?,
    override val email: String?
) : IAtomContributor, IWritableAtomContributor
