package io.lombocska.twitterindexer.dispatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lombocska.common.model.Tweet;
import io.lombocska.twitterindexer.metrics.MetricService;
import io.lombocska.twitterindexer.metrics.aop.LogExceptionMetric;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaDispatcher {

    @Value("${twitter.indexer.kafka.topic}")
    private String twitterTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MetricService metricService;
    private final ObjectMapper objectMapper;

    @LogExceptionMetric
    public void send(final Tweet tweet) {
        try {
            final String messageKey = tweet.getId().toString();
            final String serializedTweet = serialize(tweet);
            this.kafkaTemplate.send(twitterTopic, messageKey, serializedTweet);
            log.debug("Tweet has sent: {} to the topic: {}", serializedTweet, twitterTopic);
            metricService.countSentTwitterMessage();
        } catch (NullPointerException npe) {
            log.warn("Skipping tweet since missing tweet id. Tweet: {}", tweet);
            this.metricService.countException(npe.getClass().getSimpleName());
        }

    }

    private String serialize(final Tweet tweet) {
        try {
            return objectMapper.writeValueAsString(tweet);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error has occurred while serializing message.");
        }
    }

}
