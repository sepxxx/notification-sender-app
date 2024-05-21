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
    @Column
    String name;
    //TODO:почитать о влиянии jointable на производительность
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "recipients_recipients_lists",
//            joinColumns = @JoinColumn(name = "recipient_list_id"),
//            inverseJoinColumns = @JoinColumn(name = "recipient_id"))
    @OneToMany(mappedBy = "recipientList", cascade = CascadeType.ALL)
    Set<Recipient> recipientList = new HashSet<>();
    @Column
    String userId;

    public void addRecipient(Recipient recipient) {
        recipientList.add(recipient);
        recipient.setRecipientList(this);
    }

    public void removeRecipient(Recipient recipient) {
        recipientList.remove(recipient);
        recipient.setRecipientList(null);
    }

    public RecipientList(String name) {
        this.name = name;
    }

    public RecipientList(String name, String uid) {
        this.name = name;
        this.userId = uid;
    }
}