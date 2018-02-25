package com.github.magneticflux.rss

import spock.lang.Shared
import spock.lang.Specification

abstract class PersisterSpecification extends Specification {
	@Shared
	protected persister = PersistersKt.createRssPersister()
}
