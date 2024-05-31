package com.bnk.taskresolverservice.handlers;


import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateMessage;
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
public class LuiMessageDeletionHandler implements LuiMessageHandler {

    RecipientListRepository recipientListRepository;
    @Override
    public Boolean canHandle(ListInfoUpdateMessage message) {
        return ListInfoUpdateEventType.DELETION.equals(message.getEventType());
    }

    @Override
    @Transactional
    public void handle(ListInfoUpdateMessage message) {
        log.info("!!! IN {} HANDLER !!!", message.getEventType());
        recipientListRepository.deleteByNameAndUserId(message.getListName(), message.getUserId());
    }
}
