package com.bnk.notificationsenderservice.RecipientsSaverServiceRestClient;

import com.bnk.notificationsenderservice.dtos.AlertzyResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AlertzyRestClient {
    final RestTemplate restTemplate;

//    @Value(value="${recipients-service.url}")
    String alertzyRootUrl = "https://alertzy.app/send"; //TODO: вынос + гибкость

    public AlertzyResponse sendNotification(String accountKey, String title, String message) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(alertzyRootUrl)
                .queryParam("accountKey", accountKey)
                .queryParam("title", title)
                .queryParam("message", message);

        ResponseEntity<AlertzyResponse> responseEntity =
                restTemplate.exchange(
                        uriComponentsBuilder.toUriString(),
                        HttpMethod.POST,
                        null,
                        AlertzyResponse.class
                        );
        return responseEntity.getBody();
    }

}
