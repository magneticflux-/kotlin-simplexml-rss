package com.github.magneticflux.rss.namespaces.standard.converters

import com.github.magneticflux.rss.namespaces.standard.elements.Cloud
import com.github.magneticflux.rss.namespaces.standard.elements.ICommonCloud
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.4
 */
object CloudConverter : Converter<ICommonCloud> {
    override fun read(node: InputNode): ICommonCloud {
        val domain = node.getAttribute("domain").value
        val path = node.getAttribute("path").value
        val port = node.getAttribute("port").value.toInt()
        val protocol = node.getAttribute("protocol").value
        val registerProcedure = node.getAttribute("registerProcedure").value

        return Cloud(domain, path, port, protocol, registerProcedure)
    }

    override fun write(node: OutputNode, value: ICommonCloud) {
        val writable = value.toWritable()

        node.setAttribute("domain", writable.domain)
        node.setAttribute("path", writable.path)
        node.setAttribute("port", writable.port.toString())
        node.setAttribute("protocol", writable.protocol)
        node.setAttribute("registerProcedure", writable.registerProcedure)
    }
}