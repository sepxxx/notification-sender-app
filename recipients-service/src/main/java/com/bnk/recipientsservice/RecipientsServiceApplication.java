package com.bnk.recipientsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@SpringBootApplication
@EnableDiscoveryClient
public class RecipientsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipientsServiceApplication.class, args);
    }

}
