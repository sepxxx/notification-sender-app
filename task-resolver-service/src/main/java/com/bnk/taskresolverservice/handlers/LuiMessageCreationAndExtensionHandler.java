package com.bnk.taskresolverservice.handlers;


import com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient.RecipientSaverServiceRestClient;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateMessage;
import com.bnk.taskresolverservice.dtos.RecipientDto;
import com.bnk.taskresolverservice.entities.RecipientList;
import com.bnk.taskresolverservice.mappers.RecipientRecipientDtoMapper;
import com.bnk.taskresolverservice.repositories.RecipientListRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LuiMessageCreationAndExtensionHandler implements LuiMessageHandler {

    final RecipientListRepository recipientListRepository;
    final RecipientSaverServiceRestClient recipientSaverServiceRestClient;
    final RecipientRecipientDtoMapper recipientRecipientDtoMapper;
    @Value("${recipients-service.request_page_size}")
    static Integer REQUEST_PAGE_SIZE;
    @Override
    public Boolean canHandle(ListInfoUpdateMessage message) {
        return ListInfoUpdateEventType.CREATION.equals(message.getEventType()) || ListInfoUpdateEventType.EXTENSION.equals(message.getEventType());
    }

    @Override
    public void handle(ListInfoUpdateMessage message) {
        String listName = message.getListName();
        String userId = message.getUserId();
        log.info("!!! IN {} HANDLER !!!", message.getEventType());
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
