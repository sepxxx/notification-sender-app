package com.bnk.taskresolverservice.handlers;


import com.bnk.taskresolverservice.dtos.ListInfoUpdateMessage;

public interface LuiMessageHandler {
    Boolean canHandle(ListInfoUpdateMessage message);
    void handle(ListInfoUpdateMessage message);
}
