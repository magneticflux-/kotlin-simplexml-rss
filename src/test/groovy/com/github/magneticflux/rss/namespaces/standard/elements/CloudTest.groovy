package com.github.magneticflux.rss.namespaces.standard.elements

import com.github.magneticflux.rss.PersisterSpecification
import spock.lang.Unroll

class CloudTest extends PersisterSpecification {
	@Unroll
	def 'cloud #object read/write'() {

		expect: 'the output XML equals the given XML'
		persister.write(object) == xml

		and: 'the input object equals the given object'
		persister.read(Cloud, xml) == object

		where:
		domain       | path       | port | protocol       | registerProcedure       | xml
		'testDomain' | 'testPath' | 8080 | 'testProtocol' | 'testRegisterProcedure' | """<cloud domain="$domain" path="$path" port="$port" protocol="$protocol" registerProcedure="$registerProcedure"/>"""
		object = new Cloud(domain, path, port, protocol, registerProcedure)
	}
}
