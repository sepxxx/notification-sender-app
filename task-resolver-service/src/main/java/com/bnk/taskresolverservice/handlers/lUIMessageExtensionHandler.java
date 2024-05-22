package com.bnk.taskresolverservice.handlers;


import com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient.RecipientsSaverServiceRestClient;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListsInfoUpdateMessage;
import com.bnk.taskresolverservice.repositories.RecipientListRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class lUIMessageExtensionHandler implements lUIMessageHandler{

    RecipientListRepository recipientListRepository;
    RecipientsSaverServiceRestClient recipientsSaverServiceRestClient;
    static Integer REQUEST_PAGE_SIZE = 10;
    @Override
    public Boolean canHandle(ListsInfoUpdateMessage message) {
        return ListInfoUpdateEventType.EXTENSION.equals(message.getEventType());
    }

    @Override
    public void handle(ListsInfoUpdateMessage message) {
        String listName = message.getListName();
        String userId = message.getUserId();
        System.out.println("EXTENSION EXTENSION EXTENSION EXTENSION EXTENSION");
    }
}
