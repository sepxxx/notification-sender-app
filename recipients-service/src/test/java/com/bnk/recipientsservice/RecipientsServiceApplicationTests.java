package com.bnk.recipientsservice;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.entities.Recipient;

import com.bnk.recipientsservice.mappers.RecipientRecipientDtoMapper;
import com.bnk.recipientsservice.repositories.LIUMessageRepository;
import com.bnk.recipientsservice.repositories.RecipientListRepository;
import com.bnk.recipientsservice.repositories.RecipientRepository;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
class RecipientsServiceApplicationTests {

    @Autowired
    LIUMessageRepository liuMessageRepository;

    @Autowired
    RecipientRepository recipientRepository;

    @Autowired
    RecipientListRepository recipientListRepository;

    @Autowired
    RecipientRecipientDtoMapper recipientRecipientDtoMapper;
    @Test
    @Transactional
    @Rollback(value = false)
    void contextLoads() {
//        System.out.println(recipientListRepository.findByNameAndUserId("testListName1","testUserId1"));
        recipientListRepository.deleteByNameAndUserId("xxx", "1cd1147e-eb57-4c5d-8993-c62fc9b5de70");
    }

//    @Test
//    void contextLoads2() {
//        System.out.println(recipientListRepository.existsByNameAndUserId("testListName1","testUserId1"));
//    }

}
