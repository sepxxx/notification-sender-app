package com.bnk.recipientsservice.controllers;

import com.bnk.recipientsservice.Message;
import com.bnk.recipientsservice.dtos.requests.RecipientListRequestDto;
import com.bnk.recipientsservice.entities.ListInfoUpdateEventType;
import com.bnk.recipientsservice.entities.ListsInfoUpdateMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/rs")
public class ResourceController {

    @Value("${server.port}")
    private int port;
    @GetMapping(value = "/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public String helloAdmin(){
//        return new ResponseEntity<>(new Message(true, "Hello from Admin"), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    //98724058-f909-4163-926b-3382fd8d270c
        return "OKOKOK";
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Message> helloUser(@RequestHeader("sub") String uid){
        return new ResponseEntity<>(new Message(true, "uid: "+ uid + "FROM PORT: " + port), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @PostMapping(value = "/testEnum")
    public ResponseEntity<ListInfoUpdateEventType> helloUser(@RequestBody RecipientListRequestDto recipientListRequestDto){
        return new ResponseEntity<>(recipientListRequestDto.getEventType()
                , HttpStatus.OK);
    }

    @PostMapping(value = "/testEnum2")
    public ResponseEntity<ListInfoUpdateEventType> helloUser(@RequestParam ListInfoUpdateEventType eventType){
        return new ResponseEntity<>(eventType
                , HttpStatus.OK);
    }

}