package com.bnk.taskresolverservice;

import com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient.RecipientSaverServiceRestClient;
import com.bnk.taskresolverservice.entities.TaskTemplate;
import com.bnk.taskresolverservice.repositories.TaskTemplateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class TaskResolverServiceApplicationTests {

    @Autowired
    RecipientSaverServiceRestClient recipientSaverServiceRestClient;

    @Autowired
    TaskTemplateRepository taskTemplateRepository;

    @Test
    @Transactional
    void contextLoads() {
//        TaskTemplate taskTemplate = taskTemplateRepository.save(new TaskTemplate("testText", "testUser", null));
//        System.out.println(taskTemplate);
//        taskTemplate.getUserId().add("testUser2");
//        taskTemplateRepository.save(taskTemplate);
//        System.out.println(taskTemplate);

//        taskTemplateRepository.save(new TaskTemplate("testText", "testUser", null));
//        taskTemplateRepository.save(new TaskTemplate("testText", "testUser", null));
//        taskTemplateRepository.save(new TaskTemplate("testText", "testUser", null));
        taskTemplateRepository.findAllByUserIdAnd("testUser")
                .forEach(System.out::println);
        TaskTemplate taskTemplate = new TaskTemplate(null, null, null, null);
    }
}
