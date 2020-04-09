package io.lombocska.twitterindexer.client;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import io.lombocska.twitterindexer.TwitterIndexerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwitterStatusFilterClient {

    private final TwitterIndexerProperties properties;

    private Client hosebirdClient;

    public BlockingQueue<String> msgQueue;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${twitter.indexer.capacity}")
    private Integer capacity;

    @Value("${twitter.indexer.terms}")
    public String termsComaSeparatedList;

    @PostConstruct
    private void connectTwitterApi() {

        this.msgQueue = new LinkedBlockingQueue<>(capacity);

        final Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        final StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        final List<String> terms = Lists.newArrayList(termsComaSeparatedList.split(","));
        hosebirdEndpoint.trackTerms(terms);

        final TwitterIndexerProperties.TwitterApi twitterApiProperties = properties.getTwitterApi();
        final Authentication hosebirdAuth = new OAuth1(
                twitterApiProperties.getConsumerKey(),
                twitterApiProperties.getConsumerSecret(),
                twitterApiProperties.getToken(),
                twitterApiProperties.getSecret());

        final ClientBuilder builder = new ClientBuilder()
                .name(this.appName)
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        this.hosebirdClient = builder.build();
    }

    public void connect() {
        // Attempts to establish a connection.
        hosebirdClient.connect();
    }

    public void close() {
        // Attempts to close a connection.
        hosebirdClient.stop();
    }

    public boolean isDone() {
        return hosebirdClient.isDone();
    }

//    public void poll() {
//        String msg = null;
//        while (!hosebirdClient.isDone()) {
//            try {
//                msg = msgQueue.poll(5, TimeUnit.SECONDS);
//            } catch (InterruptedException e) {
//                log.warn("Polling twitter API has stopped, because: {}", e.getMessage());
//                close();
//            }
//
//            if (Strings.isNotBlank(msg)) {
//                log.info(msg);
//            }
//        }
//
//    }

}
