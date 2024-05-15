package com.bnk.taskresolverservice.clients;

import com.bnk.taskresolverservice.dtos.RecipientDto;
import jdk.jfr.Registered;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class RecipientsSaverServiceRestClient {
    final RestTemplate restTemplate;

//    @Value(value="${recipients-service.url}")
    String recipientsServiceRootUrl = "http://localhost:8080";

    public Page<RecipientDto> getRecipientsPageByListNameAndUserId(String listName, String userId,
                                                                   Integer pageNumber, Integer pageSize) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(recipientsServiceRootUrl + String.format("/%s/recipients/", listName))
                .queryParam("sub", userId)
                .queryParam("pageNumber", pageNumber)
                .queryParam("pageSize", pageSize);
        ResponseEntity<CustomPageImpl<RecipientDto>> responseEntity =
                restTemplate.exchange(uriComponentsBuilder.toUriString(),
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<CustomPageImpl<RecipientDto>>() {
                        });
        return responseEntity.getBody();

//        return restTemplate.getForObject("http://localhost:8080/{listName}/recipients/?" +
//                        "pageNumber={pageNumber}&pageSize={pageSize}&sub={userId}",
//                Page.class, listName, pageNumber, pageSize, userId);
    }

}
