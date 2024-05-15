package com.bnk.taskresolverservice.services;

import com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListsInfoUpdateMessage;
import com.bnk.taskresolverservice.dtos.RecipientDto;
import com.bnk.taskresolverservice.entities.Recipient;
import com.bnk.taskresolverservice.entities.RecipientList;
import com.bnk.taskresolverservice.repositories.RecipientListNameRepository;
import com.bnk.taskresolverservice.repositories.RecipientRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ListsUpdateServiceImpl {

    RecipientListNameRepository recipientListNameRepository;
    RecipientRepository recipientRepository;
    RecipientsSaverServiceRestClient recipientsSaverServiceRestClient;
    static Integer REQUEST_PAGE_SIZE = 10;
    public void processLUIMessage(ListsInfoUpdateMessage message) {
        if (message.getEventType().equals(ListInfoUpdateEventType.CREATION)) {
            String listName = message.getNewListName();
            String userId = message.getUserId();
            RecipientList recipientListWithId =
                    recipientListNameRepository.findByNameAndUserId(listName, userId)
                            .orElseGet(() -> recipientListNameRepository.save(
                                    new RecipientList(listName, userId))
                            );
            boolean lastPage = false;
            int pageNumber = 0;
            while(!lastPage) {
                Page<RecipientDto> recipientDtoPage = recipientsSaverServiceRestClient
                        .getRecipientsPageByListNameAndUserId(
                                listName, userId, pageNumber++, REQUEST_PAGE_SIZE
                        );
                lastPage = recipientDtoPage.isLast();
                recipientRepository.saveAll(recipientDtoPage.stream().map(
                            dto-> new Recipient(
                                    dto.getLastname(),
                                    dto.getEmail(),
                                    dto.getTg(),
                                    dto.getToken(),
                                    recipientListWithId)
                        ).collect(Collectors.toList())
                );
            }
        }
    }

}
