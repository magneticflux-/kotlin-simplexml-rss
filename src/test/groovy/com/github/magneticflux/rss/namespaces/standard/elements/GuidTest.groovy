package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.PersisterSpecification
import spock.lang.Unroll

class GuidTest extends PersisterSpecification {
	@Unroll
	def 'enclosure #object read/write'() {

		expect: 'the output XML equals the given XML'
		persister.write(object) == xml

		and: 'the input object equals the given object'
		persister.read(Guid, xml) == object

		where:
		isPermaLinkRaw | text       | xml
		'true'         | 'testText' | """<guid isPermaLink="$isPermaLinkRaw">$text</guid>"""
		'false'        | 'testText' | """<guid isPermaLink="$isPermaLinkRaw">$text</guid>"""
		null           | 'testText' | """<guid>$text</guid>"""
		object = new Guid(isPermaLinkRaw, text)
	}
}
