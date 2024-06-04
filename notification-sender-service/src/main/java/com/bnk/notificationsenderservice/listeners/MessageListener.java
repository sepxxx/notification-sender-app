package com.bnk.notificationsenderservice.listeners;


import com.bnk.notificationsenderservice.RecipientsSaverServiceRestClient.AlertzyRestClient;
import com.bnk.notificationsenderservice.dtos.AlertzyResponse;
import com.bnk.notificationsenderservice.dtos.NotificationMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//TODO: conditional on property можно вырубать consumer
public class MessageListener {
    AlertzyRestClient alertzyRestClient;
    @KafkaListener(topics = "notifications",
            groupId = "foo", containerFactory = "NotificationMessageKafkaListenerContainerFactory") //TODO: перенос конфигурации
    public void listenGroupFoo(NotificationMessage message) {
        System.out.println("Received Message: " + message);
        AlertzyResponse alertzyResponse = alertzyRestClient.sendNotification(message.getUserToken(), "TITLE", message.getText());
    }
}
