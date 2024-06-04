package com.bnk.taskresolverservice.controllers;


import com.bnk.taskresolverservice.dtos.TaskRequestDto;
import com.bnk.taskresolverservice.dtos.TaskResponseDto;
import com.bnk.taskresolverservice.dtos.TaskTemplateResponseDto;
import com.bnk.taskresolverservice.dtos.TaskTemplateSharingRequestDto;
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

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class TasksController {
    TaskServiceImpl taskService;
    @PostMapping("/tasks")
    public ResponseEntity<TaskResponseDto> createTask(@RequestParam("sub") String userId,
                                                      @RequestBody TaskRequestDto taskRequestDto) {

        return new ResponseEntity<>(taskService.createTask(taskRequestDto, userId),
                HttpStatus.CREATED);
    }
    @GetMapping("/tasks")
    public List<TaskResponseDto> getTasksByUserId(@RequestParam("sub") String userId) {
        return taskService.getTasksByUserId(userId);
    }
    @PostMapping("/tasks/templates")
    public ResponseEntity<TaskTemplateResponseDto> createTaskTemplate(@RequestParam("sub") String userId,
                                                                      @RequestBody TaskRequestDto taskRequestDto) {

        return new ResponseEntity<>(taskService.createTaskTemplate(taskRequestDto, userId),
                HttpStatus.CREATED);
    }
    @GetMapping("/tasks/templates")
    public List<TaskTemplateResponseDto> getTaskTemplatesByUserId(@RequestParam("sub") String userId, @RequestParam TaskTemplateStatus taskTemplateStatus) {
        return taskService.getTaskTemplatesByUserId(userId, taskTemplateStatus);
    }

    @PutMapping("/tasks/templates/share")
    public ResponseEntity<TaskTemplateResponseDto> shareTaskTemplate(@RequestParam("sub") String userId,
                                                                     @RequestBody TaskTemplateSharingRequestDto taskTemplateSharingRequestDto) {
        return new ResponseEntity<>(taskService.shareTemplate(userId, taskTemplateSharingRequestDto),
                HttpStatus.CREATED);
    }
}
