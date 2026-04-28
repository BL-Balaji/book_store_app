package com.bookstore.notification.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Notification Consumer
 * Processes events from Kafka topics (order-events, user-events).
 * Kafka is disabled for local testing — enable by adding spring-kafka dependency
 * and configuring spring.kafka.bootstrap-servers.
 *
 * @author BL Balaji
 */
@Component @Slf4j
public class NotificationConsumer {

    /**
     * Handles an order event payload.
     * Called programmatically or via Kafka listener in production.
     */
    public void handleOrderEvent(String message) {
        log.info("📧 [ORDER EVENT] Processing: {}", message);
        log.info("📧 Sending order confirmation notification...");
    }

    /**
     * Handles a user event payload.
     */
    public void handleUserEvent(String message) {
        log.info("📧 [USER EVENT] Processing: {}", message);
        log.info("📧 Sending welcome notification...");
    }
}
