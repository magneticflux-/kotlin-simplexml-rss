package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.PersisterSpecification
import spock.lang.Unroll

import static com.github.magneticflux.rss.UrlUtils.testUrl

class ImageTest extends PersisterSpecification {
	@Unroll
	def 'enclosure #object read/write'() {

		expect: 'the output XML equals the given XML'
		persister.write(object) == xml

		and: 'the input object equals the given object'
		persister.read(Image, xml) == object

		where:
		url       | title       | link      | description       | widthRaw | heightRaw | xml
		testUrl() | 'testTitle' | testUrl() | 'testDescription' | '100'    | '100'     | """<image>
                                                                                           |   <url>$url</url>
                                                                                           |   <title>$title</title>
                                                                                           |   <link>$link</link>
                                                                                           |   <description>$description</description>
                                                                                           |   <width>$widthRaw</width>
                                                                                           |   <height>$heightRaw</height>
                                                                                           |</image>""".stripMargin()

		testUrl() | 'testTitle' | testUrl() | null              | '100'    | '100'     | """<image>
                                                                                           |   <url>$url</url>
                                                                                           |   <title>$title</title>
                                                                                           |   <link>$link</link>
                                                                                           |   <width>$widthRaw</width>
                                                                                           |   <height>$heightRaw</height>
                                                                                           |</image>""".stripMargin()

		testUrl() | 'testTitle' | testUrl() | 'testDescription' | null     | '100'     | """<image>
                                                                                           |   <url>$url</url>
                                                                                           |   <title>$title</title>
                                                                                           |   <link>$link</link>
                                                                                           |   <description>$description</description>
                                                                                           |   <height>$heightRaw</height>
                                                                                           |</image>""".stripMargin()

		testUrl() | 'testTitle' | testUrl() | 'testDescription' | '100'    | null      | """<image>
                                                                                           |   <url>$url</url>
                                                                                           |   <title>$title</title>
                                                                                           |   <link>$link</link>
                                                                                           |   <description>$description</description>
                                                                                           |   <width>$widthRaw</width>
                                                                                           |</image>""".stripMargin()

		object = new Image(url, title, link, description, widthRaw, heightRaw)
	}
}
