package io.lombocska.twitterindexer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TwitterIndexerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterIndexerApplication.class, args);
    }

}
