package com.bnk.taskresolverservice.repositories;

import com.bnk.taskresolverservice.entities.TaskTemplate;
import com.bnk.taskresolverservice.entities.TaskTemplateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
    List<TaskTemplate> findAllByUserIdAndTaskTemplateStatus(String userId, TaskTemplateStatus taskTemplateStatus);
}
