package com.bnk.taskresolverservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tasks")
@Setter
@Getter
@ToString
//@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column
    String text;

    @Column
    String userId;

    @Column
    LocalDateTime createdAt;

    @ManyToOne
    RecipientList recipientList;

    @OneToMany(mappedBy = "task")
    Set<Notification> notifications;

    public void addNotification(Notification notification) {
        notifications.add(notification);
        notification.setTask(this);
    }

    public Task(String text, String userId, RecipientList recipientList) {
        this.text = text;
        this.userId = userId;
        this.recipientList = recipientList;
        notifications = new HashSet<>();
        createdAt = LocalDateTime.now();
    }
}
