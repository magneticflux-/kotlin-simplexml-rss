package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.PersisterSpecification
import org.codehaus.groovy.runtime.StringBufferWriter
import spock.lang.Unroll

class EnclosureTest extends PersisterSpecification {
	@Unroll
	def 'enclosure #object read/write'() {

		when: 'the object is written to XML'
		def output = new StringBuffer()
		persister.write(object, new StringBufferWriter(output))

		then: 'the output XML equals the given XML'
		output.toString() == xml

		when: 'the XML is read to an object'
		def input = persister.read(Enclosure, xml)

		then: 'the input object equals the given object'
		input == object

		where:
		url                              | length | type       | xml
		'http://www.example.com'.toURL() | 1000   | 'testType' | """<enclosure url="$url" length="$length" type="$type"/>"""
		object = new Enclosure(url, length, type)
	}
}
