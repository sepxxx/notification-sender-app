package com.bnk.recipientsservice.services.impl;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientsservice.entities.Recipient;
import com.bnk.recipientsservice.entities.RecipientList;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecipientsSaverServiceImpl implements RecipientsSaverService {

    RecipientRepository recipientRepository;
    RecipientListNameRepository recipientListNameRepository;

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
        RecipientList recipientListWithId = recipientListNameRepository
                .findByNameAndUserId(recipientsListName, currentUserId)
                .orElseGet(() -> recipientListNameRepository
                        .save(new RecipientList(recipientsListName, currentUserId))
                );
        recipientListWithId.appendRecipientList(recipientsWithIds);

        return new RecipientListResponseDto(recipientListWithId.getId(),
                recipientsListName, recipientListWithId.getRecipientList().size());
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