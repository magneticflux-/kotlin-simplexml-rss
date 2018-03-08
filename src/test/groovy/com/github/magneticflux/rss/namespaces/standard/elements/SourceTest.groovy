package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.PersisterSpecification
import spock.lang.Unroll

import static com.github.magneticflux.rss.UrlUtils.testUrl

class SourceTest extends PersisterSpecification {
	@Unroll
	def 'enclosure #object read/write'() {

		expect: 'the output XML equals the given XML'
		persister.write(object) == xml

		and: 'the input object equals the given object'
		persister.read(Source, xml) == object

		where:
		url       | text       | xml
		testUrl() | 'testText' | """<source url="$url">$text</source>"""
		testUrl() | null       | """<source url="$url"/>"""

		object = new Source(url, text)
	}
}
