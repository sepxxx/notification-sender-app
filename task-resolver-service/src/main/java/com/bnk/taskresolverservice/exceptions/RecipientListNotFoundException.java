package com.bnk.taskresolverservice.exceptions;




public class RecipientListNotFoundException extends ObjectNotFoundException {
    public RecipientListNotFoundException(String recipientListName, String currentUserId) {
        super(String.format("Recipient list not found listName: %s userId: %s", recipientListName, currentUserId));
    }
}
