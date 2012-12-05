package com.dealer.metrics.spring.graphite.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Name space handler implementation for the http://www.yammer.com/schema/metrics/graphite and
 * http://www.dealer.com/schema/metrics/graphite name spaces.
 *
 * @author Jonathan Pearlin
 * @since 2.1.5
 */
public class MetricsGraphiteNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("graphite-reporter", new GraphiteReporterBeanDefinitionParser());
	}
}
