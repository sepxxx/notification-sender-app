package com.bnk.recipientsservice.dtos.requests;

import com.bnk.recipientsservice.entities.ListInfoUpdateEventType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipientListRequestDto {
    String listName;
    ListInfoUpdateEventType eventType;
}
