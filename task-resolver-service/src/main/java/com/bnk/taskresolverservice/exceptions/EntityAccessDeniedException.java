package com.bnk.taskresolverservice.exceptions;

public class EntityAccessDeniedException extends RuntimeException {
    public EntityAccessDeniedException(String entityName, Long entityId) {
        super(String.format("Access denied to entity {} with id {}", entityName, entityId));
    }
}
