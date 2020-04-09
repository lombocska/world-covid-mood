package io.lombocska.twitterindexer.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.concurrent.ExecutionException;

@Configuration
public class KafkaConfiguration {

    @Value("${twitter.indexer.kafka.topic}")
    private String twitterDomainTopic;

    /**
     * After defining a KafkaAdmin bean in the application context, it can automatically add topics to the broker.
     * @return NewTopic
     */
    @Bean
    public NewTopic twitterDomainTopic() {
        return TopicBuilder.name(twitterDomainTopic)
                .partitions(3)
                .replicas(2) //need 2 kafka brokers at least
                .compact()
                .build();
    }

    @Bean
    public AdminClient kafkaAdminClient(final KafkaAdmin admin) {
        return AdminClient.create(admin.getConfig());
    }

    @Bean
    public HealthIndicator kafkaHealthIndicator(final KafkaAdmin admin) {
        final DescribeClusterOptions describeClusterOptions = new DescribeClusterOptions().timeoutMs(1000);
        final AdminClient adminClient = kafkaAdminClient(admin);
        return () -> {
            final DescribeClusterResult describeCluster = adminClient.describeCluster(describeClusterOptions);
            try {
                final String clusterId = describeCluster.clusterId().get();
                final int nodeCount = describeCluster.nodes().get().size();
                return Health.up()
                        .withDetail("clusterId", clusterId)
                        .withDetail("nodeCount", nodeCount)
                        .build();
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                return Health.down()
                        .withException(e)
                        .build();
            }
        };
    }

}
