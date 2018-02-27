package com.github.magneticflux.rss.namespaces

/**
 * @author Mitchell Skaggs
 * @since 1.1.2
 */
sealed class Namespace {
    object DEFAULT : Namespace() {
        const val reference: String = ""
    }

    object ITUNES : Namespace() {
        const val reference: String = "http://www.itunes.com/dtds/podcast-1.0.dtd"
    }

    object ATOM : Namespace() {
        const val reference: String = "http://www.w3.org/2005/Atom"
    }

    /**
     * "Note that unlike all other XML namespaces, both the name and the prefix are specified; i.e., if you want XML 1.0 processors to recognize this namespace, you must use the reserved prefix `xml:`."
     */
    object XML : Namespace() {
        const val reference: String = "http://www.w3.org/XML/1998/namespace"
    }
}
