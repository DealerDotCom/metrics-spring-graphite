#Spring Module for Yammer Metrics Graphite Reporter
====================================================

##About

The `metrics-spring-graphite` module integrates [Yammer Metrics Graphite Reporter](http://metrics.codahale.com/manual/graphite/) with Spring via a simple XML configuration.  For more information on Graphite, please visit the [Graphite Project](http://graphite.wikidot.com/).

###Maven

	<dependency>
		<groupId>com.dealer.metrics</groupId>
		<artifactId>metrics-spring-graphite</artifactId>
		<version>2.1.5</version>
	</dependency>

###Basic Usage

Spring Context XML:

	<beans xmlns="http://www.springframework.org/schema/beans"
		   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		   xmlns:metrics-graphite="http://www.ryantenney.com/schema/metrics/graphite"
		   xsi:schemaLocation="
				http://www.springframework.org/schema/beans
				http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
				http://www.dealer.com/schema/metrics/graphite
				http://www.dealer.com/schema/metrics/graphite/metrics-graphite-2.1.xsd">
	
		<metrics-graphite:graphite-reporter host="192.168.1.1" port="5555" />
	
		<!-- beans -->
	
	</beans>

###XML Config

The `host` attribute is the only required attribute for the tag.  In addtion, the `<metrics-graphite:graphite-reporter>` tag has the following optional arguments:


* `metrics-registry` - the id of the `MetricsRegsitry` bean with which the generated metrics should be registered. If omitted, this defaults to registry provided by `Metrics.defaultRegistry()`.
* `prefix` - the string to be pre-pended to the metric name associated with this reporter.
* `port` - sets the port used to connect to a Graphite server (defaults to 2003).
* `clock` - specifies how to measure the passage of time for the reporter (based on implementations of `com.yammer.metrics.core.Clock`).  Defaults to "DEFAULT".
* `period` - sets how often the reporter should send data to Graphite.  Defaults to 1.
* `time-unit` - sets the time unit for the period (based on the enumerated values contained in `java.util.concurrent.TimeUnit`).  Defaults to "MINUTES".

##License
=========

Copyright (c) 2012 Jonathan Pearlin, Dealer.com

Published under Apache Software License 2.0, see LICENSE
