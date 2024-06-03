package com.bnk.taskresolverservice.dtos;


import lombok.Value;

@Value
public class NotificationMessage {
    String text;
    String userToken;
}
