package com.bnk.taskresolverservice;

import com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient.RecipientSaverServiceRestClient;
import com.bnk.taskresolverservice.dtos.RecipientDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
class TaskResolverServiceApplicationTests {

    @Autowired
    RecipientSaverServiceRestClient recipientSaverServiceRestClient;
    @Test
    //testListName1/recipients/
    void contextLoads() {
        Page<RecipientDto> prdto = recipientSaverServiceRestClient.getRecipientsPageByListNameAndUserId(
                "testListName1",
                "testUserId1",
                0,
                5
        );
        System.out.println(
                "prdto.get().toList().size() " +prdto.get().toList().size()
        );
        System.out.println(
                "prdto.getTotalPages() "+ prdto.getTotalPages()
        );
        System.out.println(
                "prdto.getTotalElements()" + prdto.getTotalElements()
        );
        System.out.println("prdto.isFirst() " + prdto.isFirst());
    }

}
