package com.bnk.recipientsservice.dtos.requests;

import com.bnk.recipientsservice.entities.ListInfoUpdateEventType;
import lombok.Value;

@Value
public class RecipientListRequestDto {
    String listName;
    ListInfoUpdateEventType eventType;
}
