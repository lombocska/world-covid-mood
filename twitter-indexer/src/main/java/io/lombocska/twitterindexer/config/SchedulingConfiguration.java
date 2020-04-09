package io.lombocska.twitterindexer.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

import static net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock.InterceptMode.PROXY_METHOD;

@Configuration
@EnableScheduling
@EnableSchedulerLock(interceptMode = PROXY_METHOD,
        defaultLockAtMostFor = "${twitter.indexer.shedlock.max-time-lock-should-kept}",
        defaultLockAtLeastFor = "${twitter.indexer.shedlock.min-time-lock-should-kept}")
public class SchedulingConfiguration {

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }

}
