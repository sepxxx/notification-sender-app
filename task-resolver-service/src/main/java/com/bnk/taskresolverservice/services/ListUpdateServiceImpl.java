package com.bnk.taskresolverservice.services;

import com.bnk.taskresolverservice.dtos.ListInfoUpdateMessage;
import com.bnk.taskresolverservice.exceptions.SuitableHandlerNotFoundException;
import com.bnk.taskresolverservice.handlers.LuiMessageHandler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ListUpdateServiceImpl {

    List<LuiMessageHandler> handlerList;

    public void processLuiMessage(ListInfoUpdateMessage message) {
        handlerList.stream()
                .filter(handler->handler.canHandle(message))
                .findFirst()
                .orElseThrow(()->new SuitableHandlerNotFoundException(String.format("event type: %s", message.getEventType())))
                .handle(message);
    }

}