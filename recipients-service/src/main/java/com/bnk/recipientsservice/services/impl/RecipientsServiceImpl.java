package com.bnk.recipientsservice.services.impl;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientsservice.entities.ListInfoUpdateEventType;
import com.bnk.recipientsservice.entities.ListsInfoUpdateMessage;
import com.bnk.recipientsservice.entities.Recipient;
import com.bnk.recipientsservice.entities.RecipientList;
import com.bnk.recipientsservice.repositories.LIUMessageRepository;
import com.bnk.recipientsservice.repositories.RecipientNameRepository;
import com.bnk.recipientsservice.repositories.RecipientRepository;
import com.bnk.recipientsservice.services.RecipientsService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
    final RecipientNameRepository recipientNameRepository;
    final KafkaTemplate<String, ListsInfoUpdateMessage> kafkaTemplate;
    final LIUMessageRepository lIUMessageRepository;
//    static String LIU_TOPIC_NAME = "recipients-lists-updates";
    @Value(value = "${spring.kafka.liu-message.topic-name}")
    String LIU_MESSAGE_KAFKA_TOPIC_NAME;

    @SneakyThrows
    @Transactional
    public RecipientListResponseDto saveRecipients(MultipartFile file, String recipientsListName,
                                                   String currentUserId) {
        log.info("uploadCSV: file: {} recipientsListName: {} currentUserId:{}",
                file.getOriginalFilename(), recipientsListName, currentUserId);

        List<RecipientDto> recipientDtosWithoutIds = parseCsv(file);

        RecipientList recipientListWithId =
                recipientNameRepository.findByNameAndUserId(recipientsListName, currentUserId)
                        .orElseGet(() -> recipientNameRepository.save(
                                new RecipientList(recipientsListName, currentUserId))
                        );
        recipientListWithId.getRecipientList().addAll(
                recipientDtosWithoutIds
                        .stream()
                        .map(recipientDto ->
                                new Recipient(
                                        recipientDto.getLastname(),
                                        recipientDto.getEmail(),
                                        recipientDto.getTg(),
                                        recipientDto.getToken(),
                                        recipientListWithId
                                )

                        ).collect(Collectors.toSet())
        );
        ListsInfoUpdateMessage LIUMessage = new ListsInfoUpdateMessage()
                .setCreatedAt(LocalDateTime.now())
                .setEventType(ListInfoUpdateEventType.CREATION)
                .setNewListName(recipientsListName)
                .setUserId(currentUserId);
        sendLUIMessage(LIUMessage, LIU_MESSAGE_KAFKA_TOPIC_NAME);
        return new RecipientListResponseDto(recipientListWithId.getId(),
                recipientsListName, recipientListWithId.getRecipientList().size());
    }

    public Page<RecipientDto> getRecipientsPageByListNameAndUserId(String listName, String userId, PageRequest pageRequest) {
        RecipientList recipientList = recipientNameRepository.findByNameAndUserId(listName, userId)
                .orElseThrow(() -> new RuntimeException("Recipient list not found")); //TODO: разобраться c exceptions кастомными
        return recipientRepository
                .findAllByRecipientList(recipientList, pageRequest)
                .map(
                        recipient -> new RecipientDto(
                                recipient.getLastname(),
                                recipient.getTg(),
                                recipient.getEmail(),
                                recipient.getToken()
                        )
                );
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
    //TODO: сделать парсер универсальным, вынести в бин, не создавать кучу объектов
    //TODO: подумать насчет отказа от RecipientDto
    private List<RecipientDto> parseCsv(MultipartFile file) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(RecipientDto.class)
                .withColumnSeparator(',').withSkipFirstDataRow(true);
        Reader myReader = new InputStreamReader(file.getInputStream());

        MappingIterator<RecipientDto> iterator = csvMapper
                .readerFor(RecipientDto.class)
                .with(csvSchema)
                .readValues(myReader);
        return iterator.readAll();
    }
}