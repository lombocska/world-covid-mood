package io.lombocska.twitterindexer.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lombocska.common.model.Tweet;
import io.lombocska.twitterindexer.client.TwitterStatusFilterClient;
import io.lombocska.twitterindexer.dispatcher.KafkaDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional(value = "kafkaTransactionManager")
public class TwitterService {

    private TwitterStatusFilterClient twitterStatusFilterClient;
    private KafkaDispatcher kafkaDispatcher;
    private ObjectMapper objectMapper;

    public TwitterService(final TwitterStatusFilterClient twitterStatusFilterClient,
                          final KafkaDispatcher kafkaDispatcher,
                          final ObjectMapper objectMapper) {
        this.twitterStatusFilterClient = twitterStatusFilterClient;
        this.kafkaDispatcher = kafkaDispatcher;
        this.objectMapper = objectMapper;

        twitterStatusFilterClient.connect();
    }

    public void index() {

        String msg = null;
        while (!twitterStatusFilterClient.isDone()) {
            try {
                msg = this.twitterStatusFilterClient.msgQueue.poll(5, TimeUnit.SECONDS);
                final Tweet tweet = objectMapper.readValue(msg, Tweet.class);
                this.kafkaDispatcher.send(tweet);

            } catch (InterruptedException e) {
                log.warn("Polling twitter API has stopped, because: {}", e.getMessage());
                this.twitterStatusFilterClient.close();
            } catch (JsonMappingException e) {
                log.warn("Message could not have deseralized: {}", msg);
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            if (Strings.isNotBlank(msg)) {
                log.info(msg);
            }
        }
    }

}
