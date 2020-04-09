package io.lombocska.twitterindexer.metrics.aop;

import io.lombocska.twitterindexer.metrics.MetricService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@RequiredArgsConstructor
public class MetricAspect {

    private final MetricService metricService;

    @AfterThrowing(value = "@annotation(io.lombocska.twitterindexer.metrics.aop.LogExceptionMetric)", throwing = "ex")
    public void exceptionTypeDistribution(final Exception ex) {
        this.metricService.countException(ex.getClass().getSimpleName());
    }

}
