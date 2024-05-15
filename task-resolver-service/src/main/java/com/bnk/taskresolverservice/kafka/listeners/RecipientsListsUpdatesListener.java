package com.bnk.taskresolverservice.kafka.listeners;

import com.bnk.taskresolverservice.dtos.ListsInfoUpdateMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RecipientsListsUpdatesListener {

    @KafkaListener(topics = "recipients-lists-updates",
            groupId = "foo", containerFactory = "LIUMessageKafkaListenerContainerFactory")
    public void listenGroupFoo(ListsInfoUpdateMessage message) {
        System.out.println("Received Message: " + message);

    }
}
