package com.bnk.taskresolverservice.handlers;


import com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient.RecipientSaverServiceRestClient;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateMessage;
import com.bnk.taskresolverservice.dtos.RecipientDto;
import com.bnk.taskresolverservice.entities.RecipientList;
import com.bnk.taskresolverservice.mappers.RecipientRecipientDtoMapper;
import com.bnk.taskresolverservice.repositories.RecipientListRepository;
import com.bnk.taskresolverservice.utils.Tuple;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@Component
//@AllArgsConstructor
//@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LuiMessageCreationAndExtensionHandler implements LuiMessageHandler {

    final RecipientListRepository recipientListRepository;
    final RecipientSaverServiceRestClient recipientSaverServiceRestClient;
    final RecipientRecipientDtoMapper recipientRecipientDtoMapper;
    final TransactionTemplate transactionTemplate;
    @Value("${recipients_service.request_page_size}")
    Integer REQUEST_PAGE_SIZE; //TODO: прочесть про static
    public LuiMessageCreationAndExtensionHandler(RecipientListRepository recipientListRepository,
                                                 RecipientSaverServiceRestClient recipientSaverServiceRestClient,
                                                 RecipientRecipientDtoMapper recipientRecipientDtoMapper,
                                                 PlatformTransactionManager transactionManager) {
        this.recipientListRepository = recipientListRepository;
        this.recipientSaverServiceRestClient = recipientSaverServiceRestClient;
        this.recipientRecipientDtoMapper = recipientRecipientDtoMapper;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }


    @Override
    public Boolean canHandle(ListInfoUpdateMessage message) {
        return ListInfoUpdateEventType.CREATION.equals(message.getEventType()) || ListInfoUpdateEventType.EXTENSION.equals(message.getEventType());
    }

    @Override
    public void handle(ListInfoUpdateMessage message) {
        String listName = message.getListName();
        String userId = message.getUserId();
        boolean lastPage = false;
        int pageNumber = 0;
        log.info("!!! IN {} HANDLER !!!", message.getEventType());

        transactionTemplate.setReadOnly(Boolean.TRUE);
        Tuple<RecipientList, Integer> recipientListAndSizeTuple = transactionTemplate.execute(status -> {
            RecipientList recipientListInner = recipientListRepository.findByNameAndUserId(listName, userId)
                    .orElseGet(()->new RecipientList(listName, userId));
            return new Tuple<>(recipientListInner, recipientListInner.getRecipientList().size());
         });
        RecipientList recipientList = recipientListAndSizeTuple.getFirst();
        Integer recipientListSize = recipientListAndSizeTuple.getSecond();
        if (recipientListSize != 0)
            pageNumber = recipientListSize / REQUEST_PAGE_SIZE;
        try {
            while (!lastPage) {
                Page<RecipientDto> recipientDtoPage = recipientSaverServiceRestClient
                        .getRecipientsPageByListNameAndUserId(
                                listName, userId, pageNumber++, REQUEST_PAGE_SIZE
                        );
                lastPage = recipientDtoPage.isLast();
//                if (pageNumber==5)
//                    Thread.sleep(4000L);
                if (recipientDtoPage.getTotalElements() > recipientListSize) {
                    recipientDtoPage
                            .stream()
                            .map(recipientRecipientDtoMapper::recipientDtoToRecipient)
                            .forEach(recipientList::addRecipient);
                    recipientList = recipientListRepository.save(recipientList);
                    recipientListSize += REQUEST_PAGE_SIZE;
                }
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
            log.error("Exception {}, message:{}", ex, message);
        }
    }
}
