package com.bnk.taskresolverservice.scheduled;

import com.bnk.taskresolverservice.dtos.NotificationMessage;
import com.bnk.taskresolverservice.repositories.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class NotificationResenderScheduler {

    @Value(value = "${spring.kafka.notifications.topic-name}")
    String NOTIFICATIONS_KAFKA_TOPIC_NAME;
    TaskRepository taskRepository;

    final KafkaTemplate<String, NotificationMessage> kafkaTemplate;
//    @Scheduled(fixedDelay = 3000)
//    public void scheduleFixedDelayTaskToResendLIUMessages() {
//        Optional.ofNullable(liuMessageRepository.findAll().get(0))
//                .ifPresent(
//                        el-> resendAndDeleteLIUMessage(el, LIU_MESSAGE_KAFKA_TOPIC_NAME)
//                );
//    }

//    public void resendAndDeleteMessage(NotificationMessage message, String topicName) {
//        CompletableFuture<SendResult<String, NotificationMessage>> future = kafkaTemplate.send(topicName, message);
//        future.whenComplete((result, ex) -> {
//            if (ex == null) {
//                System.out.println("ReSent message=[" + message +
//                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
//                liuMessageRepository.delete(message);
//            } else {
//                System.out.println("Unable to ReSend message=[" +
//                        message + "] due to : " + ex.getMessage());
//            }
//        });
//    }

}

