/*
* Copyright 2012 Jonathan Pearlin and Dealer.com (http://develop.dealer.com)
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
