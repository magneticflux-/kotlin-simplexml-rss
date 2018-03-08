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
}
