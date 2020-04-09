package io.lombocska.twitterindexer.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MetricServiceImpl implements MetricService {

    private static final String COUNTER = "counter";
    private static final String SENT = "sent";
    private static final String TWITTER = "twitter";
    private static final String MESSAGE = "message";
    private static final String EXCEPTION = "exception";

    private final MeterRegistry meterRegistry;
    private final Counter exceptionCount;
    private final Counter sentTwitterMessageCount;

    public MetricServiceImpl(final MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.exceptionCount = this.meterRegistry.counter(buildMetricName(COUNTER, EXCEPTION));
        this.sentTwitterMessageCount = this.meterRegistry.counter(buildMetricName(COUNTER, SENT, TWITTER, MESSAGE));
    }

    @Override
    public void countException(final String exceptionType) {
        this.exceptionCount.increment();
        this.meterRegistry.counter(buildMetricName(COUNTER, TWITTER)).increment();
    }

    @Override
    public void countSentTwitterMessage() {
        this.sentTwitterMessageCount.increment();
    }


    private static String buildMetricName(final String... names) {
        return String.join(".", names);
    }

}
