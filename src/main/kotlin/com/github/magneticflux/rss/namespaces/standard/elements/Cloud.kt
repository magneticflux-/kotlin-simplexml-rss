package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.namespaces.standard.converters.CloudConverter
import org.simpleframework.xml.Root

/**
 * Properties common to all representations of a `<cloud>` element.
 *
 * @since 1.1.0
 */
interface ICloudCommon : HasReadWrite<ICloud, IWritableCloud> {
    val domain: String
    val path: String
    val port: Int
    val protocol: String
    val registerProcedure: String
}

/**
 * The final RSS view of a `<cloud>` element. Defaults are used if applicable.
 *
 * @since 1.1.0
 */
interface ICloud : ICloudCommon {
    override fun toReadOnly(): ICloud = this
}

/**
 * The literal contents of a `<cloud>` element. Elements with defaults may be omitted or invalid.
 *
 * @since 1.1.0
 */
interface IWritableCloud : ICloudCommon {
    override fun toWritable(): IWritableCloud = this
}

/**
 * This class represents a cloud object in a [Channel].
 *
 * @author Mitchell Skaggs
 * @since 1.0.0
 * @see CloudConverter
 */
@Root(name = "cloud", strict = false)
data class Cloud(
        override val domain: String,
        override val path: String,
        override val port: Int,
        override val protocol: String,
        override val registerProcedure: String
) : ICloud, IWritableCloud