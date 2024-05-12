package com.bnk.recipientsservice.entities;

import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "NAME")
    String name;
    //TODO:почитать о влиянии jointable на производительность
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "recipients_recipients_lists",
            joinColumns = @JoinColumn(name = "RECIPIENT_LIST_ID"),
            inverseJoinColumns = @JoinColumn(name = "RECIPIENT_ID"))
    Set<Recipient> recipientList;
    @Column(name = "USER_ID")
    String userId;

    public void appendRecipientList(List<Recipient> appendingRecipientList) {
        if (recipientList == null) {
            recipientList = new HashSet<>();
        }
        recipientList.addAll(appendingRecipientList);
    }

    public RecipientList(String name) {
        this.name = name;
    }

    public RecipientList(String name, String uid) {
        this.name = name;
        this.userId = uid;
    }
}