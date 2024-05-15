package com.bnk.taskresolverservice.services;

import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListsInfoUpdateMessage;
import com.bnk.taskresolverservice.entities.Recipient;
import com.bnk.taskresolverservice.entities.RecipientList;
import com.bnk.taskresolverservice.repositories.RecipientListNameRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ListsUpdateServiceImpl {

    RecipientListNameRepository recipientListNameRepository;


//    public void processLUIMessage(ListsInfoUpdateMessage message) {
//        if (message.getEventType().equals(ListInfoUpdateEventType.CREATION)) {
//
//            getOrCreateAndAppendRecipientList(message.getNewListName());
//        }
//    }

    private void getOrCreateAndAppendRecipientList(List<Recipient> appendingRecipientList,
                                          String listName, String currentUserId) {
        RecipientList recipientListWithId =
                recipientListNameRepository.findByNameAndUserId(listName, currentUserId)
                        .orElseGet(() -> recipientListNameRepository.save(
                                new RecipientList(listName, currentUserId))
                        );
        recipientListWithId.appendRecipientList(appendingRecipientList);
    }
}
