package com.bookstore.user.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Kafka Producer Configuration
 * Only activated when Kafka is enabled (spring.kafka.bootstrap-servers is set)
 * Disabled by default for local testing without Kafka
 *
 * @author BL Balaji
 */
@Configuration
@ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
public class KafkaProducerConfig {
    // KafkaTemplate is auto-configured by Spring Boot when kafka dependency is present
    // and spring.kafka.bootstrap-servers is configured
}
