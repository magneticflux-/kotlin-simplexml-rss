package com.github.magneticflux.rss

import org.codehaus.groovy.runtime.StringBufferWriter
import org.simpleframework.xml.core.Persister

/**
 * This extension function helps getting XML data from a persister as a String.
 *
 * @param any the object to write
 * @return a String containing the written XML
 * @receiver the persister with which to write the XML
 */
fun Persister.write(any: Any): String {
    val buffer = StringBuffer()
    this.write(any, StringBufferWriter(buffer))
    return buffer.toString()
}
