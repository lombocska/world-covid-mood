package io.lombocska.twitterconsumer.consumer;

import io.lombocska.twitterconsumer.service.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwitterConsumer {

    private final TweetService tweetService;

    //FIXME: in case of failure, offset shouldn't be committed (data loss)
    @KafkaListener(topics = "${twitter.consumer.kafka.topic}")
    public void process(final List<String> messages,
                        final @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        final @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("Start to consume batch messages from the offset : {}, partition: {} ", offsets.get(0), partitions.get(0));

        tweetService.storeInBulk(messages);

        log.info("End to consume batch messages at the offset : {}", offsets.get(offsets.size() - 1));
    }



}
