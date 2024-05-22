package com.bnk.taskresolverservice.services;

import com.bnk.taskresolverservice.dtos.ListsInfoUpdateMessage;
import com.bnk.taskresolverservice.handlers.lUIMessageHandler;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ListsUpdateServiceImpl {

    //TODO: а мб тут рубильник какой-то сделать с проверкой существования списка?
    //мб ситуация пользователь создал список и тут же его удалил
    //а TRS будет пытаться затянуть его себе
    //КОРОЧЕ ГОВОРЯ ОБДУМАТЬ

    /* создание:
       создание списка
       опрос RS и дополнение
    *
    */
    //TODO: добавить маппер

    List<lUIMessageHandler> handlerList;
    @Transactional
    public void processLUIMessage(ListsInfoUpdateMessage message) {
        handlerList.stream()
                .filter(handler->handler.canHandle(message))
                .findFirst()
                .orElseThrow(()->new RuntimeException(String.format("ET: %s", message.getEventType())))
                .handle(message);
    }

}
