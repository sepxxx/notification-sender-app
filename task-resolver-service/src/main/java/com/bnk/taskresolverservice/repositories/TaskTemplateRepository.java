package com.bnk.taskresolverservice.repositories;

import com.bnk.taskresolverservice.entities.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
    List<TaskTemplate> findAllByUserId(String userId);
}
