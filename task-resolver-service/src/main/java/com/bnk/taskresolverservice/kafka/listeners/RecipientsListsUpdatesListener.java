package com.bnk.taskresolverservice.kafka.listeners;

import com.bnk.taskresolverservice.dtos.ListsInfoUpdateMessage;
import com.bnk.taskresolverservice.services.ListsUpdateServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RecipientsListsUpdatesListener {
    ListsUpdateServiceImpl listsUpdateService;
    @KafkaListener(topics = "recipients-lists-updates",
            groupId = "foo", containerFactory = "LIUMessageKafkaListenerContainerFactory")
    public void listenGroupFoo(ListsInfoUpdateMessage message) {
        System.out.println("Received Message: " + message);
        listsUpdateService.processLUIMessage(message);

    }
}
