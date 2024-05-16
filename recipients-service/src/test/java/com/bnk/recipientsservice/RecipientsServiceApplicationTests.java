package com.bnk.recipientsservice;

import com.bnk.recipientsservice.entities.RecipientList;
import com.bnk.recipientsservice.repositories.LIUMessageRepository;
import com.bnk.recipientsservice.repositories.RecipientNameRepository;
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
    RecipientNameRepository recipientNameRepository;
    @Test
    @Transactional
    void contextLoads() {
//        System.out.println(liuMessageRepository.findById(352L));
        RecipientList recipientList = recipientNameRepository
                .findByNameAndUserId("testListName4",
                        "testUserId4"
                        ).get();
//        System.out.println(recipientRepository.findAllByRecipientList(
//                recipientList, PageRequest.of(0, 2)
//        ));
        System.out.println(recipientList.getRecipientList().size());
    }

}
