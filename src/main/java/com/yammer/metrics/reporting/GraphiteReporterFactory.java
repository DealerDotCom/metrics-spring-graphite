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
package com.yammer.metrics.reporting;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.yammer.metrics.core.Clock;
import com.yammer.metrics.core.Clock.CpuTimeClock;
import com.yammer.metrics.core.Clock.UserTimeClock;
import com.yammer.metrics.core.MetricPredicate;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.reporting.GraphiteReporter.DefaultSocketProvider;

/**
 * Factory class used to create new {@code GraphiteReporter} instances.  This factory
 * class adheres to the conventions required to be used generically by the {@code ReporterBeanDefinitionParser}
 * class found in the metrics-spring module.
 *
 * @author Jonathan Pearlin
 * @since 2.1.5
 */
public class GraphiteReporterFactory {

	/**
	 * The name of the clock attribute provided by the bean definition.
	 */
	public static final String CLOCK_ATTRIBUTE_NAME = "clock";

	/**
	 * The name of the host attribute provided by the bean definition.
	 */
	public static final String HOST_ATTRIBUTE_NAME = "host";

	/**
	 * The name of the period attribute provided by the bean definition.
	 */
	public static final String PERIOD_ATTRIBUTE_NAME = "period";

	/**
	 * The name of the port attribute provided by the bean definition.
	 */
	public static final String PORT_ATTRIBUTE_NAME = "port";

	/**
	 * The name of the prefix attribute provided by the bean definition.
	 */
	public static final String PREFIX_ATTRIBUTE_NAME = "prefix";

	/**
	 * The name of the time unit attribute provided by the bean definition.
	 */
	public static final String TIME_UNIT_ATTRIBUTE_NAME = "time-unit";

	/**
	 * Constructs a new {@code GraphiteReporter} instance using the given parameters.
	 * @param metrics The metrics registry.
	 * @param prefix The string to be prepended to all names reported to Graphite.
	 * @param host The host of the Graphite server.
	 * @param port The port of the Graphite server.
	 * @param clock The name of a {@code Clock} instance.
	 * @param period The frequency at which data is sent to the Graphite server.
	 * @param timeUnit The unit of the period value as a string (must match the name of an enumerated value in {@code TimeUnit}).
	 * @return A new {@code GraphiteReporter} instance.
	 * @throws IOException If the {@code GraphiteReporter} instance cannot be created.
	 */
	public static GraphiteReporter createInstance(final MetricsRegistry metrics, final String prefix, final String host, final Integer port, final String clock, final Long period, final String timeUnit) throws IOException {
		final GraphiteReporter reporter = new GraphiteReporter(metrics,
				prefix,
				MetricPredicate.ALL,
				new DefaultSocketProvider(host, port),
				ClockValues.findClockValueByName(clock).getClock());
		reporter.start(period, TimeUnit.valueOf(timeUnit));
		return reporter;
	}

	/**
	 * Constructs a new {@code GraphiteReporter} instance using the given parameters.  This variant is exposed
	 * to meet the convention required by the {@code ReporterBeanDefinitionParser} in the metrics-spring module.
	 * @param metrics The metrics registry.
	 * @param properties The map of properties that will be used to construct the {@code GraphiteReporter} instance.
	 * @return A new {@code GraphiteReporter} instance.
	 * @throws IOException If the {@code GraphiteReporter} instance cannot be created.
	 */
	public static GraphiteReporter createInstance(final MetricsRegistry metrics, final Map<String, Object> properties) throws IOException {
		return createInstance(metrics,
				(String)properties.get(PREFIX_ATTRIBUTE_NAME),
				(String)properties.get(HOST_ATTRIBUTE_NAME),
				(Integer)properties.get(PORT_ATTRIBUTE_NAME),
				(String)properties.get(CLOCK_ATTRIBUTE_NAME),
				(Long)properties.get(PERIOD_ATTRIBUTE_NAME),
				(String)properties.get(TIME_UNIT_ATTRIBUTE_NAME));
	}
}

/**
 * Enumeration used to map string names from the Spring bean definition to
 * a {@code Clock} instance.
 *
 * @author Jonathan Pearlin
 * @since 2.1.5
 */
enum ClockValues {

	CPU(new CpuTimeClock()),
	DEFAULT(Clock.defaultClock()),
	USER(new UserTimeClock());

	/**
	 * The {@code Clock} instance.
	 */
	private Clock clock;

	/**
	 * Creates a new enumerated value for the provided {@code Clock} instance.
	 * @param clock A {@code Clock} instance.
	 */
	private ClockValues(final Clock clock) {
		this.clock = clock;
	}

	/**
	 * Returns the underlying {@code Clock} instance for this enumerated value.
	 * @return A {@code Clock} instance.
	 */
	public Clock getClock() {
		return clock;
	}

	/**
	 * Returns the enumerated value that matches the provided string name.
	 * @param name The name of a clock value.
	 * @return The matching enumerated value or the default value if no match is found.
	 */
	public static ClockValues findClockValueByName(final String name) {
		ClockValues clockValue = ClockValues.DEFAULT;

		for(final ClockValues currentValue : ClockValues.values()) {
			if(currentValue.name().equalsIgnoreCase(name)) {
				clockValue = currentValue;
				break;
			}
		}

		return clockValue;
	}
}
