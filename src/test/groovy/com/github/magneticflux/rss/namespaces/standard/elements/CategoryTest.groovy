package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.PersisterSpecification
import spock.lang.Unroll

class CategoryTest extends PersisterSpecification {

	@Unroll
	def 'category  #object read/write'() {

		expect: 'the output XML equals the given XML'
		persister.write(object) == xml

		and: 'the input object equals the given object'
		persister.read(Category, xml) == object

		where:
		domain       | text       | xml
		'testDomain' | 'testText' | """<category domain="$domain">$text</category>"""
		null         | 'testText' | """<category>$text</category>"""
		object = new Category(domain, text)
	}
}
