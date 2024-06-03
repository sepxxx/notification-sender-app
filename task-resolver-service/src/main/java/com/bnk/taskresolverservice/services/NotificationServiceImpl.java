package com.bnk.taskresolverservice.services;

import com.bnk.taskresolverservice.dtos.NotificationMessage;
import com.bnk.taskresolverservice.repositories.NotificationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationServiceImpl {

    final NotificationRepository notificationRepository;
    final KafkaTemplate<String, NotificationMessage> kafkaTemplate;
    @Value(value = "${spring.kafka.notifications.topic-name}")
    String notificationsKafkaTopicName;
    //TODO: вынос?
    private void sendMessage(NotificationMessage message, String topicName) {
        CompletableFuture<SendResult<String, NotificationMessage>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) { //TODO: логирование
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
//                notificationRepository.save(message);

            }
        });
    }
}
