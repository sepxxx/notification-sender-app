package com.bnk.taskresolverservice.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipientList that = (RecipientList) o;
        return Objects.equals(name, that.name) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userId);
    }
}