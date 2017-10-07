package com.ebberod.trader_inst;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.wavefront.integrations.metrics.WavefrontReporter;

import java.util.concurrent.TimeUnit;

public class MetricHelper {
    private static final MetricRegistry registry;

    static {
        registry = new MetricRegistry();

        WavefrontReporter reporter = WavefrontReporter.forRegistry(registry)
                .withSource("ebberod bank")
                .withPointTag("dc", "ebberod")
                .withPointTag("service", "query")
                .withJvmMetrics()
                .build("localhost", 2878);
        reporter.start(20, TimeUnit.SECONDS);
    }

    public static final MetricRegistry getRegistry() {
        return registry;
    }
}
