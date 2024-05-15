package com.bnk.recipientsservice.services.impl;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientsservice.entities.ListInfoUpdateEventType;
import com.bnk.recipientsservice.entities.ListsInfoUpdateMessage;
import com.bnk.recipientsservice.entities.Recipient;
import com.bnk.recipientsservice.entities.RecipientList;
import com.bnk.recipientsservice.repositories.LIUMessageRepository;
import com.bnk.recipientsservice.repositories.RecipientListNameRepository;
import com.bnk.recipientsservice.repositories.RecipientRepository;
import com.bnk.recipientsservice.services.RecipientsSaverService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecipientsSaverServiceImpl implements RecipientsSaverService {

    RecipientRepository recipientRepository;
    RecipientListNameRepository recipientListNameRepository;
    KafkaTemplate<String, ListsInfoUpdateMessage> kafkaTemplate;
    LIUMessageRepository lIUMessageRepository;
//    static String LIU_TOPIC_NAME = "recipients-lists-updates";
    @Value(value = "${spring.kafka.liu-message.topic-name}")
    static String LIU_MESSAGE_KAFKA_TOPIC_NAME;

    @SneakyThrows
    @Transactional
    public RecipientListResponseDto saveRecipients(MultipartFile file, String recipientsListName,
                                                   String currentUserId) {
        log.info("uploadCSV: file: {} recipientsListName: {} currentUserId:{}",
                file.getOriginalFilename(), recipientsListName, currentUserId);

        List<RecipientDto> recipientDtosWithoutIds = parseCsv(file);

        List<Recipient> recipientsWithIds = recipientRepository
                .saveAll(
                        recipientDtosWithoutIds
                        .stream()
                        .map(recipientDto ->
                            new Recipient(
                                    recipientDto.getLastname(),
                                    recipientDto.getEmail(),
                                    recipientDto.getTg(),
                                    recipientDto.getToken())
                            ).collect(Collectors.toSet())
                );
        RecipientList recipientListWithId =
                recipientListNameRepository.findByNameAndUserId(recipientsListName, currentUserId)
                        .orElseGet(() -> recipientListNameRepository.save(
                                new RecipientList(recipientsListName, currentUserId))
                        );

        //TODO: сейчас реализуем только создание нового списка
        //нужен еще механизм как для объединения списков только для дополнения текущего
        //или можно убрать эту логику и порефачить текущий метод для использования в функционале объединения
        recipientListWithId.appendRecipientList(recipientsWithIds);
        ListsInfoUpdateMessage LIUMessage = new ListsInfoUpdateMessage()
                .setCreatedAt(LocalDateTime.now())
                .setEventType(ListInfoUpdateEventType.CREATION)
                .setNewListName(recipientsListName)
                .setUserId(currentUserId);
//        LIUMessage = lIUMessageRepository.save(LIUMessage);
        sendMessageAndSetStatus(LIUMessage, LIU_MESSAGE_KAFKA_TOPIC_NAME);
        return new RecipientListResponseDto(recipientListWithId.getId(),
                recipientsListName, recipientListWithId.getRecipientList().size());
    }

//    @Transactional
    public void sendMessageAndSetStatus(ListsInfoUpdateMessage message, String topicName) {
        CompletableFuture<SendResult<String, ListsInfoUpdateMessage>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
//                setLIUMessageStatus(message, Boolean.TRUE);
//                lIUMessageRepository.save(message.setPushedToKafka(Boolean.TRUE));

            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
//                setLIUMessageStatus(message, Boolean.FALSE);
                lIUMessageRepository.save(message);

            }
        });
    }
    /*
        вынуждены сначала проверить записал ли основной поток сообщение в бд
        иначе бывает что при сеттинге статуса пишем первые а основной метод дублирует строку
        но уже с новым id
    */
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