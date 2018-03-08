package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.PersisterSpecification
import spock.lang.Unroll

import static com.github.magneticflux.rss.UrlUtils.testUrl

class TextInputTest extends PersisterSpecification {
	@Unroll
	def 'enclosure #object read/write'() {

		expect: 'the output XML equals the given XML'
		persister.write(object) == xml

		and: 'the input object equals the given object'
		persister.read(TextInput, xml) == object

		where:
		title       | description       | name       | link      | xml
		'testTitle' | 'testDescription' | 'testName' | testUrl() | """<textInput>
                                                                     |   <title>$title</title>
                                                                     |   <description>$description</description>
                                                                     |   <name>$name</name>
                                                                     |   <link>$link</link>
                                                                     |</textInput>""".stripMargin()

		object = new TextInput(title, description, name, link)
	}
}
