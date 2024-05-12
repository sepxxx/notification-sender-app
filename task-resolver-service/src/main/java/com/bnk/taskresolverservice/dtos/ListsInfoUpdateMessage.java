package com.bnk.taskresolverservice.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ListsInfoUpdateMessage {
    Long id;
    ListInfoUpdateEventType eventType;
    LocalDateTime createdAt;
    Boolean pushedToKafka;
    String listName1;
    String listName2;
    String newListName;
}