package com.bnk.recipientsservice.controllers;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientsservice.entities.Recipient;
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

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecipientsController {
    RecipientsService recipientsService;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RecipientListResponseDto> uploadCSV(@RequestParam("file") MultipartFile file,
                                                              @RequestParam("recipientsListName") String recipientsListName,
                                                              @RequestParam("sub") String userId) {
        return new ResponseEntity<>(recipientsService.saveRecipients(file, recipientsListName, userId), HttpStatus.CREATED);
    }

    @GetMapping("/{listName}/recipients/")
//    (defaultValue = "10")
    public ResponseEntity<Page<RecipientDto>> getRecipientPageByListName(@PathVariable String listName,
                                                         @RequestParam("sub") String userId,
                                                         @RequestParam Integer pageNumber,
                                                         @RequestParam Integer pageSize) {
        return  new ResponseEntity<>(
                recipientsService
                .getRecipientsPageByListNameAndUserId(listName, userId,
                        PageRequest.of(pageNumber, pageSize)),
                HttpStatus.OK
        );
    }
}
