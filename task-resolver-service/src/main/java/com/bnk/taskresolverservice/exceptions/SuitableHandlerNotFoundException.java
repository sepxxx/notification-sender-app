package com.bnk.taskresolverservice.exceptions;

public class SuitableHandlerNotFoundException extends RuntimeException {
    public SuitableHandlerNotFoundException(String forWhat) {
        super(String.format("No suitable handler for %s", forWhat));
    }
}
