package io.lombocska.twitterindexer.metrics;


public interface MetricService {


    void countException(String exceptionType);


    void countSentTwitterMessage();

}
