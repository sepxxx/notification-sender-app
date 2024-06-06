package com.bnk.taskresolverservice;

import com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient.RecipientsServiceFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@EnableFeignClients
class TaskResolverServiceApplicationTests {

//    @Autowired
//    RecipientSaverServiceRestClient recipientSaverServiceRestClient;
//
//    @Autowired
//    TaskTemplateRepository taskTemplateRepository;
    @Autowired
    RecipientsServiceFeignClient recipientsServiceFeignClient;
    @Test
//    @Transactional
    void contextLoads() {
//        TaskTemplate taskTemplate = taskTemplateRepository.save(new TaskTemplate("testText", "testUser", null));
//        System.out.println(taskTemplate);
//        taskTemplate.getUserId().add("testUser2");
//        taskTemplateRepository.save(taskTemplate);
//        System.out.println(taskTemplate);

//        taskTemplateRepository.save(new TaskTemplate("testText", "testUser", null));
//        taskTemplateRepository.save(new TaskTemplate("testText", "testUser", null));
//        taskTemplateRepository.save(new TaskTemplate("testText", "testUser", null));
//        taskTemplateRepository.findAllByUserIdAnd("testUser")
//                .forEach(System.out::println);
//        TaskTemplate taskTemplate = new TaskTemplate(null, null, null, null);
        recipientsServiceFeignClient.getRecipientsPageByListNameAndUserId("testListName", "98724058-f909-4163-926b-3382fd8d270c", 0, 10);
    }
}
