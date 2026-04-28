package com.bookstore.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.bookstore.customer", "com.bookstore.common"})
@EnableDiscoveryClient
public class CustomerDetailsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerDetailsServiceApplication.class, args);
    }
}
