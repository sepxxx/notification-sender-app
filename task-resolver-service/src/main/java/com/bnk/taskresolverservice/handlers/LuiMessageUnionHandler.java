package com.bnk.taskresolverservice.handlers;


import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateMessage;
import com.bnk.taskresolverservice.entities.RecipientList;
import com.bnk.taskresolverservice.exceptions.RecipientListNotFoundException;
import com.bnk.taskresolverservice.repositories.RecipientListRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LuiMessageUnionHandler implements LuiMessageHandler {

    RecipientListRepository recipientListRepository;
    @Override
    public Boolean canHandle(ListInfoUpdateMessage message) {
        return ListInfoUpdateEventType.UNION.equals(message.getEventType());
    }

    @Override
    @Transactional
    public void handle(ListInfoUpdateMessage message) {
        String recipientsListName1 = message.getListName1();
        String recipientsListName2 = message.getListName2();

        String currentUserId = message.getUserId();
        log.info("!!! IN {} HANDLER !!!", message.getEventType());

        RecipientList recipientListWithId1 = recipientListRepository.findByNameAndUserId(recipientsListName1, currentUserId)
                .orElseThrow(() -> new RecipientListNotFoundException(recipientsListName1, currentUserId));
        RecipientList recipientListWithId2 = recipientListRepository.findByNameAndUserId(recipientsListName2, currentUserId)
                .orElseThrow(() -> new RecipientListNotFoundException(recipientsListName2, currentUserId));

//        recipientListWithId1.getRecipientList().addAll(recipientListWithId2.getRecipientList());
        recipientListWithId2.getRecipientList().forEach(recipientListWithId1::addRecipient);
        recipientListWithId1.setName(message.getListName());
        recipientListRepository.save(recipientListWithId1);
        recipientListRepository.delete(recipientListWithId2);
    }
}
