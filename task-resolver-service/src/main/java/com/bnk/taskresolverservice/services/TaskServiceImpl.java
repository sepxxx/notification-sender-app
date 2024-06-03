package com.bnk.taskresolverservice.services;


import com.bnk.taskresolverservice.dtos.TaskRequestDto;
import com.bnk.taskresolverservice.dtos.TaskResponseDto;
import com.bnk.taskresolverservice.entities.RecipientList;
import com.bnk.taskresolverservice.entities.Task;
import com.bnk.taskresolverservice.exceptions.RecipientListNotFoundException;
import com.bnk.taskresolverservice.repositories.RecipientListRepository;
import com.bnk.taskresolverservice.repositories.TaskRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl {
    TaskRepository taskRepository;
    RecipientListRepository recipientListRepository;
    @Transactional
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto, String userId) {
        String listName = taskRequestDto.getListName();
        RecipientList recipientList = recipientListRepository.findByNameAndUserId(listName, userId)
                .orElseThrow(() -> new RecipientListNotFoundException(listName, userId));
        Task task = taskRepository.save(new Task(taskRequestDto.getText(), userId, recipientList));
        return new TaskResponseDto(task.getId(), task.getRecipientList().getName(), task.getText());
    }

    public List<TaskResponseDto> getTasksByUserId(String userId) {
        return taskRepository.findAllByUserId(userId)
                .stream()
                .map(t -> new TaskResponseDto(t.getId(), t.getRecipientList().getName(), t.getText()))
                .toList();
    }
}
