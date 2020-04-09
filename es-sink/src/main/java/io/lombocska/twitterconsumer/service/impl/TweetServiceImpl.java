package io.lombocska.twitterconsumer.service.impl;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.lombocska.twitterconsumer.service.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private static final String TWITTER_INDEX = "twitter";
    private static final String TWITTER_INDEX_TYPE = "tweets";

    private final RestHighLevelClient esClient;

    @Override
    public void storeInBulk(final List<String> tweets) {
        BulkRequest request = new BulkRequest();

        tweets.forEach(tweet -> index(request, tweet));

        try {
            esClient.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Error has occurred during bulk storing operation.");
        }
    }

    private void index(final BulkRequest request, final String tweet) {
        final IndexRequest source = new IndexRequest(
                TWITTER_INDEX,
                TWITTER_INDEX_TYPE,
                extractTweetId(tweet) //ElasticSearch indexing process and kafka consumer will be idempotent via this id
        ).source(tweet, XContentType.JSON);

        request.add(source);
    }

    //twitter producer makes sure that tweets without ids don't get published into the topic
    private String extractTweetId(final String tweet) {
        final String jsonpathCreatorNamePath = "$.tweet.id";
        final DocumentContext jsonContext = JsonPath.parse(tweet);
        final Long tweetId = jsonContext.read(jsonpathCreatorNamePath);
        log.debug("Extracted tweet id for elasticsearch indexing: {}", tweetId);
        return tweetId.toString();
    }

}
