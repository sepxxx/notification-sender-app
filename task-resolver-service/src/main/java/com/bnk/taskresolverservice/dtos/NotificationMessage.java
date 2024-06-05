package com.bnk.taskresolverservice.dtos;


import lombok.Value;

@Value
public class NotificationMessage {
    Long taskId;
    String text;
    String userToken;
}
