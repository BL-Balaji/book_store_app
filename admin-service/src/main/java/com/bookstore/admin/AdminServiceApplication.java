package com.bookstore.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Admin Service Application
 *
 * @author BL Balaji
 */
@SpringBootApplication(scanBasePackages = {"com.bookstore.admin", "com.bookstore.common"})
@EnableDiscoveryClient
public class AdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }
}
