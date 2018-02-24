package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.PersisterSpecification
import org.codehaus.groovy.runtime.StringBufferWriter
import spock.lang.Unroll

class CategoryTest extends PersisterSpecification {

	@Unroll
	def 'category of #object read/write'() {
		expect:

		def output = new StringBuffer()
		persister.write(new Category(domain, text), new StringBufferWriter(output))
		output.toString() == xml

		def input = persister.read(Category, xml)
		input.domain == domain
		input.text == text

		where:

		domain       | text       | xml
		'testDomain' | 'testText' | """<category domain="$domain">$text</category>"""
		null         | 'testText' | """<category>$text</category>"""
		object = new Category(domain, text)
	}
}
