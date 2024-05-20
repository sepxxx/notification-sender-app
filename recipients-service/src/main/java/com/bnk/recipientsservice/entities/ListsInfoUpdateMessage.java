package com.bnk.recipientsservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name="lists_info_updates")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ListsInfoUpdateMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    Long id;
    @Enumerated(EnumType.STRING)
    @Column
    ListInfoUpdateEventType eventType;

    @Column
    LocalDateTime createdAt;

    @Column
    String listName1;

    @Column
    String listName2;

    @Column
    String newListName;

    @Column
    String userId;
}
