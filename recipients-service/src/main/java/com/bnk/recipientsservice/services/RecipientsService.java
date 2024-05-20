package com.bnk.recipientsservice.services;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientsservice.entities.Recipient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipientsService {
    RecipientListResponseDto saveRecipientList(List<RecipientDto> recipientDtosWithoutIds, String recipientsListName,
                                                               String currentUserId);
    public RecipientListResponseDto extendRecipientList(List<RecipientDto> recipientDtosWithoutIds, String recipientsListName,
                                                        String currentUserId);
    Page<RecipientDto> getRecipientsPageByListNameAndUserId(String listName, String userId, PageRequest pageRequest);
}
