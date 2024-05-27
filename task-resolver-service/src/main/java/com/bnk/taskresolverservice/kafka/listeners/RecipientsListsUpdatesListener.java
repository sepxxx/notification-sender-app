package com.bnk.taskresolverservice.kafka.listeners;

import com.bnk.taskresolverservice.dtos.ListInfoUpdateMessage;
import com.bnk.taskresolverservice.services.ListUpdateServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//TODO: conditional on property можно вырубать consumer
public class RecipientsListsUpdatesListener {
    ListUpdateServiceImpl listsUpdateService;
    @KafkaListener(topics = "recipients-lists-updates",
            groupId = "foo", containerFactory = "LIUMessageKafkaListenerContainerFactory") //TODO: перенос конфигурации
    public void listenGroupFoo(ListInfoUpdateMessage message) {
        System.out.println("Received Message: " + message);
        listsUpdateService.processLuiMessage(message);
    }
}
