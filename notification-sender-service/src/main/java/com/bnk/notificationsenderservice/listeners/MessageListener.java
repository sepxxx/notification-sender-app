package com.bnk.notificationsenderservice.listeners;


import com.bnk.notificationsenderservice.restClients.AlertzyRestClient;
import com.bnk.notificationsenderservice.dtos.NotificationMessage;
import com.bnk.notificationsenderservice.services.StatsServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//TODO: conditional on property можно вырубать consumer
public class MessageListener {
    AlertzyRestClient alertzyRestClient;
    StatsServiceImpl statsService;
    @KafkaListener(topics = "notifications",
            groupId = "foo", containerFactory = "NotificationMessageKafkaListenerContainerFactory") //TODO: перенос конфигурации
    public void listenGroupFoo(NotificationMessage message) {
        System.out.println("Received Message: " + message);
        statsService.incrementCurrentStatByTaskId(message.getTaskId());
//        AlertzyResponse alertzyResponse = alertzyRestClient.sendNotification(message.getUserToken(), "TITLE", message.getText());

    }
}
