package com.bnk.taskresolverservice.handlers;


import com.bnk.taskresolverservice.dtos.ListsInfoUpdateMessage;

public interface lUIMessageHandler {
    Boolean canHandle(ListsInfoUpdateMessage message);
    void handle(ListsInfoUpdateMessage message);
}
