package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.PersisterSpecification
import org.codehaus.groovy.runtime.StringBufferWriter
import spock.lang.Unroll

class CategoryTest extends PersisterSpecification {

	@Unroll
	def 'category  #object read/write'() {

		when: 'the object is written to XML'
		def output = new StringBuffer()
		persister.write(object, new StringBufferWriter(output))
		and: 'the XML is read to an object'
		def input = persister.read(Category, xml)

		then: 'the output XML equals the given XML'
		output.toString() == xml
		and: 'the input object equals the given object'
		input == object

		where:

		domain       | text       | xml
		'testDomain' | 'testText' | """<category domain="$domain">$text</category>"""
		null         | 'testText' | """<category>$text</category>"""
		object = new Category(domain, text)
	}
}
