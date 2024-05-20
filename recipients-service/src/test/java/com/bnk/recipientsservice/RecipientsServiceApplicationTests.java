package com.bnk.recipientsservice;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.entities.Recipient;

import com.bnk.recipientsservice.mappers.RecipientRecipientDtoMapper;
import com.bnk.recipientsservice.repositories.LIUMessageRepository;
import com.bnk.recipientsservice.repositories.RecipientListRepository;
import com.bnk.recipientsservice.repositories.RecipientRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void contextLoads() {
//        recipientRepository.findById(402L).ifPresent(
//                r -> System.out.println(r)
//        );
//        Recipient r = recipientRecipientDtoMapper.recipientDtoToRecipient(
//                new RecipientDto("1", "2", "3", "4")
//        );
        RecipientDto r = recipientRecipientDtoMapper.recipientToRecipientDto(
                new Recipient(1L, "1", "2", "3", "4", null)
        );
        System.out.println(r);
    }

}
