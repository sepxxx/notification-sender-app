package com.bnk.taskresolverservice.clients;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipientsSaverServiceRestClient {
    RestTemplate restTemplate;

    @Value(value="${recipients-service.url}")
    String recipientsServiceUrl;
    public RecipientsSaverServiceRestClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .rootUri(recipientsServiceUrl)
                .build();
    }
}
