package com.bnk.recipientsservice.controllers;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.dtos.requests.RecipientListUnionRequestDto;
import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientsservice.parsers.CsvParser;
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

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

//TODO: 1) разобраться с параметрами методов
//      2) оформить нормально апи
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
    public RecipientListResponseDto extendRecipientList(@RequestParam MultipartFile file,
                                                                              @RequestParam("sub") String userId,
                                                                        @RequestParam String listName) {
        log.info("uploadCSV: file: {} recipientsListName: {} currentUserId:{}",
                file.getOriginalFilename(), listName, userId);
        List<RecipientDto> recipientDtoList = csvParser.parseRecipients(file);
        return recipientsService.extendRecipientList(recipientDtoList, listName, userId);
    }

    @PutMapping("/union")
    public RecipientListResponseDto unionRecipientLists(@RequestHeader("sub") String userId, @RequestBody RecipientListUnionRequestDto dto) {
        return recipientsService.uniteRecipientLists(dto.getListName1(), dto.getListName2(), dto.getListNameNew(), userId);
    }

    @DeleteMapping("/{listName}")
    public RecipientListResponseDto deleteRecipientList(@RequestHeader("sub") String userId, @PathVariable String listName) {
        return recipientsService.deleteRecipientList(listName, userId);
    }

    @GetMapping("/{listName}/recipients/")
//    (defaultValue = "10")
    public Page<RecipientDto> getRecipientPageByListName(@PathVariable String listName,
                                                         @RequestParam("sub") String userId,
                                                         @RequestParam Integer pageNumber,
                                                         @RequestParam Integer pageSize) {
        return recipientsService
                .getRecipientsPageByListNameAndUserId(listName, userId,
                        PageRequest.of(pageNumber, pageSize));
    }
}
