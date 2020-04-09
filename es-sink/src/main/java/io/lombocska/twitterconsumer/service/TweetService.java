package io.lombocska.twitterconsumer.service;

import java.util.List;

public interface TweetService {

    void storeInBulk(final List<String> tweets);

}
