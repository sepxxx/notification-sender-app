package com.bnk.recipientsservice;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.entities.Recipient;

import com.bnk.recipientsservice.mappers.RecipientRecipientDtoMapper;
import com.bnk.recipientsservice.repositories.LIUMessageRepository;
import com.bnk.recipientsservice.repositories.RecipientListRepository;
import com.bnk.recipientsservice.repositories.RecipientRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    void contextLoads() {

        System.out.println(recipientListRepository.findByNameAndUserId("testListName1","testUserId1"));
    }

    @Test
    void contextLoads2() {
        System.out.println(recipientListRepository.existsByNameAndUserId("testListName1","testUserId1"));
    }

}
