package io.lombocska.twitterindexer.scheduling;

import io.lombocska.twitterindexer.service.TwitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static io.lombocska.twitterindexer.scheduling.TwitterIndexerJob.INDEXER_SCHEDULER_PROPERTIES;

@Slf4j
@Component
@ConditionalOnProperty(value = INDEXER_SCHEDULER_PROPERTIES + ".enabled")
@RequiredArgsConstructor
public class TwitterIndexerJob {

    private final TwitterService twitterService;

    static final String INDEXER_SCHEDULER_PROPERTIES = "twitter.indexer.scheduling";

    @Scheduled(cron = "${" + INDEXER_SCHEDULER_PROPERTIES + ".cron}")
    @SchedulerLock(name = "twitterIndexer")
    public void twitterIndexer() {
        log.info("Twitter indexer job has been starting...");
        this.twitterService.index();
    }

}
