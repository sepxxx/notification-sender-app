package com.bnk.taskresolverservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaskResolverServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskResolverServiceApplication.class, args);
    }

}
