package com.bnk.notificationsenderservice.dtos;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class NotificationMessage {
    String text;
    String userToken;
}
