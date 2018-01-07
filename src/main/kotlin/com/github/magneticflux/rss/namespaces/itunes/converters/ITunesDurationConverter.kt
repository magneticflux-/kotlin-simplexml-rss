package com.github.magneticflux.rss.namespaces.itunes.converters

import com.github.magneticflux.rss.DurationTransform
import com.github.magneticflux.rss.namespaces.itunes.elements.ITunesDuration
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

/**
 * @author Mitchell Skaggs
 * @since 1.0.5
 */
object ITunesDurationConverter : Converter<ITunesDuration> {
    override fun read(node: InputNode): ITunesDuration {
        val duration = DurationTransform.read(node.value)

        return ITunesDuration(duration)
    }

    override fun write(node: OutputNode, value: ITunesDuration) {
        node.value = DurationTransform.write(value.duration)
    }
}