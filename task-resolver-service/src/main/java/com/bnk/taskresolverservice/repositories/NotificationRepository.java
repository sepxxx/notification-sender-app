package com.bnk.taskresolverservice.repositories;

import com.bnk.taskresolverservice.entities.Notification;
import com.bnk.taskresolverservice.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByTask(Task task);
}
