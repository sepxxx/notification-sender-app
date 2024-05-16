package com.bnk.taskresolverservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="recipients")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Column
    String lastname;
    @Column
    String email;
    @Column
    String tg;
    @Column
    String token;
    @ManyToOne
    RecipientList recipientList;

    public Recipient(String lastname, String email, String tg, String token, RecipientList recipientList) {
        this.lastname = lastname;
        this.email = email;
        this.tg = tg;
        this.token = token;
        this.recipientList = recipientList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return Objects.equals(lastname, recipient.lastname)
                && Objects.equals(email, recipient.email)
                && Objects.equals(tg, recipient.tg)
                && Objects.equals(token, recipient.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastname, email, tg, token);
    }
}