package com.bnk.taskresolverservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="task_templates")
@Setter
@Getter
@ToString
//@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class TaskTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column
    String text;

    @Column
    String userId;

    @Column
    @Enumerated(EnumType.STRING)
    TaskTemplateStatus taskTemplateStatus;

    @ManyToOne
    RecipientList recipientList;

    public TaskTemplate(String text, String userId, TaskTemplateStatus taskTemplateStatus, RecipientList recipientList) {
        this.text = text;
        this.userId = userId;
        this.taskTemplateStatus = taskTemplateStatus;
        this.recipientList = recipientList;
    }
}
