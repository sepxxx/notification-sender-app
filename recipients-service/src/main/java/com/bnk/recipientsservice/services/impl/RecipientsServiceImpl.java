package com.bnk.recipientsservice.services.impl;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientsservice.entities.ListInfoUpdateEventType;
import com.bnk.recipientsservice.entities.ListsInfoUpdateMessage;
import com.bnk.recipientsservice.entities.RecipientList;
import com.bnk.recipientsservice.exceptions.RecipientListAlreadyExistsException;
import com.bnk.recipientsservice.exceptions.RecipientListNotFoundException;
import com.bnk.recipientsservice.mappers.RecipientRecipientDtoMapper;
import com.bnk.recipientsservice.repositories.LIUMessageRepository;
import com.bnk.recipientsservice.repositories.RecipientListRepository;
import com.bnk.recipientsservice.repositories.RecipientRepository;
import com.bnk.recipientsservice.services.RecipientsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class RecipientsServiceImpl implements RecipientsService {

    final RecipientRepository recipientRepository;
    final RecipientListRepository recipientListRepository;
    final KafkaTemplate<String, ListsInfoUpdateMessage> kafkaTemplate;
    final LIUMessageRepository lIUMessageRepository;
    final RecipientRecipientDtoMapper recipientRecipientDtoMapper;
    @Value(value = "${spring.kafka.liu-message.topic-name}")
    String LIU_MESSAGE_KAFKA_TOPIC_NAME;

    //TODO: можно сделать оптимизацию:
    //не искать список, а как предлагает vladmihaelca искать прокси getReferenceByID
    //для этого с фронта нужно будет ходить не с названием списка а с его ID либо сделать composite
    //но тогда нужно будет проверять принадлежит ли список текущему юзеру
    @Transactional
    public RecipientListResponseDto saveRecipientList(List<RecipientDto> recipientDtosWithoutIds, String recipientsListName,
                                                   String currentUserId) {
        if (recipientListRepository.existsByNameAndUserId(recipientsListName, currentUserId))
            throw new RecipientListAlreadyExistsException(recipientsListName, currentUserId);
        RecipientList recipientList = new RecipientList(recipientsListName, currentUserId);
        recipientDtosWithoutIds.stream()
                .map(recipientRecipientDtoMapper::recipientDtoToRecipient)
                .forEach(recipientList::addRecipient);
        RecipientList recipientListWithId = recipientListRepository.save(recipientList);

        ListsInfoUpdateMessage LIUMessage = new ListsInfoUpdateMessage()
                .setCreatedAt(LocalDateTime.now())
                .setEventType(ListInfoUpdateEventType.CREATION)
                .setListName(recipientListWithId.getName())
                .setUserId(currentUserId);
        sendLUIMessage(LIUMessage, LIU_MESSAGE_KAFKA_TOPIC_NAME);

        return new RecipientListResponseDto(recipientListWithId.getId(),
                recipientsListName, recipientListWithId.getRecipientList().size());
    }
    @Transactional
    public RecipientListResponseDto extendRecipientList(List<RecipientDto> recipientDtosWithoutIds, String recipientsListName,
                                                                      String currentUserId) {
        RecipientList recipientListWithId = recipientListRepository.findByNameAndUserId(recipientsListName, currentUserId)
                        .orElseThrow(() -> new RecipientListNotFoundException(recipientsListName, currentUserId));

        recipientDtosWithoutIds.stream()
                .map(recipientRecipientDtoMapper::recipientDtoToRecipient)
                .forEach(recipientListWithId::addRecipient);

        ListsInfoUpdateMessage LIUMessage = new ListsInfoUpdateMessage()
                .setCreatedAt(LocalDateTime.now())
                .setEventType(ListInfoUpdateEventType.EXTENSION)
                .setListName(recipientsListName)
                .setUserId(currentUserId);
        sendLUIMessage(LIUMessage, LIU_MESSAGE_KAFKA_TOPIC_NAME);
        return new RecipientListResponseDto(recipientListWithId.getId(),
                recipientListWithId.getName(), recipientListWithId.getRecipientList().size());
    }

    @Transactional
    public RecipientListResponseDto uniteRecipientLists(String recipientsListName1, String recipientsListName2,
                                                        String recipientsListNameNew, String currentUserId) {
        RecipientList recipientListWithId1 = recipientListRepository.findByNameAndUserId(recipientsListName1, currentUserId)
                .orElseThrow(() -> new RecipientListNotFoundException(recipientsListName1, currentUserId));
        RecipientList recipientListWithId2 = recipientListRepository.findByNameAndUserId(recipientsListName2, currentUserId)
                .orElseThrow(() -> new RecipientListNotFoundException(recipientsListName2, currentUserId));

//        recipientListWithId1.getRecipientList().addAll(recipientListWithId2.getRecipientList());
        recipientListWithId2.getRecipientList().forEach(recipientListWithId1::addRecipient);
        recipientListWithId1.setName(recipientsListNameNew);
        recipientListRepository.save(recipientListWithId1);
        recipientListRepository.delete(recipientListWithId2);

        ListsInfoUpdateMessage LIUMessage = new ListsInfoUpdateMessage()
                .setCreatedAt(LocalDateTime.now())
                .setEventType(ListInfoUpdateEventType.UNION)
                .setListName1(recipientsListName1)
                .setListName2(recipientsListName2)
                .setListName(recipientListWithId1.getName())
                .setUserId(currentUserId);
        sendLUIMessage(LIUMessage, LIU_MESSAGE_KAFKA_TOPIC_NAME);

        return new RecipientListResponseDto(recipientListWithId1.getId(),
                recipientListWithId1.getName(), recipientListWithId1.getRecipientList().size());
    }

    @Transactional
    public RecipientListResponseDto deleteRecipientList(String recipientsListName, String currentUserId) {


        recipientListRepository.deleteByNameAndUserId(recipientsListName, currentUserId);
        //TODO: нужно продумать момент когда списка не существует
        //TODO: здесь нужно будет ходить к TRS и узнавать когда была создана последняя таска по этому списку
        //или же завершена она или нет
        //делать это нужно будет асинхронно по-хорошему +
        ListsInfoUpdateMessage LIUMessage = new ListsInfoUpdateMessage()
                .setCreatedAt(LocalDateTime.now())
                .setEventType(ListInfoUpdateEventType.DELETION)
                .setListName(recipientsListName)
                .setUserId(currentUserId);
        sendLUIMessage(LIUMessage, LIU_MESSAGE_KAFKA_TOPIC_NAME);

        return new RecipientListResponseDto(null, recipientsListName, 0);
    }


    public Page<RecipientDto> getRecipientsPageByListNameAndUserId(String listName, String userId, PageRequest pageRequest) {
        RecipientList recipientList = recipientListRepository.findByNameAndUserId(listName, userId)
                .orElseThrow(() -> new RecipientListNotFoundException(listName, userId));
        return recipientRepository
                .findAllByRecipientList(recipientList, pageRequest)
                .map(recipientRecipientDtoMapper::recipientToRecipientDto);
    }
    public List<RecipientListResponseDto> getAllUserLists(String currentUserId) {
        return recipientListRepository.findAllByUserId(currentUserId)
                .stream()
                .map(recipientList -> new RecipientListResponseDto(recipientList.getId(), recipientList.getName(), recipientList.getRecipientList().size()))
                .toList();
    }

    //TODO: вынос?
    private void sendLUIMessage(ListsInfoUpdateMessage message, String topicName) {
        CompletableFuture<SendResult<String, ListsInfoUpdateMessage>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) { //TODO: логирование
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
                lIUMessageRepository.save(message);

            }
        });
    }

}