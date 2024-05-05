package com.bnk.recipientsservice.entities;

import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import lombok.*;
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
    @Column(name="lastname")
    String lastname;
    @Column(name="email")
    String email;
    @Column(name="tg")
    String tg;
    @Column(name="token")
    String token;
    @ManyToOne
    RecipientList recipientListName;

    public Recipient(String lastname, String email, String tg, String token) {
        this.lastname = lastname;
        this.email = email;
        this.tg = tg;
        this.token = token;
    }
}