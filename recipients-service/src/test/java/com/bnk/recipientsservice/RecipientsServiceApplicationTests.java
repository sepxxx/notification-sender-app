package com.bnk.recipientsservice;

import com.bnk.recipientsservice.repositories.LIUMessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecipientsServiceApplicationTests {

    @Autowired
    LIUMessageRepository liuMessageRepository;
    @Test
    void contextLoads() {
        System.out.println(liuMessageRepository.findById(352L));
    }

}
