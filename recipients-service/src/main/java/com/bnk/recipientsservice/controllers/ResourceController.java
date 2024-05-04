package com.bnk.recipientsservice.controllers;

import com.bnk.recipientsservice.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @GetMapping(value = "/admin")
    public ResponseEntity<Message> helloAdmin(){
        return new ResponseEntity<>(new Message(true, "Hello from Admin"), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    //98724058-f909-4163-926b-3382fd8d270c
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Message> helloUser(){
        return new ResponseEntity<>(new Message(true, "Hello from User"), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

}