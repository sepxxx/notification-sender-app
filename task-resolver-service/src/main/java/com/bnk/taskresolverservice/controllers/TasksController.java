package com.bnk.taskresolverservice.controllers;


import com.bnk.taskresolverservice.dtos.*;
import com.bnk.taskresolverservice.entities.TaskTemplateStatus;
import com.bnk.taskresolverservice.services.TaskServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/tasks")
public class TasksController {
    TaskServiceImpl taskService;
    @PostMapping("")
    public ResponseEntity<TaskResponseDto> createTask(@RequestHeader("sub") String userId,
                                                      @RequestBody TaskRequestDto taskRequestDto) {

        return new ResponseEntity<>(taskService.createTask(taskRequestDto, userId),
                HttpStatus.CREATED);
    }

    @PostMapping("/prefilled")
    public ResponseEntity<TaskResponseDto> createTaskFromTemplate(@RequestHeader("sub") String userId, @RequestBody TaskFromTemplateDto taskFromTemplateDto) {
        return new ResponseEntity<>(taskService.createTaskFromTemplate(taskFromTemplateDto, userId),
                HttpStatus.CREATED);
    }
    @GetMapping("")
    public List<TaskResponseDto> getTasksByUserId(@RequestHeader("sub") String userId) {
        return taskService.getTasksByUserId(userId);
    }
    @PostMapping("/templates")
    public ResponseEntity<TaskTemplateResponseDto> createTaskTemplate(@RequestHeader("sub") String userId,
                                                                      @RequestBody TaskRequestDto taskRequestDto) {

        return new ResponseEntity<>(taskService.createTaskTemplate(taskRequestDto, userId),
                HttpStatus.CREATED);
    }
    @GetMapping("/templates")
    public List<TaskTemplateResponseDto> getTaskTemplatesByUserId(@RequestHeader("sub") String userId, @RequestParam TaskTemplateStatus taskTemplateStatus) {
        return taskService.getTaskTemplatesByUserId(userId, taskTemplateStatus);
    }

    @PostMapping("/templates/share")
    public TaskTemplateResponseDto shareTaskTemplate(@RequestHeader("sub") String userId,
                                                                     @RequestBody TaskTemplateSharingRequestDto taskTemplateSharingRequestDto) {
        return taskService.shareTemplate(userId, taskTemplateSharingRequestDto);
    }

    @PutMapping("/templates")
    public TaskTemplateResponseDto setTaskTemplateStatus(@RequestHeader("sub") String userId,
                                                                     @RequestBody TaskTemplateStatusRequestDto taskTemplateStatusRequestDto) {
        return taskService.setTemplateStatus(taskTemplateStatusRequestDto, userId);
    }
}
