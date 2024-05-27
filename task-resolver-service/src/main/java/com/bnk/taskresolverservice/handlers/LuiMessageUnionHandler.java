package com.bnk.taskresolverservice.handlers;


import com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient.RecipientSaverServiceRestClient;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateMessage;
import com.bnk.taskresolverservice.repositories.RecipientListRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LuiMessageUnionHandler implements LuiMessageHandler {

    RecipientListRepository recipientListRepository;
    RecipientSaverServiceRestClient recipientSaverServiceRestClient;
    static Integer REQUEST_PAGE_SIZE = 10;
    @Override
    public Boolean canHandle(ListInfoUpdateMessage message) {
        return ListInfoUpdateEventType.UNION.equals(message.getEventType());
    }

    @Override
    public void handle(ListInfoUpdateMessage message) {
        String listName = message.getListName();
        String userId = message.getUserId();
        System.out.println("UNION UNION UNION UNION UNION ");
    }
}
