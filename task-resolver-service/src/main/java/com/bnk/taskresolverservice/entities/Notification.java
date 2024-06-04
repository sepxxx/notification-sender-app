package com.bnk.taskresolverservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="notifications")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Column
    String text;
    @Column
    String userToken;
    @Column
    Boolean processed;
    @ManyToOne
    Task task;

    public Notification(String text, String userToken) {
        this.text = text;
        this.userToken = userToken;
        this.processed = Boolean.FALSE;
    }
}
