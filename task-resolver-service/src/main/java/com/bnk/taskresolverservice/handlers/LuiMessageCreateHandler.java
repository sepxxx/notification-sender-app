package com.bnk.taskresolverservice.handlers;


import com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient.RecipientSaverServiceRestClient;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateMessage;
import com.bnk.taskresolverservice.dtos.RecipientDto;
import com.bnk.taskresolverservice.entities.Recipient;
import com.bnk.taskresolverservice.entities.RecipientList;
import com.bnk.taskresolverservice.mappers.RecipientRecipientDtoMapper;
import com.bnk.taskresolverservice.repositories.RecipientListRepository;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;


//TODO: а мб тут рубильник какой-то сделать с проверкой существования списка?
//мб ситуация пользователь создал список и тут же его удалил
//а TRS будет пытаться затянуть его себе
//КОРОЧЕ ГОВОРЯ ОБДУМАТЬ

/* создание:
   создание списка
   опрос RS и дополнение
*
*/

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LuiMessageCreateHandler implements LuiMessageHandler {

    RecipientListRepository recipientListRepository;
    RecipientSaverServiceRestClient recipientSaverServiceRestClient;
    RecipientRecipientDtoMapper recipientRecipientDtoMapper;

    static Integer REQUEST_PAGE_SIZE = 10; //TODO: вынос в properties
    @Override
    public Boolean canHandle(ListInfoUpdateMessage message) {
        return ListInfoUpdateEventType.CREATION.equals(message.getEventType());
    }

    @Override
    public void handle(ListInfoUpdateMessage message) {
        String listName = message.getListName();
        String userId = message.getUserId();
        System.out.println("CREATION CREATION CREATION CREATION CREATION");
        RecipientList recipientList = recipientListRepository.findByNameAndUserId(listName, userId)
                .orElseGet(()->new RecipientList(listName, userId));

        boolean lastPage = false;
        int pageNumber = 0;
        if (recipientList.getId() != null)
            pageNumber = recipientList.getRecipientList().size()/ REQUEST_PAGE_SIZE + 1;
        try {
            while(!lastPage) {

                Page<RecipientDto> recipientDtoPage = recipientSaverServiceRestClient
                        .getRecipientsPageByListNameAndUserId(
                                listName, userId, pageNumber++, REQUEST_PAGE_SIZE
                        );
                lastPage = recipientDtoPage.isLast();
                //TODO check if(recipientDtoPage.total > rl.size)
                recipientDtoPage
                        .stream()
                        .map(recipientRecipientDtoMapper::recipientDtoToRecipient)
                        .forEach(recipientList::addRecipient);
                recipientList = recipientListRepository.save(recipientList);
            }

        } catch (HttpClientErrorException ex) {
            log.error("HttpClientErrorException statusCode:{}, message:{}", ex.getStatusCode(), message);
            throw ex; //В принципе можем и не пробрасывать тк все равно ретраить не будем
        } catch (HttpServerErrorException ex) {
            log.error("HttpServerErrorException statusCode:{}, message:{}", ex.getStatusCode(), message);
            throw ex;
        } catch (ResourceAccessException ex) {
            log.error("ResourceAccessException , message:{}", message);
            throw ex;
        } catch (Exception ex) {
            log.error("Exception , message:{}", message);
        }
    }
}
