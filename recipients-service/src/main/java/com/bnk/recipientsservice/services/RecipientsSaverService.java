package com.bnk.recipientsservice.services;

import com.bnk.recipientsservice.dtos.responses.RecipientListResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface RecipientsSaverService {
    RecipientListResponseDto saveRecipients(MultipartFile file, String recipientsListName,
                                                   String currentUserId);
}
