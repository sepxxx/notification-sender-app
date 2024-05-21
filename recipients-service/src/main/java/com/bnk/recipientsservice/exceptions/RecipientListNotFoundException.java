package com.bnk.recipientsservice.exceptions;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;


public class RecipientListNotFoundException extends ObjectNotFoundException {
    public RecipientListNotFoundException(String recipientListName, String currentUserId) {
        super(String.format("Recipient list not found listName: %s userId: %s", recipientListName, currentUserId));
    }
}
