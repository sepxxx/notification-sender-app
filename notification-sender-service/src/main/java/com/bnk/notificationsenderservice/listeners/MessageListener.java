//package com.bnk.notificationsenderservice.listeners;
//
//
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
////TODO: conditional on property можно вырубать consumer
//public class MessageListener {
//
//    @KafkaListener(topics = "recipients-lists-updates",
//            groupId = "foo", containerFactory = "LIUMessageKafkaListenerContainerFactory") //TODO: перенос конфигурации
//    public void listenGroupFoo(ListInfoUpdateMessage message) {
//        System.out.println("Received Message: " + message);
//        listsUpdateService.processLuiMessage(message);
//    }
//}
