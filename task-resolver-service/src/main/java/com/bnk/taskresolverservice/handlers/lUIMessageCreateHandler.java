package com.bnk.taskresolverservice.handlers;


import com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient.RecipientsSaverServiceRestClient;
import com.bnk.taskresolverservice.dtos.ListInfoUpdateEventType;
import com.bnk.taskresolverservice.dtos.ListsInfoUpdateMessage;
import com.bnk.taskresolverservice.dtos.RecipientDto;
import com.bnk.taskresolverservice.entities.Recipient;
import com.bnk.taskresolverservice.entities.RecipientList;
import com.bnk.taskresolverservice.repositories.RecipientListRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;


//TODO: а мб тут рубильник какой-то сделать с проверкой существования списка?
//мб ситуация пользователь создал список и тут же его удалил
//а TRS будет пытаться затянуть его себе
//КОРОЧЕ ГОВОРЯ ОБДУМАТЬ

/* создание:
   создание списка
   опрос RS и дополнение
*
*/
//TODO: добавить маппер
@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class lUIMessageCreateHandler implements lUIMessageHandler{

    RecipientListRepository recipientListRepository;
    RecipientsSaverServiceRestClient recipientsSaverServiceRestClient;
    static Integer REQUEST_PAGE_SIZE = 10;
    @Override
    public Boolean canHandle(ListsInfoUpdateMessage message) {
        return ListInfoUpdateEventType.CREATION.equals(message.getEventType());
    }

    @Override
    public void handle(ListsInfoUpdateMessage message) {
        String listName = message.getListName();
        String userId = message.getUserId();
        System.out.println("CREATION CREATION CREATION CREATION CREATION");
        RecipientList recipientListWithId = recipientListRepository.save(new RecipientList(listName, userId));

        boolean lastPage = false;
        int pageNumber = 0;
        while(!lastPage) {
            try {
                Page<RecipientDto> recipientDtoPage = recipientsSaverServiceRestClient
                        .getRecipientsPageByListNameAndUserId(
                                listName, userId, pageNumber++, REQUEST_PAGE_SIZE
                        );
                lastPage = recipientDtoPage.isLast();

                recipientDtoPage
                        .stream()
                        .map(dto -> new Recipient(
                                dto.getLastname(),
                                dto.getEmail(),
                                dto.getTg(),
                                dto.getToken(),
                                recipientListWithId))
                        .forEach(recipientListWithId::addRecipient);
            } catch (RestClientResponseException ex) {
                //TODO: было бы неплохо логировать откидываемые сообщения
                break;
            }
        }
    }
}
