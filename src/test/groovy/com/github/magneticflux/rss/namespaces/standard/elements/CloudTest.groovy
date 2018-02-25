package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.PersisterSpecification
import org.codehaus.groovy.runtime.StringBufferWriter
import spock.lang.Unroll

class CloudTest extends PersisterSpecification {
	@Unroll
	def 'cloud #object read/write'() {

		when: 'the object is written to XML'
		def output = new StringBuffer()
		persister.write(object, new StringBufferWriter(output))
		and: 'the XML is read to an object'
		def input = persister.read(Cloud, xml)

		then: 'the output XML equals the given XML'
		output.toString() == xml
		and: 'the input object equals the given object'
		input == object

		where:

		domain       | path       | port | protocol       | registerProcedure       | xml
		'testDomain' | 'testPath' | 8080 | 'testProtocol' | 'testRegisterProcedure' | """<cloud domain="$domain" path="$path" port="$port" protocol="$protocol" registerProcedure="$registerProcedure"/>"""
		object = new Cloud(domain, path, port, protocol, registerProcedure)
	}
}
