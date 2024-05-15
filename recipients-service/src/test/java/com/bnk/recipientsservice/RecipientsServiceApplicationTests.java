package com.bnk.recipientsservice;

import com.bnk.recipientsservice.entities.RecipientList;
import com.bnk.recipientsservice.repositories.LIUMessageRepository;
import com.bnk.recipientsservice.repositories.RecipientListNameRepository;
import com.bnk.recipientsservice.repositories.RecipientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class RecipientsServiceApplicationTests {

    @Autowired
    LIUMessageRepository liuMessageRepository;

    @Autowired
    RecipientRepository recipientRepository;

    @Autowired
    RecipientListNameRepository recipientListNameRepository;
    @Test
    void contextLoads() {
//        System.out.println(liuMessageRepository.findById(352L));
        RecipientList recipientList = recipientListNameRepository
                .findByNameAndUserId("testListName1",
                        "testUserId1"
                        ).get();
        System.out.println(recipientRepository.findAllByRecipientList(
                recipientList, PageRequest.of(0, 2)
        ));
    }

}
