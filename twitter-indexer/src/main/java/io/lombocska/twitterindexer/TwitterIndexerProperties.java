package io.lombocska.twitterindexer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "twitter.indexer")
public class TwitterIndexerProperties {

    private TwitterApi twitterApi;

    @Data
    public static class TwitterApi {
        private String consumerKey;
        private String consumerSecret;
        private String token;
        private String secret;
        private String resourceUrl;
    }
}
