package io.lombocska.sentiment.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lombocska.common.model.Tweet;
import io.lombocska.sentiment.dispatcher.KafkaDispatcher;
import io.lombocska.common.model.SentimentResult;
import io.lombocska.common.model.TweetEnriched;
import io.lombocska.sentiment.service.SentimentAnalyzerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(value = "kafkaTransactionManager")
public class TwitterProcessor {

    private final KafkaDispatcher sentimentDispatcher;
    private final SentimentAnalyzerService analyzerService;
    private final ObjectMapper objectMapper;

    @Value("spring.application.name")
    private String appName;

    @KafkaListener(topics = "${twitter.consumer.kafka.topic}")
    public void process(final List<String> messages,
                        final @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        final @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("Start to consume batch messages from the offset : {}, partition: {} ", offsets.get(0), partitions.get(0));

        messages.forEach(message -> {
            final Tweet tweet = deserialize(message);
            final List<SentimentResult> nlpResult = analyzerService.analyze(tweet.getText());
            final TweetEnriched tweetEnriched = TweetEnriched.builder().sentimentResult(nlpResult).tweet(tweet).build();
            sentimentDispatcher.send(tweetEnriched);
        });

        log.info("End to consume batch messages at the offset : {}", offsets.get(offsets.size() - 1));
    }

    private Tweet deserialize(final String message) {
        Tweet tweet = null;
        try {
            tweet = objectMapper.readValue(message, Tweet.class);
        } catch (JsonProcessingException e) {
            log.error("Error has occurred during tweet message deserialization: {}", message);
        }
        return tweet;
    }


}
