package com.bnk.taskresolverservice;

import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListsInfoUpdateMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class TaskResolverServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskResolverServiceApplication.class, args);
    }

}
