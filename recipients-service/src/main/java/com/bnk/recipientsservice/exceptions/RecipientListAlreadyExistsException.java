package com.bnk.recipientsservice.exceptions;

public class RecipientListAlreadyExistsException extends RuntimeException{
    public RecipientListAlreadyExistsException(String listName, String userId) {
        super(String.format("RecipientList listName: %s userId: %s already exists", listName, userId));
    }
}
