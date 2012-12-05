package com.dealer.metrics.spring.graphite.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.ryantenney.metrics.spring.config.ReporterBeanDefinitionParser;
import com.yammer.metrics.reporting.GraphiteReporterFactory;

/**
 * Bean definition parser implementation for the {@code graphite-reporter} XML
 * element.  This parser will create a {@code com.yammer.metrics.reporting.GraphiteReporter}
 * instance using the attributes from the bean declaration.  This declaration should be in
 * the following format:
 * <code>
 * 		<metrics-graphite:graphite-reporter host="localhost" port="2003" prefix=""
 * </code>
 *
 * @author Jonathan Pearlin
 * @since 2.1.5
 */
public class GraphiteReporterBeanDefinitionParser extends ReporterBeanDefinitionParser {

	@Override
	protected String getBeanClassName(final Element element) {
		return GraphiteReporterFactory.class.getName();
	}

	@Override
	protected void doParse(final Element element, final BeanDefinitionBuilder builder) {
		builder.setFactoryMethod(FACTORY_METHOD_NAME);
		builder.addConstructorArgReference(element.getAttribute(METRICS_REGISTRY_ATTRIBUTE_NAME));
		builder.addConstructorArgValue(element.getAttribute(GraphiteReporterFactory.PREFIX_ATTRIBUTE_NAME));
		builder.addConstructorArgValue(element.getAttribute(GraphiteReporterFactory.HOST_ATTRIBUTE_NAME));
		builder.addConstructorArgValue(element.getAttribute(GraphiteReporterFactory.PORT_ATTRIBUTE_NAME));
		builder.addConstructorArgValue(element.getAttribute(GraphiteReporterFactory.CLOCK_ATTRIBUTE_NAME));
		builder.addConstructorArgValue(element.getAttribute(GraphiteReporterFactory.PERIOD_ATTRIBUTE_NAME));
		builder.addConstructorArgValue(element.getAttribute(GraphiteReporterFactory.TIME_UNIT_ATTRIBUTE_NAME));
	}
}