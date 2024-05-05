package com.bnk.recipientsservice.controllers;

import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientsservice.services.RecipientsSaverService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CsvController {
    RecipientsSaverService recipientsSaverService;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RecipientListResponseDto> uploadCSV(@RequestParam("file") MultipartFile file,
                                                              @RequestParam("recipientsListName") String recipientsListName,
                                                              @RequestParam("sub") String userId) {
        return new ResponseEntity<>(recipientsSaverService.saveRecipients(file, recipientsListName, userId), HttpStatus.CREATED);
    }
}
