package com.bookstore.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * User Service Application
 * Handles user authentication, registration, and profile management
 * 
 * @author BL Balaji
 */
@SpringBootApplication(scanBasePackages = {"com.bookstore.user", "com.bookstore.common"})
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
