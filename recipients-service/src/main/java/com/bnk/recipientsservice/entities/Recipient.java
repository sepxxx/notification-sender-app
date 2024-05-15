package com.bnk.recipientsservice.entities;

import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

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
    @Column(name="LASTNAME")
    String lastname;
    @Column(name="EMAIL")
    String email;
    @Column(name="TG")
    String tg;
    @Column(name="TOKEN")
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