package io.lombocska.sentiment.dispatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lombocska.common.model.TweetEnriched;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaDispatcher {

    @Value("${twitter.processor.kafka.topic}")
    private String twitterEnrichedDomainEventTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(final TweetEnriched tweetEnriched) {
        try {
            final String messageKey = tweetEnriched.getTweet().getId().toString(); //FIXME: ??
            final String serializedTweet = serialize(tweetEnriched);
            this.kafkaTemplate.send(twitterEnrichedDomainEventTopic, messageKey, serializedTweet);
            log.debug("Tweet has sent: {} to the topic: {}", serializedTweet, twitterEnrichedDomainEventTopic);
//            metricService.countSentTwitterMessage();
        } catch (NullPointerException npe) {
            log.warn("Skipping tweet since missing tweet id. Tweet: {}", tweetEnriched);
//            this.metricService.countException(npe.getClass().getSimpleName());
        }

    }

    private String serialize(final TweetEnriched tweetEnriched) {
        try {
            return objectMapper.writeValueAsString(tweetEnriched);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error has occurred while serializing message.");
        }
    }

}
