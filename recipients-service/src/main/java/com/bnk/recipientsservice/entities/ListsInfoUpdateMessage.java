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
    @Column(name = "EVENT_TYPE")
    ListInfoUpdateEventType eventType;

    @Column(name = "CREATED_AT")
    LocalDateTime createdAt;

    @Column(name = "PUSHED_TO_KAFKA") //TODO: можно убрать из сериализации при отправке в кафку
    Boolean pushedToKafka;

    @Column(name = "LIST_NAME_1")
    String listName1;

    @Column(name = "LIST_NAME_2")
    String listName2;

    @Column(name = "NEW_LIST_NAME")
    String newListName;


}
