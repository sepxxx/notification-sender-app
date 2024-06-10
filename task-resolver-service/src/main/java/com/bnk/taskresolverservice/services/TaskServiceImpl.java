package com.bnk.taskresolverservice.services;


import com.bnk.taskresolverservice.dtos.*;
import com.bnk.taskresolverservice.entities.*;
import com.bnk.taskresolverservice.exceptions.EntityAccessDeniedException;
import com.bnk.taskresolverservice.exceptions.ObjectNotFoundException;
import com.bnk.taskresolverservice.exceptions.RecipientListNotFoundException;
import com.bnk.taskresolverservice.repositories.NotificationRepository;
import com.bnk.taskresolverservice.repositories.RecipientListRepository;
import com.bnk.taskresolverservice.repositories.TaskRepository;
import com.bnk.taskresolverservice.repositories.TaskTemplateRepository;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class TaskServiceImpl {
    final TaskRepository taskRepository;
    final TaskTemplateRepository taskTemplateRepository;
    final NotificationRepository notificationRepository;
    final RecipientListRepository recipientListRepository;
    final KafkaTemplate<String, NotificationMessage> kafkaTemplate;
    @Value(value = "${spring.kafka.notifications.topic-name}")
    String notificationsKafkaTopicName;
    final ExecutorService executorService;

    public TaskServiceImpl(TaskRepository taskRepository, RecipientListRepository recipientListRepository,
                           KafkaTemplate<String, NotificationMessage> kafkaTemplate,
                           NotificationRepository notificationRepository,
                           TaskTemplateRepository taskTemplateRepository) {
        this.taskRepository = taskRepository;
        this.recipientListRepository = recipientListRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.executorService = Executors.newFixedThreadPool(33);//TODO: вынос
        this.notificationRepository = notificationRepository;
        this.taskTemplateRepository = taskTemplateRepository;
    }

    @Transactional
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto, String userId) {
        return createTask(taskRequestDto.getText(), userId, taskRequestDto.getListName());
    }

    @Transactional
    public TaskResponseDto createTaskFromTemplate(TaskFromTemplateDto taskFromTemplateDto, String userId) {
        TaskTemplate taskTemplate = taskTemplateRepository.findById(taskFromTemplateDto.getTemplateId())
                .orElseThrow(()->new ObjectNotFoundException("Not found taskTemplate id: " + taskFromTemplateDto.getTemplateId()));
        return createTask(taskTemplate.getText(), userId, taskTemplate.getRecipientList().getName());
    }

    private TaskResponseDto createTask(String text, String userId, String listName) {
        RecipientList recipientList = recipientListRepository.findByNameAndUserId(listName, userId)
                .orElseThrow(() -> new RecipientListNotFoundException(listName, userId));
        Task task = new Task(text, userId, recipientList);
        recipientList.getRecipientList().stream()
                .map(recipient -> new Notification(text, recipient.getToken()))
                .forEach(task::addNotification);
        task = taskRepository.save(task);
        List<List<Notification>> notificationsSubLists = Lists.partition(new ArrayList<>(task.getNotifications()), 3);//TODO: вынос
        log.info("notificationsSubLists size: {}", notificationsSubLists.size());
        notificationsSubLists.forEach(list->{
            executorService.submit(()->{
                log.info("THREAD INFO {}", Thread.currentThread().getName());
//                try {
//                    Thread.sleep(30000L);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                list.forEach(notification -> sendMessage(notification, notificationsKafkaTopicName));
            });
        });
        return new TaskResponseDto(task.getId(), task.getRecipientList().getName(), task.getText(), task.getCreatedAt());
    }

    public List<TaskResponseDto> getTasksByUserId(String userId) {
        return taskRepository.findAllByUserId(userId)
                .stream()
                .map(t -> new TaskResponseDto(t.getId(), t.getRecipientList().getName(), t.getText(), t.getCreatedAt()))
                .toList();
    }
    @Transactional
    public TaskTemplateResponseDto createTaskTemplate(TaskRequestDto taskRequestDto, String userId) {
        RecipientList recipientList = recipientListRepository.findByNameAndUserId(taskRequestDto.getListName(), userId)
                .orElseThrow(() -> new RecipientListNotFoundException(taskRequestDto.getListName(), userId));
        TaskTemplate taskTemplate = taskTemplateRepository.save(new TaskTemplate(taskRequestDto.getText(), userId, TaskTemplateStatus.CREATED, recipientList));
        return new TaskTemplateResponseDto(taskTemplate.getId(), taskTemplate.getRecipientList().getName(), taskTemplate.getText(), taskTemplate.getTaskTemplateStatus());
    }

    public List<TaskTemplateResponseDto> getTaskTemplatesByUserId(String userId, TaskTemplateStatus taskTemplateStatus) {
        return taskTemplateRepository.findAllByUserIdAndTaskTemplateStatus(userId, taskTemplateStatus)
                .stream()
                .map(taskTemplate ->
                        new TaskTemplateResponseDto(taskTemplate.getId(), taskTemplate.getRecipientList().getName(),
                                taskTemplate.getText(), taskTemplate.getTaskTemplateStatus())
                )
                .toList();
    }

    @Transactional
    public TaskTemplateResponseDto shareTemplate(String userIdOwner, TaskTemplateSharingRequestDto taskTemplateSharingRequestDto) {
        TaskTemplate taskTemplate = taskTemplateRepository.findById(taskTemplateSharingRequestDto.getTemplateId())
                .orElseThrow(()-> new ObjectNotFoundException("Not found task template id: " + taskTemplateSharingRequestDto.getTemplateId()));
        if (Objects.equals(taskTemplate.getUserId(), userIdOwner)) {
            TaskTemplate taskTemplateShared = taskTemplateRepository.save(new TaskTemplate(taskTemplate.getText(), taskTemplateSharingRequestDto.getUserIdShareTo(),
                   TaskTemplateStatus.AWAITS_ACTION, taskTemplate.getRecipientList()));
            return new TaskTemplateResponseDto(taskTemplateShared.getId(), taskTemplateShared.getRecipientList().getName(),
                    taskTemplateShared.getText(), taskTemplateShared.getTaskTemplateStatus());
        } else {
            throw new EntityAccessDeniedException("TaskTemplate", taskTemplateSharingRequestDto.getTemplateId());
        }
    }

    @Transactional
    public TaskTemplateResponseDto setTemplateStatus(TaskTemplateStatusRequestDto taskTemplateDto, String userIdOwner) {
        log.info("setTemplateStatus {}", taskTemplateDto);
        TaskTemplate taskTemplate = taskTemplateRepository.findById(taskTemplateDto.getTemplateId())
                .orElseThrow(()-> new ObjectNotFoundException("Not found task template id: " + taskTemplateDto.getTemplateId()));
        if (Objects.equals(taskTemplate.getUserId(), userIdOwner)) {
            taskTemplate.setTaskTemplateStatus(taskTemplateDto.getTaskTemplateStatus());
            taskTemplate = taskTemplateRepository.save(taskTemplate);
            return new TaskTemplateResponseDto(taskTemplate.getId(), taskTemplate.getRecipientList().getName(),
                    taskTemplate.getText(), taskTemplate.getTaskTemplateStatus());
        } else {
            throw new EntityAccessDeniedException("TaskTemplate", taskTemplateDto.getTemplateId());
        }
    }


    //TODO: вынос?
    private void sendMessage(Notification notification, String topicName) {
        NotificationMessage message = new NotificationMessage(notification.getTask().getId(), notification.getText(), notification.getUserToken());
        CompletableFuture<SendResult<String, NotificationMessage>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) { //TODO: логирование
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
                notification.setProcessed(Boolean.TRUE);
                notificationRepository.save(notification);
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }


}
