package com.bnk.taskresolverservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "recipient_lists")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipientList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Column
    String name;
    @OneToMany(mappedBy = "recipientList", cascade = CascadeType.ALL)
    Set<Recipient> recipientList = new HashSet<>();
    @Column
    String userId;


    public RecipientList(String name) {
        this.name = name;
    }

    public RecipientList(String name, String uid) {
        this.name = name;
        this.userId = uid;
    }
}