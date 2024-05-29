package com.bnk.taskresolverservice.handlers;


import com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient.RecipientSaverServiceRestClient;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateMessage;
import com.bnk.taskresolverservice.dtos.RecipientDto;
import com.bnk.taskresolverservice.entities.Recipient;
import com.bnk.taskresolverservice.entities.RecipientList;
import com.bnk.taskresolverservice.repositories.RecipientListRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;


//TODO: а мб тут рубильник какой-то сделать с проверкой существования списка?
//мб ситуация пользователь создал список и тут же его удалил
//а TRS будет пытаться затянуть его себе
//КОРОЧЕ ГОВОРЯ ОБДУМАТЬ

/* создание:
   создание списка
   опрос RS и дополнение
*
*/

@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class LuiMessageCreateHandler implements LuiMessageHandler {

    RecipientListRepository recipientListRepository;
    RecipientSaverServiceRestClient recipientSaverServiceRestClient;
    static Integer REQUEST_PAGE_SIZE = 10; //TODO: вынос в properties
    @Override
    public Boolean canHandle(ListInfoUpdateMessage message) {
        return ListInfoUpdateEventType.CREATION.equals(message.getEventType());
    }

    @Override
    //TODO: pool interval - время за которое должен обработать
    public void handle(ListInfoUpdateMessage message) {
        String listName = message.getListName();
        String userId = message.getUserId();
        System.out.println("CREATION CREATION CREATION CREATION CREATION");
        RecipientList recipientListWithoutId = new RecipientList(listName, userId);

        boolean lastPage = false;
        int pageNumber = 0;
        try {
            while(!lastPage) {

                    Page<RecipientDto> recipientDtoPage = recipientSaverServiceRestClient
                            .getRecipientsPageByListNameAndUserId(
                                    listName, userId, pageNumber++, REQUEST_PAGE_SIZE
                            );
                    lastPage = recipientDtoPage.isLast();
                    System.out.println("PAGENUMBER "+pageNumber);
//                    recipientDtoPage
//                            .stream()
//                            .map(dto -> new Recipient(
//                                    dto.getLastname(),
//                                    dto.getEmail(),
//                                    dto.getTg(),
//                                    dto.getToken(),
//                                    recipientListWithoutId))//TODO: добавить маппер
//                            .forEach(recipientListWithoutId::addRecipient);

            }
//            recipientListRepository.save(recipientListWithoutId);
        } catch (HttpClientErrorException ex) {
            log.error("HttpClientErrorException statusCode:{}, message:{}", ex.getStatusCode(), message);
//            throw ex; //В принципе можем и не пробрасывать тк все равно ретраить не будем
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
