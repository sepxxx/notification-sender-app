package com.bnk.recipientsservice.controllers;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.dtos.requests.RecipientListRequestDto;
import com.bnk.recipientsservice.dtos.requests.RecipientListUnionRequestDto;
import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientsservice.entities.ListInfoUpdateEventType;
import com.bnk.recipientsservice.entities.Recipient;
import com.bnk.recipientsservice.parsers.CsvParser;
import com.bnk.recipientsservice.parsers.CsvParserImpl;
import com.bnk.recipientsservice.services.RecipientsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecipientsController {
    RecipientsService recipientsService;
    CsvParser csvParser;

    @PostMapping("/upload")
    public ResponseEntity<RecipientListResponseDto> saveRecipientList(@RequestParam MultipartFile file,
                                                              @RequestParam("sub") String userId,
                                                                      @RequestParam String listName) {
        log.info("uploadCSV: file: {} recipientsListName: {} currentUserId:{}",
                file.getOriginalFilename(), listName, userId);
        List<RecipientDto> recipientDtoList = csvParser.parseRecipients(file);
        return new ResponseEntity<>(recipientsService.saveRecipientList(recipientDtoList, listName, userId),
                HttpStatus.CREATED);
    }

    @PutMapping("/upload")
    public ResponseEntity<RecipientListResponseDto> extendRecipientList(@RequestParam MultipartFile file,
                                                                              @RequestParam("sub") String userId,
                                                                        @RequestParam String listName) {
        log.info("uploadCSV: file: {} recipientsListName: {} currentUserId:{}",
                file.getOriginalFilename(), listName, userId);
        List<RecipientDto> recipientDtoList = csvParser.parseRecipients(file);
        return new ResponseEntity<>(recipientsService.extendRecipientList(recipientDtoList, listName, userId),
                HttpStatus.OK);
    }

    @PutMapping("/union")
    public ResponseEntity<RecipientListResponseDto> unionRecipientLists(@RequestHeader("sub") String userId, @RequestBody RecipientListUnionRequestDto dto) {
        return new ResponseEntity<>(recipientsService.uniteRecipientLists(dto.getListName1(), dto.getListName2(), dto.getListNameNew(), userId),
                HttpStatus.OK);
    }

    @GetMapping("/{listName}/recipients/")
//    (defaultValue = "10")
    public ResponseEntity<Page<RecipientDto>> getRecipientPageByListName(@PathVariable String listName,
                                                         @RequestParam("sub") String userId,
                                                         @RequestParam Integer pageNumber,
                                                         @RequestParam Integer pageSize) {
        return new ResponseEntity<>(
                recipientsService
                .getRecipientsPageByListNameAndUserId(listName, userId,
                        PageRequest.of(pageNumber, pageSize)),
                HttpStatus.OK
        );
    }
}
