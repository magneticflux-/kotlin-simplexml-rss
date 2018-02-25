package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.PersisterSpecification
import spock.lang.Unroll

class EnclosureTest extends PersisterSpecification {
	@Unroll
	def 'enclosure #object read/write'() {

		expect: 'the output XML equals the given XML'
		persister.write(object) == xml

		and: 'the input object equals the given object'
		persister.read(Enclosure, xml) == object

		where:
		url                              | length | type       | xml
		'http://www.example.com'.toURL() | 1000   | 'testType' | """<enclosure url="$url" length="$length" type="$type"/>"""
		object = new Enclosure(url, length, type)
	}
}
