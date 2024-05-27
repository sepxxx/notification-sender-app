package com.bnk.taskresolverservice.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ListInfoUpdateMessage {
    Long id;
    ListInfoUpdateEventType eventType;
    LocalDateTime createdAt;
    String listName1;
    String listName2;
    String listName;
    String userId;
}