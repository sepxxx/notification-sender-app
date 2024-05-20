package com.bnk.recipientsservice.services.impl;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientsservice.entities.ListInfoUpdateEventType;
import com.bnk.recipientsservice.entities.ListsInfoUpdateMessage;
import com.bnk.recipientsservice.entities.RecipientList;
import com.bnk.recipientsservice.exceptions.RecipientListAlreadyExistsException;
import com.bnk.recipientsservice.mappers.RecipientRecipientDtoMapper;
import com.bnk.recipientsservice.repositories.LIUMessageRepository;
import com.bnk.recipientsservice.repositories.RecipientListRepository;
import com.bnk.recipientsservice.repositories.RecipientRepository;
import com.bnk.recipientsservice.services.RecipientsService;
import jakarta.transaction.Transactional;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RecipientsServiceImpl implements RecipientsService {

    final RecipientRepository recipientRepository;
    final RecipientListRepository recipientListRepository;
    final KafkaTemplate<String, ListsInfoUpdateMessage> kafkaTemplate;
    final LIUMessageRepository lIUMessageRepository;
    final RecipientRecipientDtoMapper recipientRecipientDtoMapper;
    @Value(value = "${spring.kafka.liu-message.topic-name}")
    String LIU_MESSAGE_KAFKA_TOPIC_NAME;
    /* разные кейсы со списками
    * сохранение: файл + название + создатель
    * удаление: название + создатель для проверки
    * объединение: название1 + название2 + название новое + создатель
    * дополнение: файл + название + создатель
    *
    * сохранение:
    * создание списка
    * добавление получателей
    * отправка сообщения
    *
    * объединение:
    * вытягивание списков (включает проверку на принадлежность)
    * создание нового списка
    * добавление получателей
    * отправка сообщения
    *
    * дополнение:
    * вытягивание списка
    * добавление получателей
    * отправка сообщения
    *
    * обдумать кейс что если такого списка нет для дополнения
    *
    * обдумать как в 1 эндпоинте разделять сохранение и расширение
    * а нужно ли такое деление в целом RS?
    * как будто нужно = что если пытаемся создать список который уже есть - тут нужно кинуть exception
    * а для чего TRS знать о том что это расширение а не создание?
    * если доверяем RS то в принципе разницы не будет = тк уже проверили есть ли такой список на самом деле
    */
    //TODO: можно сделать оптимизацию:
    //не искать список, а как предлагает vladmihaelca искать прокси getReferenceByID
    //для этого с фронта нужно будет ходить не с названием списка а с его ID
    //но тогда нужно будет проверять принадлежит ли список текущему юзеру
    @Transactional
    public RecipientListResponseDto saveRecipientList(List<RecipientDto> recipientDtosWithoutIds, String recipientsListName,
                                                   String currentUserId) {
        recipientListRepository.findByNameAndUserId(recipientsListName, currentUserId) //TODO: переделать проверку
                        .ifPresent(v->{throw new RecipientListAlreadyExistsException("такой список уже существует");});
        RecipientList recipientListWithId = recipientListRepository.save(new RecipientList(recipientsListName, currentUserId));
        fillRecipientListAndSendLUIMessage(recipientListWithId, recipientDtosWithoutIds, ListInfoUpdateEventType.CREATION,
                recipientsListName, currentUserId);
        return new RecipientListResponseDto(recipientListWithId.getId(),
                recipientsListName, recipientListWithId.getRecipientList().size());
    }
    @Transactional
    public RecipientListResponseDto extendRecipientList(List<RecipientDto> recipientDtosWithoutIds, String recipientsListName,
                                                                      String currentUserId) {
        RecipientList recipientListWithId =
                recipientListRepository
                        .findByNameAndUserId(recipientsListName, currentUserId)
                        .orElseThrow();
        fillRecipientListAndSendLUIMessage(recipientListWithId, recipientDtosWithoutIds, ListInfoUpdateEventType.EXTENSION,
                recipientsListName, currentUserId);
        return new RecipientListResponseDto(recipientListWithId.getId(),
                recipientsListName, recipientListWithId.getRecipientList().size());
    }

    public Page<RecipientDto> getRecipientsPageByListNameAndUserId(String listName, String userId, PageRequest pageRequest) {
        RecipientList recipientList = recipientListRepository.findByNameAndUserId(listName, userId)
                .orElseThrow(() -> new NotFoundException("Recipient list not found"));
        //TODO: разобраться c exceptions кастомными
        return recipientRepository
                .findAllByRecipientList(recipientList, pageRequest)
                .map(recipientRecipientDtoMapper::recipientToRecipientDto);
    }

    private void fillRecipientListAndSendLUIMessage(RecipientList recipientListWithId, List<RecipientDto> recipientDtosWithoutIds,
                                                    ListInfoUpdateEventType eventType, String recipientListName, String currentUserId) {
        recipientListWithId.getRecipientList()
                .addAll(
                        recipientDtosWithoutIds
                                .stream()
                                .map(dto->recipientRecipientDtoMapper.recipientDtoToRecipient(dto, recipientListWithId))
                                .collect(Collectors.toSet())
                );

        ListsInfoUpdateMessage LIUMessage = new ListsInfoUpdateMessage()
                .setCreatedAt(LocalDateTime.now())
                .setEventType(eventType)
                .setNewListName(recipientListName)
                .setUserId(currentUserId);
        sendLUIMessage(LIUMessage, LIU_MESSAGE_KAFKA_TOPIC_NAME);
    }
    private void sendLUIMessage(ListsInfoUpdateMessage message, String topicName) {
        CompletableFuture<SendResult<String, ListsInfoUpdateMessage>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
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