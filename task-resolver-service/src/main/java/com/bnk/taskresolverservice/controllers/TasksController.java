package com.bnk.taskresolverservice.controllers;


import com.bnk.taskresolverservice.dtos.TaskRequestDto;
import com.bnk.taskresolverservice.dtos.TaskResponseDto;
import com.bnk.taskresolverservice.services.TaskServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class TasksController {
    TaskServiceImpl taskService;
    @PostMapping("/upload")
    public ResponseEntity<TaskResponseDto> createTask(@RequestParam("sub") String userId,
                                                      @RequestBody TaskRequestDto taskRequestDto) {

        return new ResponseEntity<>(taskService.createTask(taskRequestDto, userId),
                HttpStatus.CREATED);
    }
//    @GetMapping("/{listName}/recipients/")
////    (defaultValue = "10")
//    public Page<RecipientDto> getRecipientPageByListName(@PathVariable String listName,
//                                                         @RequestParam("sub") String userId,
//                                                         @RequestParam Integer pageNumber,
//                                                         @RequestParam Integer pageSize) {
//        return recipientsService
//                .getRecipientsPageByListNameAndUserId(listName, userId,
//                        PageRequest.of(pageNumber, pageSize));
//    }
}
