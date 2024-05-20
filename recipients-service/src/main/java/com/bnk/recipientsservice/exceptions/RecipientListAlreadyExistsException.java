package com.bnk.recipientsservice.exceptions;

public class RecipientListAlreadyExistsException extends RuntimeException{
    public RecipientListAlreadyExistsException(String message) {
        super(message);
    }
}
