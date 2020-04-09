//package io.lombocska.twitterindexer.config;
//
//import io.lombocska.twitterindexer.TwitterIndexerProperties;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.connect.ConnectionRepository;
//import org.springframework.social.connect.mem.InMemoryConnectionRepository;
//import org.springframework.social.connect.support.ConnectionFactoryRegistry;
//import org.springframework.social.twitter.api.Twitter;
//import org.springframework.social.twitter.api.impl.TwitterTemplate;
//import org.springframework.social.twitter.connect.TwitterConnectionFactory;
////import org.springframework.social.connect.ConnectionFactoryLocator;
////import org.springframework.social.connect.support.ConnectionFactoryRegistry;
////import org.springframework.social.twitter.api.Twitter;
////import org.springframework.social.twitter.api.impl.TwitterTemplate;
////import org.springframework.social.twitter.connect.TwitterConnectionFactory;
//
//@Configuration
//@RequiredArgsConstructor
//public class TwitterSocialConfiguration {
//
//    private final TwitterIndexerProperties properties;
//
//    @Bean
//    public ConnectionFactoryRegistry connectionFactoryLocator() {
//        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
//        registry.addConnectionFactory(new TwitterConnectionFactory(
//                properties.getApi().getConsumerKey(),
//                properties.getApi().getConsumerSecret()));
//        return registry;
//    }
//
//    @Bean
//    public ConnectionRepository connectionRepository() {
//        return new InMemoryConnectionRepository(connectionFactoryLocator());
//    }
//
//    @Bean
////    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
//    public Twitter twitter() {
//        Connection<Twitter> connection = connectionRepository().findPrimaryConnection(Twitter.class);
//        return connection != null ? connection.getApi() : new TwitterTemplate(
//                properties.getApi().getConsumerKey(),
//                properties.getApi().getConsumerSecret(),
//                properties.getApi().getToken(),
//                properties.getApi().getSecret());
//    }
//
////    @Bean
////    public Twitter twitter() {
////        return new TwitterTemplate(properties.getApi().getConsumerKey(),
////                properties.getApi().getConsumerSecret(),
////                properties.getApi().getToken(),
////                properties.getApi().getToken());
////    }
//
//}
