package com.bnk.recipientsservice.services;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientsservice.entities.Recipient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

public interface RecipientsService {
    RecipientListResponseDto saveRecipients(MultipartFile file, String recipientsListName,
                                                   String currentUserId);
    Page<RecipientDto> getRecipientsPageByListNameAndUserId(String listName, String userId, PageRequest pageRequest);
}
