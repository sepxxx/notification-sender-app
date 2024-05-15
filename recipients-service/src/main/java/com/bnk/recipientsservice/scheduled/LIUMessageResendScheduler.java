package com.bnk.recipientsservice.scheduled;

import com.bnk.recipientsservice.entities.ListsInfoUpdateMessage;
import com.bnk.recipientsservice.repositories.LIUMessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class LIUMessageResendScheduler {

    @Value(value = "${spring.kafka.liu-message.topic-name}")
    String LIU_MESSAGE_KAFKA_TOPIC_NAME;

    final LIUMessageRepository liuMessageRepository;

    final KafkaTemplate<String, ListsInfoUpdateMessage> kafkaTemplate;
    @Scheduled(fixedDelay = 3000)
    public void scheduleFixedDelayTaskToResendLIUMessages() {
        Optional.ofNullable(liuMessageRepository.findAll().get(0))
                .ifPresent(
                        el-> resendAndDeleteLIUMessage(el, LIU_MESSAGE_KAFKA_TOPIC_NAME)
                );
    }

    public void resendAndDeleteLIUMessage(ListsInfoUpdateMessage message, String topicName) {
        CompletableFuture<SendResult<String, ListsInfoUpdateMessage>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("ReSent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
                liuMessageRepository.delete(message);
            } else {
                System.out.println("Unable to ReSend message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }
}

