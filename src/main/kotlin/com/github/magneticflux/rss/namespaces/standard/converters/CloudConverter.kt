package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.namespaces.standard.elements.Cloud
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object CloudConverter : Converter<Cloud> {
    override fun read(node: InputNode): Cloud {
        val domain = node.getAttribute("domain").value
        val path = node.getAttribute("path").value
        val port = node.getAttribute("port").value.toInt()
        val protocol = node.getAttribute("protocol").value
        val registerProcedure = node.getAttribute("registerProcedure").value

        return Cloud(domain, path, port, protocol, registerProcedure)
    }

    override fun write(node: OutputNode, value: Cloud) {
        node.setAttribute("domain", value.domain)
        node.setAttribute("path", value.path)
        node.setAttribute("port", value.port.toString())
        node.setAttribute("protocol", value.protocol)
        node.setAttribute("registerProcedure", value.registerProcedure)
    }
}