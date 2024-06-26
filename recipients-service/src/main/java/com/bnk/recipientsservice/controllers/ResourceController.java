package com.bnk.recipientsservice.controllers;

import com.bnk.recipientsservice.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @Value("${server.port}")
    private int port;
    @GetMapping(value = "/user")
    public ResponseEntity<Message> helloUser(@RequestHeader("sub") String uid){
        return new ResponseEntity<>(new Message(true, "uid: "+ uid + "FROM PORT: " + port), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @GetMapping(value = "/hello")
    public String helloUser() {
        return "hello";
    }


}