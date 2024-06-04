package com.bnk.notificationsenderservice.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class AlertzyResponse {
    String response;
    List<String> sentTo;
}
